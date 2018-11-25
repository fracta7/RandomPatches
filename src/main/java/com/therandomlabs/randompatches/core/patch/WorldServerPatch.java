package com.therandomlabs.randompatches.core.patch;

import com.therandomlabs.randompatches.core.Patch;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

public final class WorldServerPatch extends Patch {
	@Override
	public void apply(ClassNode node) {
		final MethodNode method = findMethod(node, "<init>");

		TypeInsnNode createTeleporter = null;
		MethodInsnNode initTeleporter = null;

		for(int i = 0; i < method.instructions.size(); i++) {
			final AbstractInsnNode instruction = method.instructions.get(i);

			if(createTeleporter == null) {
				if(instruction.getOpcode() == Opcodes.NEW) {
					createTeleporter = (TypeInsnNode) instruction;

					if(!"net/minecraft/world/Teleporter".equals(createTeleporter.desc)) {
						createTeleporter = null;
					}

					continue;
				}
			}

			if(instruction.getOpcode() == Opcodes.INVOKESPECIAL) {
				initTeleporter = (MethodInsnNode) instruction;

				if("net/minecraft/world/Teleporter".equals(initTeleporter.owner)) {
					break;
				}

				initTeleporter = null;
			}
		}

		createTeleporter.desc = "com/therandomlabs/verticalendportals/util/VEPTeleporter";
		initTeleporter.owner = "com/therandomlabs/verticalendportals/util/VEPTeleporter";
	}
}
