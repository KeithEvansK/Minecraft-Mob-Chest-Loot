<div align="center">
  
# Minecraft MMO Mob Chest Loot

A MMO style loot system solution to easily add to Minecraft Spigot based servers. 

</div>

## How Mob Chest Loot Works

![MMO_Looting](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/6d66199f-4000-40f8-8abb-d424463abb8c)

This plugin combines all dropped loot from enemies into a single entity chest. 

Then provides a clean user interface for the player to select loot they want to take and throw away loot they may not want. 

![image](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/e747972f-6898-4491-b770-a4a69043ed8a)



Features Include: 
 - Player specific loot. The plugin will remember what player the loot belongs to and lock it from other players or until a specific amount of time has passed.
 - Less stress on your server because it won't need to render several dropped item entities at any given time.
 - Allows use for if you want players dropped loot to also be locked into chests.
 - Clean user interface for players to navigate and select their loot.
 - Automatic cleanup. Normally if a player doesn't want their loot it stays for several minutes. Now once checked, the unwanted loot is instantly removed from the game. 

## Regular Minecraft Looting

![Regular_Looting](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/c62d440c-54b8-4778-abed-8feea98d5306)

In Minecraft, all loot is generally dropped onto the ground when an enemy is killed. 

This can cause confusion among multiple members, allows your items to be stolen by other players, and causes unnecessary load on the server to maintain all dropped entities at a given time. 



# About
This is a plugin completely developed by myself originally intended only to be used by my own Minecraft server. 

But I may make it available for other users at some point so they can add it to their own servers as well. 

Otherwise the code here is available if someone wants to develop their own similar software. 

## Development

Developing this software had several challenges but can be broken up into a few main things. 
 - Determining when loot is dropped
 - Creating the visual chest entity
 - Creating a virtual chest UI for the loot

And I'll go into all of these in depth to provide an understanding of how I achieved this. 

### Loot Drops
The first thing to understand about this plugin or to develop a plugin like it is that the first thing it does is captures when the loot is dropped. 

This is simple enough to determine by running some simple checks. 

Spigot, (The Minecraft Plugin Development Community) is nice enough that it actually provides a simple method onMobDeath() that allows us to run our check each time an enemy dies. 

This section is fairly straight forward in which we check if the enemy that dies has dropped items and if so we collect them into a list and cancel the drop. 

We do need to save important other information such as the location it exists at. 

### Creating the Visual Chest Entity

The idea of this section is to create a chest entity where the other items were supposed to spawn. 

But this is really the most difficult part of this plugins development for several reasons I'll go into. 

The first being that the chest entity, or any entity for that matter is not supposed to be clickable within Minecraft. 
The game was designed in a way that it would detect when a player was close to it and would instantly add them to the player's inventory. 

To get around this I did some work with positioning. 

I ran a check every time the player clicked, no matter where they are and check their position to the list of positions available within the Chest Index. 

I then added a radius to the position check and if the player was close enough within that position it would hit and open the chest. 

![image](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/e33e6afb-dc2e-4f4a-a900-58a9f1ab6721)


Another problem was that if the chest moved locations after creation. 

In Minecraft entities are effected by gravity. So if it fell in location and you didn't check that then the player would be finding a chest at the wrong location and be unable to click it to open. 

This was fixed by checking the entity location of the dropped chest entity, and then correcting the location of the virtual chest which does not exist in space. 
I run this check once every second which is once every 20 server ticks. So while I'd like a more efficient way to achieve this without running checks every second, it works for now. I designed this whole plugin with the idea of trying to avoid running that check in any way possible and I was able to minimize it to only the location and live check which worked out pretty good. 

With the concept that the chest can move, you also have to deal with that the chest could "die" by something happening like it falls into lava which destroys entities. 
So within that same function I check if the entity chest is still alive and if not I remove the virtual chest from memory. 


### Creating the Virtual Chest

We know that because of the way Minecraft works you can't click a dropped entity. Which means you also can't normally open a chest UI either. 

This is an entity chest. 

![image](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/a7326856-73a1-4e8f-9124-cd54e4e9a3a5)


And this is a real in game chest player can normally click on to collect or store items from. 

![image](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/af188547-0068-4dd1-b92a-5743543836cf)


I got around the entity problem in the previous section but now I needed to get past the problem of how do I get a UI and make it appear to the player that they are actually opening a chest. 

I achieved this by creating a virtual inventory and treating it like a chest. 

Like above, upon clicking within the area of the chest I open the specified inventory and add the items previously cancelled by the drop. 

Something we also don't want is a bunch of item dropping in the same area if you continue to kill the enemies. 

So in this section I also check the location of chests within range of the current one being dropped and if the same player owns them both, as if they killed both enemies, then I remove the other chest and combine them into one for easier use. 

![image](https://github.com/KeithEvansK/Minecraft-Mob-Chest-Loot/assets/99915276/e7f61a14-e9f5-4f2e-a2d8-5230d4880b4e)


### Final Development Thoughts

There are several other small things and bugs I've fixed in this project and many more that will probably arise as Minecraft comes out with new updates or as players use it and find unique ways to break it. 

Because of the necessary development of this plugin I may or may never release it as my own plugin just because of the maintenance required for it. 

But the Minecraft Plugin development community is great and was helpful with documentation and questions when I needed assistance learning more. 

This is only my second Minecraft plugin but I've used Java many times before so it was a fun way of using those skills and seeing them translate into something I enjoy and I don't think this will be my last plugin. 

I developed this plugin out of an idea I had and couldn't find any other solutions already so I made my own. 

Thanks for checking out my project :)




