package ru.ijo42.desave.mixin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.io.File;

@Mixin(net.minecraft.world.chunk.storage.AnvilSaveHandler.class)
public class AnvilSaveHandler {
    /**
     * @author ijo42
     */
    @Overwrite
    public void saveWorldInfoWithPlayer(WorldInfo worldInformation, @Nullable NBTTagCompound tagCompound) {

    }

    @Redirect(method = "getChunkLoader", at = @At(value = "INVOKE", target = "Ljava/io/File;mkdirs()Z"))
    private boolean adjustMkdir(File file) {
        return false;
    }
}
