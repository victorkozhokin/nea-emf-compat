package strm.neaemfcompat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.entity_model_features.models.animation.EMFAnimationEntityContext;
import traben.entity_model_features.models.animation.state.EMFBipedPose;
import strm.neaemfcompat.compat.EMFCompat;
import strm.neaemfcompat.util.SavedPoses;

import java.util.UUID;

@Mixin(EMFBipedPose.class)
public class EMFBipedPoseMixin {
    @Inject(method = "applyTo", at = @At("RETURN"))
    private void neaemfcompat$restoreArmorPose(BipedEntityModel<?> model, CallbackInfo ci) {
        var state = EMFAnimationEntityContext.getEmfState();
        if (state == null || state.emfEntity() == null) return;
        UUID uuid = state.emfEntity().etf$getUuid();

        var mc = MinecraftClient.getInstance();
        if (mc.player != null && mc.player.getUuid().equals(uuid)
                && mc.options.getPerspective().isFirstPerson()
                && mc.getCameraEntity() == mc.player) {
            return;
        }

        SavedPoses savedPoses = EMFCompat.entitySavedPoses.get(uuid);
        if (savedPoses == null) return;
        if (savedPoses.leftArm() != null) savedPoses.leftArm().applyRotation(model.leftArm);
        if (savedPoses.rightArm() != null) savedPoses.rightArm().applyRotation(model.rightArm);
    }
}
