import hmac

import torch
from transformers import AutoModelForCausalLM, AutoTokenizer

MODEL_NAME = "HuggingFaceTB/SmolLM2-135M-Instruct"
MAX_TOKENS = 80
KEY = b"td-watermarking" 
C = 1.0 

def hash_token(token_id, key):
    message = str(token_id).encode("utf-8")
    digest = hmac.digest(key, message, "sha256")
    return int.from_bytes(digest, "big")

def is_odd(token_id, key):
    return hash_token(token_id, key) % 2 == 1

def build_odd_mask(vocab_size, key):
    """Masque booléen : True pour les tokens dont le hachage est impair.
    Calculé une seule fois (le hachage ne dépend que de l'id du token et de la clé)."""
    return torch.tensor([is_odd(t, key) for t in range(vocab_size)], dtype=torch.bool)

def parity_label(token_id, key):
    return "IMPAIR" if is_odd(token_id, key) else "pair"

tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
model = AutoModelForCausalLM.from_pretrained(MODEL_NAME).eval()

question = "What is the capital of France ?"
context = tokenizer.apply_chat_template(
    [{"role": "user", "content": question}],
    tokenize=True,
    add_generation_prompt=True,
    return_tensors="pt",
    return_dict=False,
)
answer = []

odd_mask = build_odd_mask(model.config.vocab_size, KEY)

with torch.inference_mode():
    for _ in range(MAX_TOKENS):
        logits = model(context, use_cache=False).logits[0, -1, :]

        logits = logits + C * odd_mask.to(logits.dtype)

        probabilities = torch.softmax(logits, dim=-1)
        next_token = torch.multinomial(probabilities, num_samples=1)

        if next_token.item() == tokenizer.eos_token_id:
            break

        answer.append(next_token.item())
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
print("Token par token")
for i, t in enumerate(answer):
    print(f"{i:3d}  id={t:6d}  {parity_label(t, KEY):6}  {tokenizer.decode([t])!r}")

k = sum(is_odd(t, KEY) for t in answer)
distinct = set(answer)
kd = sum(is_odd(t, KEY) for t in distinct)
print(f"\ntokens impairs : {k}/{len(answer)}")
print("\nTexte final :")
print(tokenizer.decode(answer, skip_special_tokens=True))
