package whocraft.tardis_refined.common.tardis.manager;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import whocraft.tardis_refined.NbtConstants;
import whocraft.tardis_refined.client.TardisIntReactions;
import whocraft.tardis_refined.common.block.door.GlobalDoorBlock;
import whocraft.tardis_refined.common.block.door.RootShellDoorBlock;
import whocraft.tardis_refined.common.block.shell.GlobalShellBlock;
import whocraft.tardis_refined.common.block.shell.RootedShellBlock;
import whocraft.tardis_refined.common.blockentity.door.GlobalDoorBlockEntity;
import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisDesktops;
import whocraft.tardis_refined.common.tardis.TardisArchitectureHandler;
import whocraft.tardis_refined.common.tardis.themes.DesktopTheme;
import whocraft.tardis_refined.common.tardis.themes.ShellTheme;
import whocraft.tardis_refined.registry.BlockRegistry;

public class TardisInteriorManager {

    private TardisLevelOperator operator;
    private boolean isWaitingToGenerate = false;
    private boolean isGeneratingDesktop = false;
    private int interiorGenerationCooldown = 0;

    private DesktopTheme preparedTheme;

    public TardisInteriorManager(TardisLevelOperator operator) {
        this.operator = operator;
    }

    public boolean isGeneratingDesktop() {
        return this.isGeneratingDesktop;
    }

    public boolean isWaitingToGenerate() {
        return this.isWaitingToGenerate;
    }

    public int getInteriorGenerationCooldown() {
        return this.interiorGenerationCooldown / 20;
    }

    public CompoundTag saveData(CompoundTag tag) {
        tag.putBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE, this.isWaitingToGenerate);
        tag.putBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP, this.isGeneratingDesktop);
        tag.putInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN, this.interiorGenerationCooldown);
        if (this.preparedTheme != null) {
            tag.putString(NbtConstants.TARDIS_IM_PREPARED_THEME, this.preparedTheme.id);
        } else {
            tag.putString(NbtConstants.TARDIS_IM_PREPARED_THEME, "");
        }

        return tag;
    }

    public void loadData(CompoundTag tag) {
        this.isWaitingToGenerate = tag.getBoolean(NbtConstants.TARDIS_IM_IS_WAITING_TO_GENERATE);
        this.isGeneratingDesktop = tag.getBoolean(NbtConstants.TARDIS_IM_GENERATING_DESKTOP);
        this.interiorGenerationCooldown = tag.getInt(NbtConstants.TARDIS_IM_GENERATION_COOLDOWN);
        this.preparedTheme = TardisDesktops.getDesktopThemeById(tag.getString(NbtConstants.TARDIS_IM_PREPARED_THEME));

    }

    public void tick(Level level) {
        if (this.isWaitingToGenerate) {
            if (level.players().size() == 0) {
                this.operator.getExteriorManager().triggerShellRegenState();
                operator.setDoorClosed(true);
                generateDesktop(this.preparedTheme);

                this.isWaitingToGenerate = false;
                this.isGeneratingDesktop = true;
            }
        }

        if (this.isGeneratingDesktop) {

            if (!level.isClientSide()) {
                interiorGenerationCooldown--;
            }


            if (level.getGameTime() % 20 == 0) {
                System.out.println("Seconds till done: " + getInteriorGenerationCooldown());
            }

            if (interiorGenerationCooldown == 0) {
                this.operator.setShellTheme(ShellTheme.FACTORY);
                this.isGeneratingDesktop = false;
            }

            if (level.getGameTime() % 60 == 0) {
                operator.getExteriorManager().playSoundAtShell(SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F + operator.getExteriorManager().getLastKnownLocation().level.getRandom().nextFloat(), 0.1f);
            }
        }
    }

    public void generateDesktop(DesktopTheme theme) {
        if (this.operator.getInternalDoor() != null) {
            this.operator.getLevel().setBlockAndUpdate(this.operator.getInternalDoor().getDoorPosition(), Blocks.AIR.defaultBlockState()); // Remove the already existing door.
        }

        if(operator.getLevel() instanceof ServerLevel serverLevel){
            TardisArchitectureHandler.generateDesktop(serverLevel, theme);
        }
    }

    public void prepareDesktop(DesktopTheme theme) {
        this.preparedTheme = theme;
        this.isWaitingToGenerate = true;
        this.interiorGenerationCooldown = 1200; // Make this more independent.
    }

    public void cancelDesktopChange() {
        this.preparedTheme = null;
        this.isWaitingToGenerate = false;
    }

    public void setShellTheme(ShellTheme theme) {
        BlockState state = operator.getLevel().getBlockState(operator.getInternalDoor().getDoorPosition());
        // Check if its our default global shell.

        if (state.getBlock() instanceof GlobalDoorBlock) {
            operator.getLevel().setBlock(operator.getInternalDoor().getDoorPosition(),
                    state.setValue(GlobalDoorBlock.SHELL, theme), 2);
        } else {
            if (state.getBlock() instanceof RootShellDoorBlock) {
                operator.getLevel().setBlock(operator.getInternalDoor().getDoorPosition(),
                        BlockRegistry.GLOBAL_SHELL_BLOCK.get().defaultBlockState().setValue(GlobalShellBlock.OPEN, state.getValue(RootedShellBlock.OPEN))
                                .setValue(GlobalShellBlock.FACING, state.getValue(RootedShellBlock.FACING)).setValue(GlobalShellBlock.SHELL, theme), 2);

                var shellBlockEntity = operator.getLevel().getBlockEntity(operator.getInternalDoor().getDoorPosition());
                if (shellBlockEntity instanceof GlobalDoorBlockEntity entity) {
                    operator.setInternalDoor(entity);
                }
            }
        }
    }
}
