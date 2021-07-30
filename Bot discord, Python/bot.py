# bot.py
import discord
import asyncio
import random

messages = ["Maravilha Maravilha", "Sempre Contente", "Muito Bem Alimentado", "112 há fogo", "911", "Owannnnnnnnnnnnnnnnnnnnnnnn"]

class MyClient(discord.Client):
    async def on_ready(self):
        print('Logged on as', self.user)

    async def on_message(self, message):
        # don't respond to ourselves
        if message.author == self.user:
            return

        if message.content == 'Maravilha Maravilha':
            await message.channel.send('Sempre Contente')

        if message.content == 'ola':
            await message.channel.send('Cona da mãe')
        
        if message.content == '991':
            await message.channel.send('Owannnnnnnnnnnnnnnnnnnnnnnn')
        
        if message.content == 'amo-te cristina':
            await message.channel.send('Tou em cima de uma laranjeira')
        
        if message.content == 'tas bem men?':
            await message.channel.send('Claro que tou')
            await asyncio.sleep(1)
            await message.channel.send('Tou todo fodido')
        
        if message.content == 'ta chover?':
            await message.channel.send('ta a chover chouriças')

        if message.content == 'nao se esqueçam que vao comigo':
            await message.channel.send('ISSO É UMA AMEAÇA BÁRBARA?')
            
        if message.content == 'Spam':
            while True:
                channel = client.get_channel("696130133374926902") #chanel id
                await message.channel.send(random.choice(messages))
                await asyncio.sleep(360)
        #if message.content == 'mute'

client = MyClient()



#client.loop.create_task(background_loop())

client.run('NzkwNzA2OTI3MzAyMzQ0NzM0.X-EhNg.-yDkca2QTUcHkb5GL3OwOdtrCEs')