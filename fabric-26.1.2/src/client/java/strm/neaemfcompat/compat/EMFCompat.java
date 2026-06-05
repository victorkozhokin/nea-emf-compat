package strm.neaemfcompat.compat;

import dev.tr7zw.notenoughanimations.api.BasicAnimation;
import dev.tr7zw.notenoughanimations.animations.fullbody.BurningAnimation;
import dev.tr7zw.notenoughanimations.animations.fullbody.FreezingAnimation;
import dev.tr7zw.notenoughanimations.animations.fullbody.HorseAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.BoatAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.EatDrinkAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.HugAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.ItemSwapAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.LookAtItemAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.MapHoldingAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.NarutoRunningAnimation;
import dev.tr7zw.notenoughanimations.animations.hands.PetAnimation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import strm.neaemfcompat.util.SavedPoses;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EMFCompat {

    public static final Map<UUID, SavedPoses> entitySavedPoses = new HashMap<>();
    public static long currentFrame = 0;

    public static boolean shouldPauseForAnimation(BasicAnimation anim) {
        if (anim == null) return false;
        return anim instanceof BoatAnimation
                || anim instanceof HorseAnimation
                || anim instanceof EatDrinkAnimation
                || anim instanceof HugAnimation
                || anim instanceof ItemSwapAnimation
                || anim instanceof MapHoldingAnimation
                || anim instanceof LookAtItemAnimation
                || anim instanceof PetAnimation
                || anim instanceof NarutoRunningAnimation
                || anim instanceof BurningAnimation
                || anim instanceof FreezingAnimation;

    }
}