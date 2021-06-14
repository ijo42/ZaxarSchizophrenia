package ru.ijo42.desave.mixin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.IOException;

@Mixin(net.minecraft.world.chunk.storage.AnvilChunkLoader.class)
public class AnvilChunkLoader {
    /**
     * @author ijo42
     */
    @Overwrite
    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }

    /**
     * @author ijo42
     */
    @Overwrite
    private void writeChunkData(ChunkPos pos, NBTTagCompound compound) throws IOException {

    }
}
