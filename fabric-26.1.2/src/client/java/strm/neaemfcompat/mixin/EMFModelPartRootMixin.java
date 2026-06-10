package strm.neaemfcompat.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.entity_model_features.models.animation.EMFAnimationEntityContext;
import traben.entity_model_features.models.parts.EMFModelPartRoot;
import traben.entity_model_features.models.parts.EMFModelPartVanilla;
import strm.neaemfcompat.compat.EMFCompat;
import strm.neaemfcompat.util.SavedPoses;

import java.util.UUID;

@Mixin(EMFModelPartRoot.class)
public class EMFModelPartRootMixin {

    @Unique
    private long neaemfcompat$lastRestoreFrame = -1;

    @Inject(method = "animate", at = @At("RETURN"))
    private void neaemfcompat$restorePosesAfterAnimate(CallbackInfo ci) {
        if (neaemfcompat$lastRestoreFrame == EMFCompat.currentFrame) return;
        neaemfcompat$lastRestoreFrame = EMFCompat.currentFrame;

        var state = EMFAnimationEntityContext.getEmfState();
        if (state == null || state.emfEntity() == null) return;

        UUID uuid = state.emfEntity().etf$getUuid();

        var mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.getUUID().equals(uuid) && mc.options.getCameraType().isFirstPerson() && mc.getCameraEntity() == mc.player) {
            return;
        }

        SavedPoses savedPoses = EMFCompat.entitySavedPoses.get(uuid);
        if (savedPoses == null) return;

        EMFModelPartRoot root = (EMFModelPartRoot) (Object) this;
        EMFModelPartVanilla leftArmPart = null;
        EMFModelPartVanilla rightArmPart = null;
        EMFModelPartVanilla leftSleeve = null;
        EMFModelPartVanilla rightSleeve = null;

        // исправление бага с рукавом, который возникает из-за снапшотского костыля
        for (EMFModelPartVanilla part : root.getAllVanillaPartsEMF()) {
            String shortName = part.toStringShort();
            if ("[vanilla part left_arm]".equals(shortName)) {
                if (savedPoses.leftArm() != null) {
                    savedPoses.leftArm().applyRotation(part);
                    leftArmPart = part;
                }
            } else if ("[vanilla part right_arm]".equals(shortName)) {
                if (savedPoses.rightArm() != null) {
                    savedPoses.rightArm().applyRotation(part);
                    rightArmPart = part;
                }
            } else if ("[vanilla part left_sleeve]".equals(shortName)) {
                leftSleeve = part;
            } else if ("[vanilla part right_sleeve]".equals(shortName)) {
                rightSleeve = part;
            }
        }

        if (leftArmPart != null && leftSleeve != null) {
            var vanillaArm = leftArmPart.getVanillaModelPartsOfCurrentState();
            var vanillaSleeve = leftSleeve.getVanillaModelPartsOfCurrentState();
            if (vanillaArm != null && vanillaSleeve != null && vanillaArm != leftArmPart) {
                savedPoses.leftArm().applyRotation(vanillaSleeve);
            } else {
                savedPoses.leftArm().applyRotation(leftSleeve);
            }
        }
        if (rightArmPart != null && rightSleeve != null) {
            var vanillaArm = rightArmPart.getVanillaModelPartsOfCurrentState();
            var vanillaSleeve = rightSleeve.getVanillaModelPartsOfCurrentState();
            if (vanillaArm != null && vanillaSleeve != null && vanillaArm != rightArmPart) {
                savedPoses.rightArm().applyRotation(vanillaSleeve);
            } else {
                savedPoses.rightArm().applyRotation(rightSleeve);
            }

        }
    }
}
