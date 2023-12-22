package com.chromanyan.chromagadgets.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {

    public static class Common {

        public final DoubleValue mossyMirrorDamage;
        public final IntValue mossyMirrorWeaknessAmplifier;
        public final IntValue mossyMirrorWeaknessDuration;

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
