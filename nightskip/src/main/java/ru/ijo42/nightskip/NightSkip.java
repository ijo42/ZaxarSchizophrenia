package ru.ijo42.nightskip;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.stream.Collectors;

@Mod(
        modid = NightSkip.MOD_ID,
        name = NightSkip.MOD_NAME,
        version = NightSkip.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
@Mod.EventBusSubscriber(value = Side.SERVER, modid = NightSkip.MOD_ID)
public class NightSkip {

    public static final String MOD_ID = "nightskip";
    public static final String MOD_NAME = "NightSkip";
    public static final String VERSION = "1.0-SNAPSHOT";
    /**
     * This is the instance of your mod as created by Forge. It will never be null.
     */
    @Mod.Instance(MOD_ID)
    public static NightSkip INSTANCE;
    public static Configuration config;
    public static Logger logger;

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent ev) {
        World world = ev.getEntityLiving().getEntityWorld();
        if (!world.playerEntities.isEmpty()) {
            int spectators = 0;
            int sleeping = 1;

            for (EntityPlayer entityplayer : world.playerEntities) {
                if (entityplayer.isSpectator()) {
                    ++spectators;
                } else if (entityplayer.isPlayerSleeping()) {
                    ++sleeping;
                }
            }

            if (sleeping > 0 && sleeping >= Config.playersToSkipNight - spectators) {
                if (world.getGameRules().getBoolean("doDaylightCycle")) {
                    long k = world.getWorldTime() + 24000L;
                    world.setWorldTime(k - k % 24000L);
                }

                world.playerEntities.stream().filter(EntityPlayer::isPlayerSleeping)
                        .collect(Collectors.toList()).forEach(entityPlayer ->
                        entityPlayer.wakeUpPlayer(false, false, true));

                if (world.getGameRules().getBoolean("doWeatherCycle")) {
                    world.provider.resetRainAndThunder();
                }
            }
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), NightSkip.MOD_ID + ".cfg"));
        logger = e.getModLog();
        Config.readConfig();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static class Config {

        private static final String CATEGORY_GENERAL = "general";

        public static int playersToSkipNight = 1;

        public static void readConfig() {
            Configuration cfg = NightSkip.config;
            try {
                cfg.load();
                initGeneralConfig(cfg);
            } catch (Exception e1) {
                NightSkip.logger.log(Level.ERROR, "Problem loading config file!", e1);
            } finally {
                if (cfg.hasChanged()) {
                    cfg.save();
                }
            }
        }

        private static void initGeneralConfig(Configuration cfg) {
            cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
            playersToSkipNight = cfg.getInt("playersToSkipNight", CATEGORY_GENERAL, playersToSkipNight, 1,
                    Integer.MAX_VALUE, "Count players need to skip night");
        }
    }
}
