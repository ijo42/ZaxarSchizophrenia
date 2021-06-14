package ru.ijo42.desave;

import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = DeSave.MOD_ID,
        name = DeSave.MOD_NAME,
        version = DeSave.VERSION
)
public class DeSave {

    public static final String MOD_ID = "desave";
    public static final String MOD_NAME = "DeSave";
    public static final String VERSION = "1.0-SNAPSHOT";

    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static DeSave INSTANCE;

}
