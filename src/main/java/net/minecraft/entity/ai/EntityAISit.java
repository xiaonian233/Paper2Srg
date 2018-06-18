package net.minecraft.entity.ai;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;


public class EntityAISit extends EntityAIBase {

    private final EntityTameable tameable;
    private boolean isSitting;

    public EntityAISit(EntityTameable entitytameableanimal) {
        this.tameable = entitytameableanimal;
        this.setMutexBits(5);
    }

    public boolean shouldExecute() {
        if (!this.tameable.isTamed()) {
            return this.isSitting && this.tameable.getAttackTarget() == null; // CraftBukkit - Allow sitting for wild animals
        } else if (this.tameable.isInWater()) {
            return false;
        } else if (!this.tameable.onGround) {
            return false;
        } else {
            EntityLivingBase entityliving = this.tameable.getOwner();

            return entityliving == null ? true : (this.tameable.getDistanceSq(entityliving) < 144.0D && entityliving.getRevengeTarget() != null ? false : this.isSitting);
        }
    }

    public void startExecuting() {
        this.tameable.getNavigator().clearPath();
        this.tameable.setSitting(true);
    }

    public void resetTask() {
        this.tameable.setSitting(false);
    }

    public void setSitting(boolean flag) {
        this.isSitting = flag;
    }
}
