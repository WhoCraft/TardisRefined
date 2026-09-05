# Version 2.1.8

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2_1.png)

#### Additions

- Added tab support to the ARS GUI. Each root node gets its own tab.
- Added keyboard navigation support to the ARS GUI. Use Tab to cycle through tabs.

#### Changes

- Pressing E will now close Monitor, ARS, and Astral Manipulator GUIs.
- Improved keyboard navigation support for the Monitor and Astral Manipulator GUIs.
- The TARDIS item will now read the `setup_data` tag on the item, if it exists, and pass it on to the created TARDIS.
- Shells are now breakable if no TARDIS dimension exists yet. This means the root shell is now breakable before you shear it open.
- Shulker shell now uses a 1 block tall bounding box when closed.
- Addons can now specify arbitrary voxel shapes for their shell and interior door collision boxes.

#### Bug Fixes

- Fixed the Astral Manipulator GUI not opening when the player is holding an item.
- Fixed points and XP text in the ARS GUI not being translatable.
- Fixed the mod name being drawn in the wrong position in the ARS GUI.
- Fixed the TARDIS item having infinite uses in Survival Mode.
- Fixed the arm not swinging when using the TARDIS item.
- Fixed the GPS not using the same direction and dimension name formatting as the rest of the mod.
- Fixed the item name of sampled dimensions not being translatable.
- Fixed a rare crash when rendering the shell holo.
- Fixed the Forge Zeiton Glass load function removing the default `requestModelDataUpdate()` function call from every Forge block entity.
- Fixed players glitching out when relogging while in shell view on Fabric.
- Fixed the shell-view return position being rounded to the nearest block position.
- Fixed players not facing the correct direction after exiting shell view.
- Fixed items dropped in shell view ending up outside the TARDIS.
- Fixed players being able to pick up items and XP while in shell view.
- Fixed the TARDIS sometimes playing the default hum when set to a different hum.
- Fix root shell not breaking when placed inside a TARDIS.
- Fix top half of door and shell bounding box not being selectable. A hidden redirect block will now be placed there which you can see in F3 when sneaking. Don't worry, the block is only placed if there's space, so the diamond block you glitched into the top half of your TARDIS shell is still safe.
- Fix briefcase shell and door using the default bounding box.
- Fix Immersive Portals portal not positioned correctly for offset interior doors.
- Fix half-baked shell no longer being shearable after taking off and landing (or reloading the world). It will now continue to be shearable until you unlock the chameleon circuit.
