package strm.neaemfcompat.util;

import net.minecraft.client.model.ModelPart;

//как только traben ответит по поводу asm, и бага с отключением анимаций на частях модели, можно будет убрать

public class PoseSnapshot {
    public final float pitch, yaw, roll;
    public final float originX, originY, originZ;
    public final float xScale, yScale, zScale;
    public final boolean visible;
    public final boolean hidden;

    public PoseSnapshot(ModelPart part) {
        this.pitch = part.pitch;
        this.yaw = part.yaw;
        this.roll = part.roll;
        this.originX = part.originX;
        this.originY = part.originY;
        this.originZ = part.originZ;
        this.xScale = part.xScale;
        this.yScale = part.yScale;
        this.zScale = part.zScale;
        this.visible = part.visible;
        this.hidden = part.hidden;
    }

    public void apply(ModelPart part) {
        part.pitch = this.pitch;
        part.yaw = this.yaw;
        part.roll = this.roll;
        part.originX = this.originX;
        part.originY = this.originY;
        part.originZ = this.originZ;
        part.xScale = this.xScale;
        part.yScale = this.yScale;
        part.zScale = this.zScale;
        part.visible = this.visible;
        part.hidden = this.hidden;
    }

    public void applyRotation(ModelPart part) {
        part.pitch = this.pitch;
        part.yaw = this.yaw;
        part.roll = this.roll;
    }
}
