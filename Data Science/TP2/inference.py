"""Génération token par token avec un petit LLM, sur CPU.
"""

import torch
import hmac
from transformers import AutoModelForCausalLM, AutoTokenizer

def hash_token(token_id, key):
    message = str(token_id).encode("utf-8")
    digest = hmac.digest(key, message, "sha256")
    return int.from_bytes(digest, "big")

MODEL_NAME = "HuggingFaceTB/SmolLM2-135M-Instruct"
MAX_TOKENS = 80


tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
model = AutoModelForCausalLM.from_pretrained(MODEL_NAME).eval()
key = b"td-watermarking"

question = "What is the capital of France ?"
context = tokenizer.apply_chat_template(
    [{"role": "user", "content": question}],
    tokenize=True,
    add_generation_prompt=True,
    return_tensors="pt",
    return_dict=False,
)
answer = []
print(context)
for i in range(len(context[0])):
    print("id :", context[0][i], ", text :", tokenizer.decode([context[0][i]]))

# 1 et 2 tokens spéciaux 
# id :  tensor(1) , text :  <|im_start|>
# id :  tensor(2) , text :  <|im_end|>


with torch.inference_mode():
    for _ in range(MAX_TOKENS):
        # Pour chaque token possible son logits
        logits = model(context, use_cache=False).logits[0, -1, :]

        # On calcule les probabilités avec un softmax
        probabilities = torch.softmax(logits, dim=-1)

        # On tire le prochain token selon les probabilitées qu'on vient de calculer
        next_token = torch.multinomial(probabilities, num_samples=1)
        print(".", end="", flush=True)

        # Si le token produit est le token spécial de fin, on arrête.
        if next_token.item() == tokenizer.eos_token_id:
            break

        # On rajoute le token à la réponse
        answer.append(next_token.item())

        # On rajoute le token au context
        context = torch.cat([context, next_token.reshape(1, 1)], dim=1)

        print("current context :", context)
        threeMostLikelyTokens = torch.topk(probabilities, 3).indices
        print("3 most likely tokens :")
        for i in range (3):
            print("token : ", threeMostLikelyTokens[i], " to text : ", tokenizer.decode([threeMostLikelyTokens[i]]))
        print("generated answer : ")
        #for i in range (len(answer)):
            #print("token : ", answer[i], " to text : ", tokenizer.decode([answer[i]]))
        print(tokenizer.decode(answer))
        print()

print()
print(context)
print(tokenizer.decode(answer, skip_special_tokens=True))
