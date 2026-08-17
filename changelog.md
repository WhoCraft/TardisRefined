# Version 2.1.8

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2_1.png)

#### Changes
Shells are now breakable if no TARDIS dimension exists yet. This means the root shell is now breakable before you shear it open.
Shulker shell now uses a 1 block tall bounding box when closed.
Addons can now specify arbitrary voxel shapes for their shell and interior door collision boxes.

#### Bug Fix
Fix root shell not breaking when placed inside a TARDIS.
Fix top half of door and shell bounding box not being selectable. A hidden redirect block will now be placed there which you can see in F3 when sneaking. Don't worry, the block is only placed if there's space, so the diamond block you glitched into the top half of your TARDIS shell is still safe.
Fix briefcase shell and door using the default bounding box.
Fix Immersive Portals portal not positioned correctly for offset interior doors.