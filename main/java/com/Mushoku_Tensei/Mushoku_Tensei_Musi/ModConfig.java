package com.Mushoku_Tensei.Mushoku_Tensei_Music;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue MUSIC_VOLUME;

    static {
        BUILDER.push("Music Settings");
        MUSIC_VOLUME = BUILDER
                .comment("音量大小 (0.0 到 1.0，默认 0.2)")
                .defineInRange("musicVolume", 0.2, 0.0, 1.0);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
