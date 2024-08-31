# AIDisord Chat Bot
This is a Discord AI chat bot, make sure you have some powerful hardware before running this! You need to use a Ollama server running on the same pc as the bot.

## Features:
- AI replies in a channel
- AI generated welcome messages in dm's.
- AI generated bot status.
- Setting personality types

More to come.

## Running locally

First install [Ollama](https://ollama.com/), and download a model ([Google Gemma recommended!](https://ollama.com/library/gemma2)).

Now install the ZIP file from releases.

Edit the .env to your liking, make sure you have typed your bot token correct and put the name of your ollama model (For gemma you would do: gemma2:2b).


Actually running the bot:
```bash
java -jar bot.jar
```
