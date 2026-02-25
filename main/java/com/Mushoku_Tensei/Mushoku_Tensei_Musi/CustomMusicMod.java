package com.Mushoku_Tensei.Mushoku_Tensei_Music;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod(CustomMusicMod.MOD_ID)
public class CustomMusicMod {
    public static final String MOD_ID = "mushoku_tensei";
    public CustomMusicMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModSounds.SOUND_EVENTS.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, com.Mushoku_Tensei.Mushoku_Tensei_Music.ModConfig.SPEC);
    }
}