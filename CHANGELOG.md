## Version 1.6.3 - 7/24/2025

### 🎣 New features 🎣
- Fish entities will now tilt up and down when moving vertically

### 🚀 Compatibility Features 🚀
- Compatibility with Fishing Real for Fabric versions

### ⚙️ Bug fixes ⚙️
- Fixed a bug where setting the `giveJournal` config to false breaks the fishing journal layout on multiplayer servers
- Fixed a bug where inventory slots would appear in the JEI rod upgrading GUI
- Fixed a bug where an invalid texture caused log errors

---

## Version 1.6.2 - 6/30/2025

### 🚀 Compatibility Features 🚀
- Compatibility with LevelZ and JobsAddon

### ⚙️ Bug fixes ⚙️
- Fixed a bug where the forge ItemStackedOnOther event wouldn't be called with fishing rod items
- Fixed a bug where Fishing Real for NeoForge 1.21.1 results in a crash
- Fixed a bug where the wrong crate type could be pulled from lava lakes
- Fixed a crash with fishing rod tooltips

---

## Version 1.6.1 - 6/3/2025

### 🎣 New features 🎣
- Any container block entity can now be used as a crate through datapacks/mods
- Fishing rod accessories will drop when a rod breaks
- Some miscellaneous loot balancing adjustments

### ⚙️ Bug fixes ⚙️
- Fixed a bug where fishing with the latest version of Fishing Real results in a crash
- Fixed a bug where torn notes were reset under certain conditions
- Fixed a few crashes relating to the fishing hook entity

---

## Version 1.6.0 - 5/9/2025

### 🎣 New features 🎣

---
**Fishing Rod Improvements**
- Updated fishing rod textures (created by codly)
- Updated bait slot textures to match the 1.21.4 port
- Improved fishing line rendering to more closely align with the item model
- Some fishing rods will now have special features:
  - Amethyst fishing rod chimes when a fish bites
  - Golden fishing rod gives +1 luck
  - Diamond fishing rod gives extra XP
  - Netherite fishing rod can fish in lava without a special hook

---
**Torn Notes**
- Torn Note items can be found in overworld surface crates
- Torn Notes can be right-clicked to show a small hint drawing of legendary fish locations
- Written books can no longer be found in from overworld surface crates

---
**Datapack Features**
- A `loot_crate_block` loot predicate has been added, allowing users to put items in specific crate types
- A `profile.info.location.aether` profile location translation key has been added

---
**Miscellaneous**
- Structure-specific fish have been moved to their own journal page (will be added to later)
- Fish entities no longer show as "???" if they aren't unlocked in the journal
- The fishing journal can now be placed in lecterns

---

### 🚀 Compatibility Features 🚀
- Improved fishing rod rendering while using the First-Person Model mod
- Fixed Fishing Real compat for forge
- Added support for Hybrid Aquatic's hook items
- Added some missing Hybrid Aquatic fish to the journal

---

### ⚙️ Bug fixes ⚙️
- Fixed a bug where fish entities would block others from spawning
- Fixed a typo in amethyst fishing bobber recipe JSON
- Fixed a bug where a fishing rod can repair itself when spamming right click
- Fixed a rendering bug where fishing lines occasionally connect to other players' bobbers
- Fixed a bug where the reinforced line's bonus is applied to regular fishing line
- Fixed a world crash with the end oasis feature
- Fixed a text formatting bug in the fishing journal

---

## Version 1.5.0 - 1/17/2025

### 🎣 New features 🎣

---
**Minigame Changes**
- Modified the minigame GUI textures (mainly to make them slightly larger)
- Added subtle sound effects for winning and losing the minigame
- Added a simple blinking animation to the bar when the minigame ends
- Slightly modified some of the feedback messages
---
**New Bobbers**
- Added 16 new themed fishing bobber variants that can be crafted with various materials
- Fishing bobbers now render their item model on the hook
- New datapack-based rod accessory data system
---
**Angling Table**
- Renamed the "Angler's Workshop" to "Angling Table"
- New block texture (created by yawpbaron) that more closely matches other minecraft workstations
- Changed the recipe for the Angling Table: it now requires 2 copper, 2 string, and 2 planks
---
**Misc Features**
- Created 2 config settings for modifying crate probabilities
- Fish from the Hybrid Aquatic mod will appear in the fishing journal
- Fish from the Unusual End mod will appear in the fishing journal
- New crate and hook textures (created by yawpbaron)
- New fishing journal texture (created by miqotepetter)
---

### ⚙️ Bug fixes ⚙️
- Fixed a bug where interacting with the minigame would be heavily delayed on servers with high latency _([#4](https://github.com/Lightning-64/Tide/issues/4))_
- Fixed a bug where putting a rod into the Angling Table would delete already inputted accessories
- Fixed a bug where custom journal profiles would have no description text unless they used a translation
- Fixed a bug where attempting to fish while Mine Cells is installed would not work

---

## Version 1.4.2 - 12/25/2024

### 🎣 New features 🎣

- All Tide fishing rods can now have durability enchantments applied, such as Unbreaking or Mending
- The fishing journal's page selection buttons are closer to the "done" button and will no longer disappear if they're not applicable
- The fishing journal's "welcome" page will hide itself if other pages are unlocked
- Renamed "Trout" to "Rainbow Trout" (it looks more like one)
- Default journal entries for modded fish now have internal translations that can be changed with resource packs or mods _([#32](https://github.com/Lightning-64/Tide/issues/32))_
- The Origins mod will now recognize fish as meat _([#35](https://github.com/Lightning-64/Tide/issues/35))_
---

### ⚙️ Bug fixes ⚙️
- Fixed a bug where dropping a fishing rod while fishing would result in a crash _([#55](https://github.com/Lightning-64/Tide/issues/55))_
- Fixed a bug where having a fishing speed of over 5 would prevent catches _([#54](https://github.com/Lightning-64/Tide/issues/54))_
- Fixed a crash relating to the EasyNPC mod _([#42](https://github.com/Lightning-64/Tide/issues/42))_

---

## Version 1.4.1 - 12/22/2024

### 🎣 New features 🎣

- Added 2 new legendary fish that can be found in structures (one of them is 1.21+ exclusive)
---

### ⚙️ Bug fixes ⚙️
- Fixed a bug where mobs generated by monster spawners wouldn't spawn with any items _([#52](https://github.com/Lightning-64/Tide/issues/52))_
- Loot table `gameplay/fishing/crates/block.json` no longer has a `mod_loaded` condition
- Some minor bug fixes

---

## Version 1.4 - 12/13/2024

### 🎣 New features 🎣

---
**New Bait System:**
- Added bait slots to Tide fishing rod tooltip.
- Bait can now be equipped directly onto a fishing rod; to do this, open the inventory GUI and drag the bait item over to the rod (or the other way around) and right click. This will fill up the first bait slot if possible. Similarly, bait can be removed from the slots by right-clicking the rod item.
- Whatever bait item that is placed in the first bait slot will provide bait bonuses.
- Holding bait in your offhand will no longer provide bait bonuses.
---
**Angler's Workshop**
- Accessory items are now stored in the rod and can be retrieved by placing the rod in the angler's workshop.
- By grabbing the output item, the accessories in the input slots will be stored in the rod and will be consumed.
---
**Datapack Features**
- Items can now be marked as bait and receive bait bonuses through datapacks. This can be done by creating json files in the `data / tide / bait` folder (see the [wiki](https://github.com/Lightning-64/Tide/wiki) for info)
- Created more fishing loot tables that can be customized with datapacks. These include `gameplay/fishing/crates`, `gameplay/fishing/crates/block`, and `gameplay/fishing/special`. These can be found on the [github repository](https://github.com/Lightning-64/Tide/tree/main/common/src/generated/resources/data/tide/loot_table/gameplay/fishing).
---
**Misc Features**
- Fishing rod line will now darken with the current block lighting.
- Some tooltip text changes, such as the fishing rod accessory display and the bait info display.
- Fishing rod accessories are stored as `ItemStack` instances and not `Item` instances.
- Fishing rod accessories use a more compatibility-friendly system to get their info.
- Added a bit of delay before minigame inputs can be received after one starts
- Torchflowers and pitcher pods can be used as bait.
---
**Compatibility**
- Butterflies/caterpillars from [Naturalist](https://www.curseforge.com/minecraft/mc-mods/naturalist) can be used as bait.
---

### ⚙️ Bug fixes ⚙️
- Added internal translations for fish entities _([#48](https://github.com/Lightning-64/Tide/issues/48))_
- Fixed a crash relating to fishing loot tables _([#37](https://github.com/Lightning-64/Tide/issues/37))_
- Fixed a bug where you could switch between fishing rods without retrieving the hook.
- Tide's "maximum fishing luck" was set as 5, but should have been 6.
- The Oakfish now has its minigame "strength" value set to 6.0 instead of 0.

---

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
- Fixed a bug where nether depths upgrade fish couldn't be caught in minecraft 1.21+ _([#20](https://github.com/Lightning-64/Tide/issues/20))
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

1.21 port of version 1.2.0. Here's the changelog for that version:

IMPORTANT NOTE: The mod now requires Cloth Config

- Internal mod rework (this was done to add neoforge and fabric support)
- Fixed a lot of bugs
- Balance changes and better compatibility functions
- Added more fish info to journal
- Reworked config system

There might be some bugs that I still haven't fixed from reworking the mod code. Let me know if you find anything I missed.