package ru.ijo42.desave.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(net.minecraft.world.chunk.storage.RegionFileCache.class)
public class RegionFileCache {
    @Redirect(method = "createOrLoadRegionFile", at = @At(value = "INVOKE", target = "Ljava/io/File;mkdirs()Z"))
    private static boolean adjustMkdir(File file) {
        return false;
    }

/*    @Redirect(method = {"getRegionFileIfExists", "createOrLoadRegionFile"},
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static <K, V> V adjustCache(Map<K, V> map, K key, V value) {
        return null;
    }*/
}
