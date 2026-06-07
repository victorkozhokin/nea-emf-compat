package strm.neaemfcompat.mixin;

import dev.tr7zw.notenoughanimations.animations.hands.NarutoRunningAnimation;
import dev.tr7zw.notenoughanimations.logic.AnimationProvider;
import dev.tr7zw.notenoughanimations.api.BasicAnimation;
import dev.tr7zw.notenoughanimations.animations.fullbody.BurningAnimation;
import dev.tr7zw.notenoughanimations.animations.fullbody.FreezingAnimation;
import dev.tr7zw.notenoughanimations.versionless.animations.BodyPart;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import strm.neaemfcompat.compat.EMFCompat;
import strm.neaemfcompat.util.PoseSnapshot;
import strm.neaemfcompat.util.SavedPoses;

@Mixin(AnimationProvider.class)
public class AnimationProviderMixin {

    @Unique
    private BasicAnimation[] neaemfcompat$animationArray;

    @ModifyVariable(
            method = "applyAnimations",
            at = @At(value = "STORE"),
            ordinal = 0,
            remap = false
    )
    private BasicAnimation[] neaemfcompat$captureAnimationArray(BasicAnimation[] animation) {
        this.neaemfcompat$animationArray = animation;
        return animation;
    }

    @Inject(method = "applyAnimations", at = @At("RETURN"), remap = false)
    private void neaemfcompat$onApplyAnimationsReturn(AbstractClientPlayer entity, PlayerModel model, float delta, float swing, CallbackInfo ci) {
        BasicAnimation[] animation = this.neaemfcompat$animationArray;
        if (animation == null) return;
        if (entity.isSprinting()) {
            boolean hasBurningOrFreezingOrNaruto = false;
            for (BasicAnimation anim : animation) {
                if (anim instanceof BurningAnimation || anim instanceof FreezingAnimation || anim instanceof NarutoRunningAnimation) {
                    hasBurningOrFreezingOrNaruto = true;
                    break;
                }
            }
            if (!hasBurningOrFreezingOrNaruto) {
                EMFCompat.entitySavedPoses.remove(entity.getUUID());
                return;
            }
        }

        boolean leftArm = EMFCompat.shouldPauseForAnimation(animation[BodyPart.LEFT_ARM.ordinal()]);
        boolean rightArm = EMFCompat.shouldPauseForAnimation(animation[BodyPart.RIGHT_ARM.ordinal()]);
        if (!leftArm && !rightArm) {
            EMFCompat.entitySavedPoses.remove(entity.getUUID());
            return;
        }
        PoseSnapshot leftPose = leftArm ? new PoseSnapshot(model.leftArm) : null;
        PoseSnapshot rightPose = rightArm ? new PoseSnapshot(model.rightArm) : null;

        EMFCompat.entitySavedPoses.put(entity.getUUID(), new SavedPoses(leftPose, rightPose));
        EMFCompat.currentFrame++;
    }
}
