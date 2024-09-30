## Version 1.3.3 - 9/30/2024

### 🎣 New features 🎣
- Added the ability to remove existing fish profiles through the use of datapacks (see the [wiki](https://github.com/Lightning-64/Tide/wiki) for info) _([#9](https://github.com/Lightning-64/Tide/issues/9))_
- Some simple compatibility for the farmer's delight mod:
  - Added a fish slice/cooked fish slice item
  - Edible tide fish can be used in some recipes

### ⚙️ Bug fixes ⚙️
- Fixed a bug where the angler's workshop would act like a crafting table when [Visual Workbench](https://www.curseforge.com/minecraft/mc-mods/visual-workbench) was installed _([#6](https://github.com/Lightning-64/Tide/issues/6))_
- Fixed a bug where tags on mod-generated blocks didn't load properly when tide was installed _([#10](https://github.com/Lightning-64/Tide/issues/10))_
- Fixed a bug where fishing loot parameters weren't properly set on forge and neoforge _([#12](https://github.com/Lightning-64/Tide/issues/12))_

---

**[1.3.2 - 9/26/24]**

⚙️ Technical features ⚙️
- The mod now uses a datapack system for adding to the contents of the fishing journal! This is to
replace the older version of this system that used config files. Info on how to make one of these
datapacks will be found on the [wiki](https://github.com/Lightning-64/Tide/wiki) (once I create the page)
- Also, a [GitHub page](https://github.com/Lightning-64/Tide) containing the source code and the new
issue tracker is now avaliable too.

🎣 Other mod changes 🎣
- Fixed a bug where treasure loot could not be caught while fishing! (sorry about that)
- Added a config setting for disabling the toast notifications that show when a new fish is found.
- Added a config setting for using the default fishing line color (for better visuals with shaders)
- Enchanted books can now be found in loot crates (0-1 per crate depending on the rarity)
- Some very minor bug fixes that aren't worth listing here lol

---

**[1.3.1 - 9/20/24]**

- Added compatibility with Fins and Tails
- Fixed a bug where fish entities' custom names were always empty
- The mod will no longer crash if fishing loot tables don't select a fish
- Added config setting for disabling mod structures
- Removed algae patches (they aren't really necessary)

---

**[1.3.0 - 9/15/24]**

🐟 Fish entities are FINALLY a thing!
All the freshwater and saltwater fish items have entity counterparts.
They can be killed to get their fish item and spawn in their respective climates.
You can also right-click them with the fishing journal to view their profile easily.

⚙️ New config settings, such as:
- Hold to cast
- Minigame difficulty
- Rod durability

🚀 Compatibility with a bunch of other mods, including:
- Nether Depths Upgrade
- Stardew Fishing
- Rainbow Reef
- Fishing Real

🎣 And some other stuff
- Fishing journal now highlights newly unlocked fish (There's a config setting for this too)

---

**[1.2.0 - 9/2/24]**

1.21 port of version 1.2.0. Here's the changelog for that version:

IMPORTANT NOTE: The mod now requires Cloth Config

- Internal mod rework (this was done to add neoforge and fabric support)
- Fixed a lot of bugs
- Balance changes and better compatibility functions
- Added more fish info to journal
- Reworked config system

There might be some bugs that I still haven't fixed from reworking the mod code. Let me know if you find anything I missed.