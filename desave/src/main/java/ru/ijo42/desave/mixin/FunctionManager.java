package ru.ijo42.desave.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(net.minecraft.advancements.FunctionManager.class)
public class FunctionManager {
    /**
     * @author ijo42
     */
    @Overwrite
    private void loadFunctions() {
    }
}
