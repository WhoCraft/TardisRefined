# Version 2.0.6

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2.png)

## New Features

- Datapack Console Patterns and Shell Patterns can now define the sounds used by the Tardis.
  - Console Patterns can define Left Click and Right Click sounds for Generic interactions, as well as Throttle and
    Handbrake specific sounds
  - Shell Patterns can define Door Unlock/Lock, Door Open/Close sounds.

**Note:** These additions are optional fields, meaning all existing Pattern datapacks will not need to be updated.

## Gameplay Fixes
- Fixes occasional legacyrandom crash within Gra- I mean Mavity Well
- Fixes left clicking controls during flight dance activating the control's function unexpectedly. E.g. Left clicking X
  Coordinate control actually changing the X coordinate when re-aligning a control
- Fixed blocks causing player's hand to swing twice in every interaction. Caused by the wrong InteractionResult being
  used on the serverside.
- Fixes Dimensions not being registered to the server, causing the world to not exist upon reloading the save. This also
  increases performance for world loading as it means the server doesn't need to create a new world everytime a player
  enters a Tardis after server restart (for multiplayer) or logging out then reopening a save file (singleplayer).
- Comprehensive fix for Root Shell not updating its `OPEN` state during terraforming (#299). This has been achieved by
  cleaning up and unifying door update logic as well as patching the desktop regeneration logic.
- Attempting to land a Tardis on a block where there are no valid positions caused an infinite crash loop #309
- Fixes issue where (Neo) Forge rendering would cause Consoles and Shells to stop rendering at certain angles

## Technical Debt
- Cleaned up all Tardis manager classes and implemented common methods stuch as ``tick(ServerLevel level)``,
  ``saveData`` and ``loadData``
- Unified door closing and door locking logic by implementing the ``TardisInternalDoor`` interface on
  ``AbstractDoorBlockEntity`` instead of some classes sometimes using the Blockstate property, and other classes
  sometimes using the BlockState properties directly.
  - Before this cleanup the codebase logic would continue to become even more inconsistent, difficult to maintain, test,
    debug and enable poor practices to slip in.
  - Example: Forgetting to update the exterior shell but not the internal door, and vice versa.
- Unified shell open and locking logic by using ``ExteriorShell`` interface which was introduced early on in the 1.19,2
  development but was hidden in a random package and never referenced again.
- Relocated ``ExteriorShell`` interface to another package so that it's more noticeable
- Moved desktop generation logic into a method for ease of viewing
- Reordered some getter and setter methods in classes for ease of viewing
- Fixed teleportation via Tardis doors not using the correct facing, requiring us to call `Direction#getOpposite` in
  many places to compensate. Previously it created great difficulty to debug teleportation issues.
- Removed `AbstractDoorBlockEntity#isLocked` in favour of a Blockstate Property. This allows the locking logic to use
  the BlockState Property to be consistent with the Exterior Shell.
- Enhanced ``TardisExteriorManager#setOrUpdateExteriorBlock`` to not be as verbose with the parameters. Removed the
  boolean field, and added a default method called ``TardisExteriorManager.placeExteriorBlockForLanding`` which
  implements the correct logic for when the Tardis is landing, and thus requires a new Shell blockstate instance to be
  created.
- Exterior Shell updates using duplicate implementations across multiple classes, include `AestheticHandler`,
  `TardisExteriorManager` and `TardisInteriorManager`. This is now unified in `TardisLevelOperator` for ease of
  maintenance.

## API Changes

- Added new events for addon mods - `LockDoor` and `UnlockDoor`, which are fired after the internal door and exterior
  shell blocks are locked/unlocked
- Made `OpenDoor` and `CloseDoor` events fire after doors have been updated. Previously it fired after the internal door
  updates, but before the exterior shell updated. This would have caused some issues when addon mods subscribe to these
  events because it would mean addon mods are applying logic before the exterior shell door has closed, which can have
  unintended consequences.
- Added `ShellChangeSource`, an object to identify why a Shell was updated. This provides additional data to use for
  differentiate logic between updating shell from landing the Tardis to when the shell is updated during terraforming

