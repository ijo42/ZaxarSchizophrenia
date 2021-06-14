package ru.ijo42.invisibleglass;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

public class InvisibleGlassBlock extends BlockGlass {
    public InvisibleGlassBlock() {
        super(Material.GLASS, false);
        this.setHardness(0.3F).setRegistryName(InvisibleGlass.MOD_ID, "glass")
                .setTranslationKey("glass");
    }
}
