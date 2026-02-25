package com.Mushoku_Tensei.Mushoku_Tensei_Music;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CustomMusicMod.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_1 = registerSound("music1");
    public static final RegistryObject<SoundEvent> MUSIC_2 = registerSound("music2");
    public static final RegistryObject<SoundEvent> MUSIC_3 = registerSound("music3");
    public static final RegistryObject<SoundEvent> MUSIC_4 = registerSound("music4");
    public static final RegistryObject<SoundEvent> MUSIC_5 = registerSound("music5");
    public static final RegistryObject<SoundEvent> MUSIC_6 = registerSound("music6");
    public static final RegistryObject<SoundEvent> MUSIC_7 = registerSound("music7");
    public static final RegistryObject<SoundEvent> MUSIC_8 = registerSound("music8");
    public static final RegistryObject<SoundEvent> MUSIC_9 = registerSound("music9");
    public static final RegistryObject<SoundEvent> MUSIC_10 = registerSound("music10");
    public static final RegistryObject<SoundEvent> MUSIC_11 = registerSound("music11");
    public static final RegistryObject<SoundEvent> MUSIC_12 = registerSound("music12");
//如果你想添加新音乐在这里按住ctry+d可以复制下一行，把数字改成13即可，MusicHandler里也需要改，还有sounds.json里也是，
// 别忘了语言文件，音乐格式得是ogg才行，想MP3这种不能播放。
    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(CustomMusicMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(loc));
    }
}