# Version 2.0.2

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2.png)


## ADDITIONS
- Unlock all and lock all commands for Upgrades
- Added Faded Shell Variant for Police Box
- Added Hellbound Desktop
- Added IronGuard Desktop
- Added Lodestone Desktop
- Added Mechanical Desktop
- Added Mesh Desktop
- Added Newwave Desktop
- Added Radiance Desktop
- Added Vapor Desktop
- Added Watchface Desktop
- Added Watchface Desktop
- Added Basalt Deltas Hum
- Added Crimson Forest Hum
- Added Nether Wastes Hum
- Added Underwater Hum
- Added Soul Sand Valley Hum
- Added Warped Forest Hum

## BUG FIXES
+ Fixes [#293](https://github.com/WhoCraft/TardisRefined/issues/293) - Tardis Flight Loop sound only sometimes plays
+ Fixes [#202](https://github.com/WhoCraft/TardisRefined/issues/202) - Navigation buttons not working - Incorrect registry logic on Neoforge
+ Fixes [#269](https://github.com/WhoCraft/TardisRefined/issues/269) - Dimension Switch midflight
+ Fixes [#278](https://github.com/WhoCraft/TardisRefined/issues/278) - Immersive Portals Issues
+ Fixes [#272](https://github.com/WhoCraft/TardisRefined/issues/272) - Tardis currentLocation bug
+ Fixes [#283](https://github.com/WhoCraft/TardisRefined/issues/283) - Console buttons nonfunctional during flight mini game.
+ Fixes teleporter ticking twice
+ Addresses a issue with Addon Mods patterns
+

## CODE IMPROVEMENTS
- ShellBaseBlockEntity always having the wrong TARDIS_ID being set during exterior shell placement in TardisExteriorManager. This breaks most functions of the Tardis, such as teleporting players to the wrong dimension, and breaking the Tardis Key's ability to unlock/lock the Tardis
    - Added check to ``TardisHelper.teleportEntityTardis`` to prevent entities being teleported to an invalid dimension if trying to enter via an exterior shell which doesn't have a valid TARDIS_ID due to above bug.
    - Since players won't be able to enter their Tardises anymore (better this than wrong teleports), they can "fix" it by teleporting into the Tardis Interior via `/tardis_refined interior` and forcing a block update on the Shell block, such as changing exteriors or flying the Tardis to a different location.
    - On multiplayer servers, administrator intervention may be required.
- Added ``TardisExteriorManager#setOrUpdateExteriorBlock`` as a common logic for placing Exterior blocks whilst preserving critical data such as ShellTheme and Patterns
- Replaced ``TardisExteriorManager#placeExteriorBlock`` with ``startLanding`` and ``setOrUpdateExteriorBlock``, where ``setOrUpdateExteriorBlock`` executes the block placement, and ``startLanding`` updates Tardis data and chunkloads the area.
- Enhanced ``TardisExteriorManager#triggerShellRegenState`` with a boolean flag to unify logic for updating the REGEN blockstate property on the Shell Block when the Tardis is reconfiguring its Desktop.
- KeyItem using wrong logic to append strings to translatable tooltip strings. Fixes scenario where tooltip displayed ``*message.tardis_refined.tooltip_in_flight*``
- Fixed ``TardisExteriorManager.setDoorClosed`` using confusing logic when trying to determine if we are trying to close the door or not. Previously, it was using the ``locked`` field to determine "closed" state, which was erroneous.
- Large amount of duplicate control entities present on Nuka Console
- Fixed placement of monitor control for all consoles. Fixes some of the behaviour described in #283. **To be tested**
- Added dimension load fix from #296
- Missing translations for all default Hum sounds (used one translation key to cover all scenarios- "TARDIS hums")
- Missing translation for SCREWDRIVER_CONNECT sound
- Missing translation for LOW_FUEL sound
- Fixed FlightDance control re-alignment logic in ControlEntity allowing player to activate the control. Fixes Issue #269
    - **Example**: "Randomiser" control is considered "misaligned" by FlightDanceManager.
    - When player clicks on the Randomiser control, the control is realigned BUT the Tardis coordinates are ALSO randomised.
    - This causes the flight destination to be changed and the flight duration to be recalculated, which was unintended.
- Fixed Gravity Shaft causing player to open elytra when JUMP key is pressed.
    - NOTE: Due to the patch using ``Player#onGround`` this means all game behaviours relating to when the player is on the ground (hunger, damage etc.) will also be applied
- Fixed Tardis Flight Loop not playing (Combination of undocumented logic being called in multiple call sites, and the volume being set to zero causing the SoundManager to skip playing it). Fixes Issue #293
- Fixed several sound entries having the incorrect registry names, which caused any data-generated translations for their subtitles to always be incorrect.
- `HumSelectionScreen` using erroneous logic for parsing Hum text components, causing log spam each time the screen is opened.

### Changes
- Refactor of LoopingSound and TardisClientLogic to unify logic, remove duplicate call sites, document logic.
    - Moved SoundInstance registry entries into their own class, cleaning up method names
- Made TardisPilotingManager and TardisExteriorManager explicitly update TardisClientData in a few critical methods such as ``endFlight``, to ensure that sounds dependent on that data is played at the correct time.

### Additions
- Added a boolean flag to the `Control` object to allow for controls to be excluded from the list of possible controls to use for the FlightDanceManager. This is flexible enough to allow for addon mods to modify and customise behaviour.


Full Changelog: [https://wiki.tardisrefined.net/version-2-0-2.html](https://wiki.tardisrefined.net/version-2-0-1.html)