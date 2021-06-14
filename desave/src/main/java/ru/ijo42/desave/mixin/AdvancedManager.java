package ru.ijo42.desave.mixin;

import com.google.common.collect.Maps;
import net.minecraft.advancements.Advancement;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Map;

@Mixin(net.minecraft.advancements.AdvancementManager.class)
public class AdvancedManager {
    /**
     * @author ijo42
     */
    @Overwrite
    private Map<ResourceLocation, Advancement.Builder> loadCustomAdvancements() {
        return Maps.newHashMap();
    }
}
