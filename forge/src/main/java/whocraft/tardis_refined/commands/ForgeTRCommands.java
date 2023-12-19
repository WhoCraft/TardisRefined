package whocraft.tardis_refined.commands;


import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import whocraft.tardis_refined.TardisRefined;
import whocraft.tardis_refined.command.arguments.UpgradeArgumentType;
import whocraft.tardis_refined.registry.DeferredRegistry;
import whocraft.tardis_refined.registry.RegistrySupplier;

public class ForgeTRCommands {

    public static final DeferredRegistry<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegistry.create(TardisRefined.MODID, Registries.COMMAND_ARGUMENT_TYPE);


    public static final RegistrySupplier<SingletonArgumentInfo<UpgradeArgumentType>> UPGRADE_ARGUMENT = COMMAND_ARGUMENT_TYPES.register("upgrade", () ->
            ArgumentTypeInfos.registerByClass(UpgradeArgumentType.class, SingletonArgumentInfo.contextFree(UpgradeArgumentType::upgradeArgumentType)));
}