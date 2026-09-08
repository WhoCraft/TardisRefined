// Temporary workaround because Forge 1.20.1 does not seem to support interface mixins at all...
function initializeCoreMod() {
	var ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
	var Opcodes = Java.type('org.objectweb.asm.Opcodes');
	var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
	var InsnList = Java.type('org.objectweb.asm.tree.InsnList');

	return {
        "tardis_refined_entity_getter_inject": {
        	"target": {
                "type": "METHOD",
                "class": "net.minecraft.world.level.EntityGetter",
                "methodName": "m_5788_",
                "methodDesc": "(DDDDLjava/util/function/Predicate;)Lnet/minecraft/world/entity/player/Player;"
            },
            "transformer": function(method) {
                var toAdd = new InsnList();
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, 9)); // Predicate<Entity>
                toAdd.add(
                    ASMAPI.buildMethodCall(
                        "whocraft/tardis_refined/common/capability/player/TardisPlayerInfo",
                        "wrapNullablePredicateWithExcludeShellView",
                        "(Ljava/util/function/Predicate;)Ljava/util/function/Predicate;",
                        ASMAPI.MethodType.STATIC
                    )
                );
                toAdd.add(new VarInsnNode(Opcodes.ASTORE, 9));
                method.instructions.insert(method.instructions.getFirst(), toAdd);
                return method;
            }
        }
    }
}