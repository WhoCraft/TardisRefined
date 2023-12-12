# TardisEvents Class

## Overview

The **TardisEvents** class provides a set of events related to the TARDIS (Time And Relative Dimension In Space) functionality in the Minecraft mod "Tardis Refined." These events cover various actions such as take-off, landing, door operations, shell changes, player entry, TARDIS crashes, and unlocking upgrades.

## Events

- **TakeOff Event**
    - *Event Signature:*
      ```java
      EventResult onTakeOff(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);
      ```
    - *Description:*
      This event is triggered when a TARDIS takes off. It provides information about the TARDIS level operator, the level it is taking off from, and the position of the TARDIS.

- **CloseDoor Event**
    - *Event Signature:*
      ```java
      void onDoorClosed(TardisLevelOperator tardisLevelOperator);
      ```
    - *Description:*
      This event is triggered when the TARDIS door is closed. It provides information about the TARDIS level operator.

- **OpenDoor Event**
    - *Event Signature:*
      ```java
      void onDoorOpen(TardisLevelOperator tardisLevelOperator);
      ```
    - *Description:*
      This event is triggered when the TARDIS door is opened. It provides information about the TARDIS level operator.

- **Land Event**
    - *Event Signature:*
      ```java
      void onLand(TardisLevelOperator tardisLevelOperator, LevelAccessor level, BlockPos pos);
      ```
    - *Description:*
      This event is triggered when a TARDIS lands. It provides information about the TARDIS level operator.

- **ShellChange Event**
    - *Event Signature:*
      ```java
      void onShellChange(TardisLevelOperator tardisLevelOperator, ResourceLocation theme, boolean isSetupTardis);
      ```
    - *Description:*
      This event is triggered when a TARDIS changes its shell. It provides information about the TARDIS level operator, the theme the TARDIS changed to, and whether the change was from a setup TARDIS.

- **TardisEntry Event**
    - *Event Signature:*
      ```java
      void onEnterTardis(TardisLevelOperator tardisLevelOperator, ExteriorShell shell, Player player, BlockPos externalPos, Level level, Direction direction);
      ```
    - *Description:*
      This event is triggered when a player enters a TARDIS. It provides information about the TARDIS level operator, the exterior shell, the player, the external position, the TARDIS level, and the direction the player is facing.

- **TardisCrash Event**
    - *Event Signature:*
      ```java
      void onTardisCrash(TardisLevelOperator tardisLevelOperator, TardisNavLocation crashLocation);
      ```
    - *Description:*
      This event is triggered when a player crashes a TARDIS. It provides information about the TARDIS level operator and the location of the crash.

- **UpgradeUnlocked Event**
    - *Event Signature:*
      ```java
      void onUpgradeUnlock(TardisLevelOperator tardisLevelOperator, Upgrade upgrade);
      ```
    - *Description:*
      This event is triggered when a TARDIS unlocks a new upgrade. It provides information about the TARDIS level operator and the unlocked upgrade.
