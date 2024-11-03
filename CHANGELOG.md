## Version 1.3.4 - 11/2/2024

### 🎣 New features 🎣
- Fishing rod modifier items are no longer consumed when using the Angler's Workshop _([#24](https://github.com/Lightning-64/Tide/issues/24))_
- Multiple locations/climates can now be displayed for a single fish _([#18](https://github.com/Lightning-64/Tide/issues/18))_
- Fishing rod accessories are no longer consumed upon being applied _([#24](https://github.com/Lightning-64/Tide/issues/24))_
- Fishing rod accessory data is now stored in item NBT (1.20 and below) or data components (1.21+) so custom accessories can be created by modders _([#26](https://github.com/Lightning-64/Tide/issues/26))_
- Added a config setting for modifying the rarity of a crate catch _([#30](https://github.com/Lightning-64/Tide/issues/30))_
- Added a config setting for enabling/disabling the '???' text on fish that haven't been logged in the journal
- Added ru_ru translation (thanks to Waffylka) _([#28](https://github.com/Lightning-64/Tide/issues/28))_
- Updated zh_cn translation (thanks to junshengxie)

### ⚙️ Bug fixes ⚙️
- Fixed a bug where farmer's delight fish slices were unobtainable (sorry!) _([#25](https://github.com/Lightning-64/Tide/issues/25))_
- Fixed a bug where custom journal entries would persist between worlds _([#17](https://github.com/Lightning-64/Tide/issues/17))_
- Fixed a bug where custom journal entries would duplicate upon using /reload _([#31](https://github.com/Lightning-64/Tide/issues/31))_
- Fixed a minor tag issue _([#29](https://github.com/Lightning-64/Tide/issues/29))_

---

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

IMPORTANT NOTE: The mod now requires Cloth Config

- Internal mod rework (this was done to add neoforge and fabric support)
- Fixed a lot of bugs
- Balance changes and better compatibility functions
- Added more fish info to journal
- Reworked config system

There might be some bugs that I still haven't fixed from reworking the mod code. Let me know if you find anything I missed.
