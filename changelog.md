# Version 2.1.8

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2_1.png)

#### Additions

- Added tab support to the ARS GUI. Each root node gets its own tab.
- Added keyboard navigation support to the ARS GUI. Use Tab to cycle through tabs.
- Added a System Settings screen to the Monitor. Currently, it only contains a setting to toggle materialize around on and off.
- Added config option to choose between logarithmic travel time and linear travel time.
- Added config option to choose how much the travel time can vary in percentage.
- Added config option to change the default TARDIS travel speed.
- Added config option to change the default TARDIS XP gain.

#### Changes

- TARDIS travel time is now logarithmic to the travel distance instead of linear. This means that the TARDIS will travel faster to locations that are further away (mostly noticeable with extreme distances such as above 100,000 blocks). For shorter trips, the travel time will be roughly the same as before.
- TARDIS travel time now varies slightly from trip to trip.
- Pressing E will now close Monitor, ARS, and Astral Manipulator GUIs.
- Improved keyboard navigation support for the Monitor and Astral Manipulator GUIs.
- The TARDIS item will now read the `setup_data` tag on the item, if it exists, and pass it on to the created TARDIS.

#### Bug Fixes

- Fix distance not recalculated when loading a waypoint or using fast return.
- Fix mid-flight flight distance recalculation always using the takeoff position instead of the actual location in the journey.
- Fix crash when creating a TARDIS on a ship.
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
- Fixed materialize around not working in unloaded chunks.
- Fixed materialize around bringing mobs along when taking off.
- Fixed crash when relogging while TARDIS is unable to land.