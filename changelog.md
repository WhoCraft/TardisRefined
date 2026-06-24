# Version 2.1.7

![TARDIS Refined](https://wiki.tardisrefined.net/TARDIS-Refined-Wiki/tardis_refined_v2_1.png)

#### Items
- Added Grown Tardis Item.

#### Changes
- Amethyst Screwdriver tooltip now is colored gray.
- Updated create-fabric integration to create 6.

#### Configs
- New config option to disable teleportation for the Immersive Portals portal, instead teleporting the player directly as if Immersive Portals integration was disabled.
- New config option to toggle the above setting separately when the door is on a Valkyrien Skies ship.
- New config option to disable the collision box of the door while open with Immersive Portals integration enabled on Valkyrien Skies ships.

#### Bug Fix
- Bug fix: TARDIS exterior disappears when moved by other mods.
- Bug fix: Taking off or landing a TARDIS on a Valkyrien Skies ship breaks the ship.
- Bug fix: Impossible to enter TARDIS when on a Valkyrien Skies ship.
- Bug fix: TARDIS shows shipyard coordinates when on a Valkyrien Skies ship.
- Bug fix: Player does not face the right horizontal direction when entering/exiting a TARDIS on a Valkyrien Skies ship.
- Bug fix: TARDIS does not take Valkyrien Skies ships into account when computing travel distance.
- Bug fix: TARDIS does not automatically try to land on ships.
- Bug fix: Flickering when spectating TARDIS exterior on a Valkyrien Skies ship.
- Bug fix: Grown Tardis Item crashes the game if Valkyrien Skies is installed.
- Bug fix: Immersive Portals portal is not rotated correctly when door is on a Valkyrien Skies ship.

- Bug fix: Fixes Console Textures having left over prefabs
- Bug fix: Fixes Forge not having the same access level as Fabric (https://github.com/WhoCraft/TardisRefined/issues/477)
- Bug fix: Fixed Shulker shells not having correct texture paths
- Bug fix: Fixed the TARDIS forgetting its current dimension on world reload
- Bug fix: Fix materialize around upgrade not working.
- Bug fix: Fixed TARDIS being broken when created in a non-overworld dimension.
- Bug fix: Immersive Portals portal sometimes not spawning when opened from exterior.
- Bug fix: Respawn Anchor and /spawnpoint command not working inside the TARDIS
- Bug fix: Capability deserialization fails on Arclight
- Bug fix: TARDIS removes forceloading when taking off and landing.
- Bug fix: TARDIS takeoff sound plays from the interior door instead of the console.
- Bug fix: Immersive Portals compatibility crashing the game on Forge.
- Bug fix: Vortex not rendering in GUIs when Journeymap or Kilt is installed.
- Bug fix: TARDIS cannot reach edges when moving in the vortex view on Forge.
- Bug fix: Players can take damage or die while in shell view.
- Bug fix: Players can receive new potion effects while in shell view.
- Bug fix: Create TARDIS Summary does not display all lines correctly.
- Bug fix: Crash when closing the game near a TARDIS Create display while Valkyrien Skies is installed.
- Bug fix: Recently created TARDIS keeps printing "Preparing spawn area: 100%" every time a chunk loads until the server is restarted.