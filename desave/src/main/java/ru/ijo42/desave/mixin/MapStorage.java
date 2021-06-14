package ru.ijo42.desave.mixin;

import net.minecraft.world.storage.WorldSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(net.minecraft.world.storage.MapStorage.class)
public class MapStorage {

    /**
     * @author ijo42
     */
    @Overwrite
    private void saveData(WorldSavedData data) {
    }

}
