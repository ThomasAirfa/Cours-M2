import argparse
import hmac

from scipy.stats import binomtest
from transformers import AutoTokenizer

MODEL_NAME = "HuggingFaceTB/SmolLM2-135M-Instruct"


def hash_token(token_id, key):
    message = str(token_id).encode("utf-8")
    digest = hmac.digest(key, message, "sha256")
    return int.from_bytes(digest, "big")


def is_odd(token_id, key):
    return hash_token(token_id, key) % 2 == 1


def detect(text, key, tokenizer):
    # add_special_tokens=False : on ne veut pas compter d'éventuels tokens spéciaux
    token_ids = tokenizer.encode(text, add_special_tokens=False)
    distinct = set(token_ids)
    n = len(distinct)
    k = sum(is_odd(t, key) for t in distinct)
    p_value = binomtest(k=k, n=n, p=0.5, alternative="greater").pvalue
    return k, n, p_value



parser = argparse.ArgumentParser()
parser.add_argument("text", help="texte à analyser")
parser.add_argument("key", help="clé secrète")
args = parser.parse_args()

tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
k, n, p = detect(args.text, args.key.encode("utf-8"), tokenizer)
print(f"tokens distincts : {n}, impairs : {k} ({k / n:.1%})")
print(f"p-value : {p}")