package ru.ijo42.invisibleglass;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = InvisibleGlass.MOD_ID,
        name = InvisibleGlass.MOD_NAME,
        version = InvisibleGlass.VERSION
)
public class InvisibleGlass {

    public static final String MOD_ID = "invisibleglass";
    public static final String MOD_NAME = "InvisibleGlass";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static InvisibleGlass INSTANCE;

    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
        public static final InvisibleGlassBlock glass = null;
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            //noinspection ConstantConditions
            event.getRegistry().register(new ItemBlock(Blocks.glass)
                    .setRegistryName(MOD_ID, "glass")
                    .setTranslationKey("glass")
                    .setCreativeTab(CreativeTabs.BUILDING_BLOCKS));
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new InvisibleGlassBlock());
        }
    }

}
