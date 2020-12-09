import json # loading settings 
import random 
import os
import time # to avoid overlapping directory
from collections import Counter, OrderedDict

# GLOBALS
FILE_WORDS = OrderedDict()

with open("settings.json") as s:
    settings = json.load(s)

with open("words.txt") as words:
    words = words.read().splitlines()
    word_set = random.sample(words, settings["WORD_SET_SIZE"])

TIMESTAMP = int(time.time())
DIRECTORY_NAME = f"./{settings['DIRECTORY_NAME']}"

os.makedirs(DIRECTORY_NAME, exist_ok=True)
EXT_ZERO_PAD = len(str(settings["NUM_OF_TEXTFILES"]))
for ext in range(settings["NUM_OF_TEXTFILES"]):
    name = f"{settings['TEXTFILE_PREFIX']}_{str(ext).rjust(EXT_ZERO_PAD, '0')}.txt"
    with open(f"{DIRECTORY_NAME}/{name}", "w") as writeFile:
        text = [" ".join(random.choices(word_set, k=settings["WORDS_PER_LINE"])) for l in range(settings["NUM_OF_LINES"])]
        FILE_WORDS[name[:-4]] = Counter((" ".join(text)).split())
        writeFile.write("\n".join(text))

# create output file
WORD_SET_IN_FILES = set.union(*[set(words) for words in FILE_WORDS.values()])
OUT = {word:[count[word] for name, count in FILE_WORDS.items()] for word in sorted(WORD_SET_IN_FILES)}
MAX_WORD_SIZE = len(max(word_set, key=lambda x: len(x)))
MAX_COL_SIZE = max(len(max(FILE_WORDS.keys(), key=lambda x: len(x))) + settings["OUTPUT_COLUMN_BUFFER"], settings["OUTPUT_COLUMN_WIDTH_MIN"])
# add the total
for key, value in OUT.items():
    OUT[key] = value + [sum(value)]
with open(f"output.csv", "w") as output:
    # header
    output.write(f",{','.join(FILE_WORDS.keys())},total\n")
    # lines
    result = [f"{k},{','.join([str(num) for num in v])}" for k,v in OUT.items()]
    output.write("\n".join(result))

with open(f"output.txt", "w") as output:
    # header
    output.write(f"{''.ljust(MAX_WORD_SIZE)} {' '.join([name.ljust(MAX_COL_SIZE) for name in FILE_WORDS.keys()])} {'total'.ljust(MAX_COL_SIZE)}\n")
    # lines
    result = [f"{str(k).ljust(MAX_WORD_SIZE)} {' '.join([str(num).ljust(MAX_COL_SIZE) for num in v])}" for k,v in OUT.items()]
    output.write("\n".join(result))