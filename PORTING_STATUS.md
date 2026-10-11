# TARDIS Refined 26.3 Fabric port — checkpoint

## Current result

The project now completes `:fabric:build` for Minecraft Java 26.3. The task compiles the sources, validates the access widener, packages the Fabric mod, and finishes successfully. The produced development JAR is `fabric/build/libs/tardis_refined-mc26.3-v26.3-port-dev.jar`.

No automated Fabric tests exist in this project (`:fabric:test` reports `NO-SOURCE`). A Minecraft client or dedicated server has not yet been launched, so the TARDIS dimension, gameplay, and mixin behavior still need runtime validation. Treat this as a buildable development port, not a release-ready or fully verified mod.

## Port scope and known limits

- Target toolchain: Minecraft 26.3, Fabric Loader 0.19.5, Fabric API 0.161.0+26.3, Java 25, Loom 1.18.2, Gradle 9.7.1.
- The upstream PolyForm Shield license and existing attribution remain in the source tree.
- Optional integrations based on older Immersive Portals, Create, and Sodium APIs remain disabled or routed through adapters; they have not been verified against 26.3 versions.
- The old stencil-clipped animated vortex/portal rendering has not been restored. Several previews currently use static 2D textures, and the old Ponder 3D structure preview is represented by coordinate text.
- The snow shader overlay is unavailable pending a 26.3 RenderPipeline implementation.
- The custom registry-holder wrapper now acts as a lazy supplier because Minecraft seals `Holder`; no source call sites used its old custom Holder interface.
- The data-pack export helper now uses normalized absolute paths, explicit per-segment validation, portable-name checks, and a containment check before writing files.
- Dynamic TARDIS dimension creation mutates registries after startup and remains a runtime risk to investigate. Dimension creation, world saves, teleportation, villager interactions, rendering, and dedicated-server startup require in-game checks.

## Recent compile-driven changes

The latest porting pass addressed the remaining compile errors in Fabric and common source, including current data-result parsing, NBT BlockPos codecs, world/server level access, structure template managers, block interaction results, block update/collision callbacks, player messages, command permissions/arguments, particle creation, villager profession/data APIs, recipe codec dispatch, registry lookups, and 26.3 access-widener entries. The delayed player-ejection task now uses the server tick queue with a ten-tick delay.

The most recent build command was `:fabric:build`; it completed successfully after access-widener validation. No JAR launch or gameplay test was performed.
