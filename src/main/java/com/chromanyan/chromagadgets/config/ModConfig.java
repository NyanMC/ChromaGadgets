package com.chromanyan.chromagadgets.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {

    public static class Common {

        public final DoubleValue mossyMirrorDamage;
        public final IntValue mossyMirrorWeaknessAmplifier;
        public final IntValue mossyMirrorWeaknessDuration;

        public final DoubleValue defaultFriction;
        public final DoubleValue slipperyFriction;
        public final BooleanValue ignoreSpeedyBlocks;

        Common(ForgeConfigSpec.Builder builder) {
            builder.push("GadgetSettings");
                builder.push("MossyMirrorSettings");
                    mossyMirrorDamage = builder
                            .comment("The percentage of the player's max health dealt when using the Mossy Mirror.")
                            .defineInRange("mossyMirrorDamage", 0.25, 0, 1);
                    mossyMirrorWeaknessAmplifier = builder
                            .comment("The amplifier for the Weakness effect applied when using the Mossy Mirror.")
                            .defineInRange("mossyMirrorWeaknessAmplifier", 1, 0, 255);
                    mossyMirrorWeaknessDuration = builder
                            .comment("The duration for the Weakness effect applied when using the Mossy Mirror.")
                            .defineInRange("mossyMirrorWeaknessDuration", 2400, 0, Integer.MAX_VALUE);
                builder.pop();
            builder.pop();

            builder.push("EnchantmentSettings");
                defaultFriction = builder
                        .comment("The \"default\" friction of a block. This is used by the Friction enchantment as the maximum friction for all blocks the player with the enchantment stands on. Higher values mean more slippery.")
                        .defineInRange("defaultFriction", 0.6, 0.01, 1);
                slipperyFriction = builder
                        .comment("The friction for slippery blocks. This is used by the Curse of Slipperiness as the minimum friction value for all blocks the player with the curse stands on. Default value is equivalent to the friction of ice.")
                                .defineInRange("slipperyFriction", 0.98, 0.01, 1);
                ignoreSpeedyBlocks = builder
                        .comment("Whether the Curse of Slipperiness should ignore blocks with a speedFactor greater than 1. This prevents a bug relating to achieving huge amounts of speed, but can be turned off for the purpose of fun. I will not help with issues relating to the Curse of Slipperiness if this is set to false.")
                                .define("ignoreSpeedyBlocks", true);
            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
