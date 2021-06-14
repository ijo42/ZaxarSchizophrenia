package ru.ijo42.desave.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;
import java.io.File;

@Mixin(net.minecraft.world.storage.SaveHandler.class)
public class SaveHandler {

    /**
     * @author ijo42
     */
    @Overwrite
    public void saveWorldInfoWithPlayer(WorldInfo worldInformation, @Nullable NBTTagCompound tagCompound) {

    }

    /**
     * @author ijo42
     */
    @Overwrite
    public void writePlayerData(EntityPlayer player) {

    }

    /**
     * @author ijo42
     */
    @Overwrite
    private void setSessionLock() {

    }

    /**
     * @author ijo42
     */
    @Overwrite
    public void checkSessionLock() throws MinecraftException {

    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;mkdirs()Z"))
    public boolean adjustMkdir(File file) {
        return false;
    }

}
