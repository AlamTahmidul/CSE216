# A Simple File Text Generator

#### To setup and run:
```
$ git clone https://github.com/weiwenzhou/text_generator.git
$ cd text_generator
$ python generate.py
```


#### To customize the files generated edit settings file:
``` 
{
    "DIRECTORY_NAME": "input", // the directory will have a timestamp appended 
    "TEXTFILE_PREFIX": "text", 
    "NUM_OF_TEXTFILES": 50, 
    "NUM_OF_LINES": 1000,
    "WORDS_PER_LINE": 10, 
    "WORD_SET_SIZE": 50, // numbers of unique words from the word bank
    "OUTPUT_COLUMN_WIDTH_MIN": 10, 
    "OUTPUT_COLUMN_BUFFER": 4 
}
```