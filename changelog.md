# Version 2.1.8

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2_1.png)

#### Changes
TARDIS item will now read the setup_data tag on the item if it exists and pass it on to the created TARDIS.

#### Bug Fix
Fix TARDIS item having infinite uses in survival mode.
Fix arm not swinging when using the TARDIS item.
Fix GPS not using the same direction and dimension name formatting as the rest of the mod.
Fix item name of sampled dimension not being translatable.
Fix a rare crash when rendering the shell holo.
Fix the Forge Zeiton Glass load function removing the default `requestModelDataUpdate()` function call from every single Forge block entity.
Fix players glitching out when relogging while in shell view on Fabric.
Fix shell-view return position being rounded to the nearest block position.
Fix player exiting shell-view not facing the correct direction.
Fix items dropped in shell view ending up outside the TARDIS.
Fix players able to pick up items and xp while in shell view.
