package fr4gtastic.justmyrottenluck;

import net.minecraftforge.fml.common.Mod;

import static fr4gtastic.justmyrottenluck.JustMyRottenLuck.DEPENDENCIES;
import static fr4gtastic.justmyrottenluck.JustMyRottenLuck.MOD_ID;
import static fr4gtastic.justmyrottenluck.JustMyRottenLuck.MOD_NAME;
import static fr4gtastic.justmyrottenluck.JustMyRottenLuck.VERSION;

@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class JustMyRottenLuck {

    public static final String MOD_ID = "justmyrottenluck";
    public static final String MOD_NAME = "Just My Rotten Luck";
    public static final String VERSION = "0.0.1";
    public static final String DEPENDENCIES = "required:forge@[14.23.5.2847,);after:terrafirmacraft;after:betterwithmods;after:horsepower";

}
