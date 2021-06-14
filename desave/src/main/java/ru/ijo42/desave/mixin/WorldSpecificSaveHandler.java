package ru.ijo42.desave.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(net.minecraftforge.common.WorldSpecificSaveHandler.class)
public class WorldSpecificSaveHandler {
    @Redirect(method = "getMapFileFromName", at = @At(value = "INVOKE", target = "Ljava/io/File;mkdirs()Z"))
    public boolean adjustMkdir(File file) {
        return false;
    }
}
