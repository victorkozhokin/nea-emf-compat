package strm.neaemfcompat.util;

import net.minecraft.client.model.geom.ModelPart;

//как только traben ответит по поводу asm, и бага с отключением анимаций на частях модели, можно будет убрать

public class PoseSnapshot {
    public final float xRot, yRot, zRot;
    public final float x, y, z;
    public final float xScale, yScale, zScale;
    public final boolean visible;
    public final boolean skipDraw;

    public PoseSnapshot(ModelPart part) {
        this.xRot = part.xRot;
        this.yRot = part.yRot;
        this.zRot = part.zRot;
        this.x = part.x;
        this.y = part.y;
        this.z = part.z;
        this.xScale = part.xScale;
        this.yScale = part.yScale;
        this.zScale = part.zScale;
        this.visible = part.visible;
        this.skipDraw = part.skipDraw;
    }

    public void apply(ModelPart part) {
        part.xRot = this.xRot;
        part.yRot = this.yRot;
        part.zRot = this.zRot;
        part.x = this.x;
        part.y = this.y;
        part.z = this.z;
        part.xScale = this.xScale;
        part.yScale = this.yScale;
        part.zScale = this.zScale;
        part.visible = this.visible;
        part.skipDraw = this.skipDraw;
    }

    /**
     * Applies only rotation values, leaving translation, scale and visibility intact.
     * Used to sync arm rotation with NEA while letting EMF control arm position.
     */
    public void applyRotation(ModelPart part) {
        part.xRot = this.xRot;
        part.yRot = this.yRot;
        part.zRot = this.zRot;
    }

}
