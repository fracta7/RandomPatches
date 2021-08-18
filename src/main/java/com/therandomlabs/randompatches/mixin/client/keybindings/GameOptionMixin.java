package com.therandomlabs.randompatches.mixin.client.keybindings;

import net.minecraft.client.option.KeyBinding;

public interface GameOptionMixin {
	void setKeys(KeyBinding[] keyBindings);
}
