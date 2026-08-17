// We need to use a coremod because Forge really does not like applying mixins into interfaces.
var LocalVariableNode = Java.type('org.objectweb.asm.tree.LocalVariableNode');
var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
var InsnList = Java.type('org.objectweb.asm.tree.InsnList');
var ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
var Opcodes = Java.type('org.objectweb.asm.Opcodes');
var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
var TypeInsnNode = Java.type('org.objectweb.asm.tree.TypeInsnNode');

function addLocalVariable(method, desc, signature, start, end) {
    if (start == undefined || end == undefined) {
        var findEnding = end == undefined;
        for (var index = 0; index < method.instructions.size(); index++) {
            var i = method.instructions.get(index);
            if (i instanceof LabelNode) {
                if (start == undefined) {
                    start = i;
                }
                if (findEnding) {
                    end = i;
                }
            }
        }
    }
    var maxLocal = 0;
    for (var vIndex = 0; vIndex < method.localVariables.size(); vIndex++) {
        var v = method.localVariables.get(vIndex);
        if (v.index > maxLocal) {
            maxLocal = v.index;
            // Yes, each double requires 2 variables in the JVM.
            if (v.desc == "D") {
                maxLocal++;
            }
        }
    }
    var newLocal = maxLocal+1;
    method.localVariables.add(new LocalVariableNode(
        "ref" + newLocal, desc, signature, start, end, newLocal
    ));
    method.maxLocals++;
    return newLocal;
}

function newLocalRef() {
    var newLocalRef = new InsnList();
    newLocalRef.add(new TypeInsnNode(Opcodes.NEW, "com/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl"));
    newLocalRef.add(new InsnNode(Opcodes.DUP));
    newLocalRef.add(
        ASMAPI.buildMethodCall(
            "com/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl",
            "<init>",
            "()V",
            ASMAPI.MethodType.SPECIAL
        )
    );
    return newLocalRef;
}

function initLocalRef() {
    return ASMAPI.buildMethodCall(
       "com/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl",
       "init",
       "(Ljava/lang/Object;)V",
       ASMAPI.MethodType.VIRTUAL
    );
}

function disposeLocalRef() {
    return ASMAPI.buildMethodCall(
        "com/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl",
        "dispose",
        "()Ljava/lang/Object;",
        ASMAPI.MethodType.VIRTUAL
    );
}

// Temporary workaround because Forge 1.20.1 does not seem to support interface mixins at all...
function initializeCoreMod() {
    return {
        "tardis_refined_block_getter_inject": {
            "target": {
                "type": "METHOD",
                "class": "net.minecraft.world.level.BlockGetter",
                "methodName": "m_151358_",
                "methodDesc": "(Lnet/minecraft/world/level/ClipContext;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/BlockHitResult;"
            },
            "transformer": function(method) {
                method.maxStack = Math.max(method.maxStack, 10);

                  var localBlockPos = addLocalVariable(
                      method, "Lcom/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl;",
                      "Lcom/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl;"
                  );
                  var localBlockState = addLocalVariable(
                    method, "Lcom/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl;",
                    "Lcom/llamalad7/mixinextras/sugar/impl/ref/generated/LocalRefImpl;"
                );
                var toAdd = new InsnList();

                var blockStateIndex = 3;

                toAdd.add(newLocalRef());
                toAdd.add(new VarInsnNode(Opcodes.ASTORE, localBlockPos));

                toAdd.add(newLocalRef());
                toAdd.add(new VarInsnNode(Opcodes.ASTORE, localBlockState));

                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockPos));
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, 2)); // BlockPos
                toAdd.add(initLocalRef());

                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockState));
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, blockStateIndex));
                toAdd.add(initLocalRef());

                toAdd.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, 2)); // BlockPos
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockPos));
                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockState));
                toAdd.add(
                    ASMAPI.buildMethodCall(
                        "whocraft/tardis_refined/common/block/shell/RedirectBlock",
                        "handleClip",
                        "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lcom/llamalad7/mixinextras/sugar/ref/LocalRef;Lcom/llamalad7/mixinextras/sugar/ref/LocalRef;)V",
                        ASMAPI.MethodType.STATIC
                    )
                );

                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockState));
                toAdd.add(disposeLocalRef());
                toAdd.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/world/level/block/state/BlockState"));
                toAdd.add(new VarInsnNode(Opcodes.ASTORE, blockStateIndex));

                toAdd.add(new VarInsnNode(Opcodes.ALOAD, localBlockPos));
                toAdd.add(disposeLocalRef());
                toAdd.add(new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/core/BlockPos"));
                toAdd.add(new VarInsnNode(Opcodes.ASTORE, 2)); // BlockPos

                for (var index = 0; index < method.instructions.size(); index++) {
                    var i = method.instructions.get(index);
                    if (i.name == ASMAPI.mapMethod("m_6425_") && i.owner == "net/minecraft/world/level/BlockGetter" && i.desc == "(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;") {
                        method.instructions.insertBefore(i, toAdd);
                        break;
                    }
                }

                return method;
            }
        }
    }
}