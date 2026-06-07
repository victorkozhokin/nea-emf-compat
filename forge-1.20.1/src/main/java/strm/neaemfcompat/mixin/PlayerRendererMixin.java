package strm.neaemfcompat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import strm.neaemfcompat.compat.EMFCompat;
import strm.neaemfcompat.util.PoseSnapshot;
import strm.neaemfcompat.util.SavedPoses;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(
            method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            )
    )
    private void neaemfcompat$restoreArmPoseBeforeFirstRender(PoseStack stack, MultiBufferSource buffer, int i, AbstractClientPlayer player, ModelPart armPart, ModelPart sleevePart, CallbackInfo ci) {
        SavedPoses saved = EMFCompat.entitySavedPoses.get(player.getUUID());
        if (saved == null) return;

        PlayerModel<AbstractClientPlayer> model = ((PlayerRenderer) (Object) this).getModel();
        PoseSnapshot snapshot = armPart == model.leftArm ? saved.leftArm() : armPart == model.rightArm ? saved.rightArm() : null;

        if (snapshot != null) {
            snapshot.applyRotation(armPart);
            snapshot.applyRotation(sleevePart);
        }
    }

    @Inject(
            method = "renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/model/geom/ModelPart;Lnet/minecraft/client/model/geom/ModelPart;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/geom/ModelPart;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V",
                    ordinal = 1,
                    shift = At.Shift.BEFORE
            )
    )
    private void neaemfcompat$restoreArmPoseBeforeSecondRender(PoseStack stack, MultiBufferSource buffer, int i, AbstractClientPlayer player, ModelPart armPart, ModelPart sleevePart, CallbackInfo ci) {
        SavedPoses saved = EMFCompat.entitySavedPoses.get(player.getUUID());
        if (saved == null) return;

        PlayerModel<AbstractClientPlayer> model = ((PlayerRenderer) (Object) this).getModel();
        PoseSnapshot snapshot = armPart == model.leftArm ? saved.leftArm() : armPart == model.rightArm ? saved.rightArm() : null;

        if (snapshot != null) {
            snapshot.applyRotation(armPart);
            snapshot.applyRotation(sleevePart);
        }
    }
}
