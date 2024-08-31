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


## Giving the bot extra context

If you want to use this bot in a Minecraft Discord Server for example, you can provide the bot with extra context in the .env, you can get results like this:
![image](https://github.com/user-attachments/assets/f607269c-3738-435e-8a94-19e35c0093a5)

This example is using EXTRACONTEXT="This Discord Server is for a Minecraft server .The rules for this server can be found in <#1279429702368493692>. Users can connect to the Minecraft server using the ip play.helpme.nl"

