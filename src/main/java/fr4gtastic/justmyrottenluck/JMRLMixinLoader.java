package fr4gtastic.justmyrottenluck;

import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.MixinLoader;

@MixinLoader
public class JMRLMixinLoader {

    static {
        if (Loader.isModLoaded("betterwithmods")) {
            Mixins.addConfiguration("mixins.bwm.json");
            Logger.LOGGER.info("Better With Mods loaded: initiating mixin configuration.");
        }
        if (Loader.isModLoaded("horsepower")) {
            Mixins.addConfiguration("mixins.horsepower.json");
            Logger.LOGGER.info("HorsePower loaded: initiating mixin configuration.");
        }
    }
}
