package com.Mushoku_Tensei.Mushoku_Tensei_Music;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = CustomMusicMod.MOD_ID, value = Dist.CLIENT)
public class MusicHandler {
    private static final Random RANDOM = new Random();
    private static List<SoundEvent> CUSTOM_MUSIC_LIST;
    private static SoundInstance currentCustomMusic;
    private static long nextPlayTimeMs = 0;
    private static boolean isPlaying = false;

    private static final long MIN_INTERVAL_MS = 5 * 60 * 1000;
    private static final long MAX_INTERVAL_MS = 10 * 60 * 1000;

    private static List<SoundEvent> getMusicList() {
        if (CUSTOM_MUSIC_LIST == null) {
            CUSTOM_MUSIC_LIST = new ArrayList<>();
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_1.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_2.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_3.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_4.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_5.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_6.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_7.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_8.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_9.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_10.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_11.get());
            CUSTOM_MUSIC_LIST.add(ModSounds.MUSIC_12.get());

        }
        return CUSTOM_MUSIC_LIST;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.isPaused()) return;

        SoundManager soundManager = mc.getSoundManager();


        if (isPlaying) {
            if (currentCustomMusic == null || !soundManager.isActive(currentCustomMusic)) {

                isPlaying = false;
                currentCustomMusic = null;


                if (RANDOM.nextFloat() < 0.6F) {
                    playRandomCustomMusic();
                } else {
                    nextPlayTimeMs = System.currentTimeMillis() + getRandomIntervalMs();
                }
            }
        }


        if (!isPlaying && System.currentTimeMillis() >= nextPlayTimeMs) {
            playRandomCustomMusic();
        }
    }

    private static void playRandomCustomMusic() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        List<SoundEvent> musicList = getMusicList();
        if (musicList.isEmpty()) return;

        SoundManager soundManager = mc.getSoundManager();


        if (currentCustomMusic != null) {
            soundManager.stop(currentCustomMusic);
            currentCustomMusic = null;
        }


        SoundEvent randomSound = musicList.get(RANDOM.nextInt(musicList.size()));
        float volume = ModConfig.MUSIC_VOLUME.get().floatValue();

        SoundInstance newMusic = new SimpleSoundInstance(
                randomSound.getLocation(),
                SoundSource.MUSIC,
                volume,
                1.0f,
                RandomSource.create(),
                false,
                0,
                SoundInstance.Attenuation.NONE,
                0.0, 0.0, 0.0,
                true
        );

        currentCustomMusic = newMusic;
        isPlaying = true;
        soundManager.play(newMusic);

        ResourceLocation soundId = randomSound.getLocation();
        String translationKey = "sound." + soundId.getNamespace() + "." + soundId.getPath();
        Component musicName = Component.translatable(translationKey);
        Component nowPlayingMessage = Component.translatable("music.now_playing", musicName);

        if (mc.player != null) {
            mc.player.displayClientMessage(nowPlayingMessage, true);
        }
    }

    private static long getRandomIntervalMs() {
        return MIN_INTERVAL_MS + RANDOM.nextInt((int) (MAX_INTERVAL_MS - MIN_INTERVAL_MS + 1));
    }


    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.getSound();
        if (sound.getSource() == SoundSource.MUSIC &&
                sound.getLocation().getNamespace().equals("minecraft")) {
            event.setSound(null);
        }
    }


    @SubscribeEvent
    public static void onPlayerLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        nextPlayTimeMs = 0;
        isPlaying = false;
        if (currentCustomMusic != null) {
            Minecraft.getInstance().getSoundManager().stop(currentCustomMusic);
            currentCustomMusic = null;
        }
    }


    @SubscribeEvent
    public static void onPlayerLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        if (currentCustomMusic != null) {
            Minecraft.getInstance().getSoundManager().stop(currentCustomMusic);
            currentCustomMusic = null;
        }
        isPlaying = false;
        nextPlayTimeMs = 0;
    }
}

