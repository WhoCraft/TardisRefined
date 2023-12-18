## Upgrades
- ARS Tree now grows with the percentage of upgrades unlocked
- External Shell Configuration now locked behind Upgrades
- x1000 Increment now locked behind upgrades
- x2500 Increment now introduced and locked behind upgrades
- x5000 Increment now introduced and locked behind upgrades
- Added Waypoints 
- Added Co-ordinate Input (This might make you lazy people happy, but you are going to hell and back to get it)
- Landing Pad now locked behind upgrades

## Particles
#### Additions
- Added ARS Leaves Particles
- Crashed TARDIS now emits smokey particles as if room is overflowing with smoke
- Root Plant now emits particles when growing to indicate to the user that they are doing it correctly

## Sounds
#### Updates
- Added ARS Room sound
- Added Destination Ding noise, plays when TARDIS initially starts landing
- TARDIS flight noise now loops via LoopingSound

## Commands
#### Additions 
- Introduced Upgrades command to unlock/lock TARDIS Upgrades
- Introduced Points command to set TARDIS xp/points

## Shells
#### Updates
- Updated Police Box Shell Model
- Updated Factory Shell Model
#### Additions
- Added Lift Shell
- Added Hieroglyph
- Added Castle
#### Removals
- Removed Police Box "Marbles" Pattern due to Model Change
- Removed Police Box "Gaudy" Pattern due to Model Change
- Removed Police Box "Metal" Pattern due to Model Change
- Removed Police Box "Stone" Pattern due to Model Change
- Removed Police Box "Red" Pattern due to Model Change

## Crafting
- Bulkhead doors can now be crafted

## Consoles
#### Additions
- Updated all Toyota Pattern Textures
#### Updates
- Updated Nuka Default Pattern

## Interiors
- Added Shalka Interior

## TARDIS Growth Process 
- Players must now use Shears to unlock the growth stage TARDIS

## Rooms
- ARS Room is back under mod protection
- ARS Room Tree grows as the user upgrades their TARDIS (will degrow if addons add new upgrades as it is calculated with upgrade unlock percentage)

## Immersive Portals (Fabric)
- Fixed bug where users could not leave via Immersive Portals portals due to a sizing issue

## Quality of Life
- Interior doors and Shells now hold their own data instead of taking it from their related dimension
- Shell animations now play in Shell Selection Screen
- Sounds that loop are now handled differently, improving performance
- Root shell now only creates the TARDIS dimension when sheared (Hi Performance gains!)
- TARDIS interior is now air instead of growth stone 


# Bugs
- Fix GlobalDoorBlock deleting the player held item when interacted with
- Fix unexpected behaviour with ConsoleConfigurator console removal logic and change theme
- Fixed console removal running the interaction twice and causing the configurator to change its console theme
- Fixed console changing not updating control positions and sizes
- Fixed various screens hard crashing the server

## API
#### Additions
- Added TARDIS Upgrade Unlocked Event
- Allows other mods to add: Shells, Patterns, Desktops, Consoles, Upgrades



