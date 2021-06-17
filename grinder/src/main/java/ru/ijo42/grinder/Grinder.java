package ru.ijo42.grinder;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = Grinder.MOD_ID,
        name = Grinder.MOD_NAME,
        version = Grinder.VERSION
)
public class Grinder {

    public static final String MOD_ID = "grinder";
    public static final String MOD_NAME = "Grinder";
    public static final String VERSION = "1.0-SNAPSHOT";

    @Mod.Instance(MOD_ID)
    public static Grinder INSTANCE;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        GameRegistry.registerTileEntity(GrinderTileEntity.class, new ResourceLocation(MOD_ID, "grinder"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiProxy());
    }

    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks {
        public static final GrinderBlock grinder = null;
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {
            //noinspection ConstantConditions
            event.getRegistry().register(
                    new ItemBlock(Blocks.grinder)
                            .setTranslationKey("grinder")
                            .setCreativeTab(CreativeTabs.REDSTONE)
                            .setRegistryName(Blocks.grinder.getRegistryName())
            );
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(new GrinderBlock());
            GameRegistry.registerTileEntity(GrinderTileEntity.class, new ResourceLocation(MOD_ID, "grinder"));
        }
    }

}
