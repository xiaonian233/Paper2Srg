package net.minecraft.entity.ai;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


public class EntityAIBeg extends EntityAIBase {

    private final EntityWolf wolf;
    private EntityPlayer player;
    private final World world;
    private final float minPlayerDistance;
    private int timeoutCounter;

    public EntityAIBeg(EntityWolf entitywolf, float f) {
        this.wolf = entitywolf;
        this.world = entitywolf.world;
        this.minPlayerDistance = f;
        this.setMutexBits(2);
    }

    public boolean shouldExecute() {
        this.player = this.world.getClosestPlayerToEntity(this.wolf, (double) this.minPlayerDistance);
        return this.player == null ? false : this.hasTemptationItemInHand(this.player);
    }

    public boolean shouldContinueExecuting() {
        return !this.player.isEntityAlive() ? false : (this.wolf.getDistanceSq(this.player) > (double) (this.minPlayerDistance * this.minPlayerDistance) ? false : this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player));
    }

    public void startExecuting() {
        this.wolf.setBegging(true);
        this.timeoutCounter = 40 + this.wolf.getRNG().nextInt(40);
    }

    public void resetTask() {
        this.wolf.setBegging(false);
        this.player = null;
    }

    public void updateTask() {
        this.wolf.getLookHelper().setLookPosition(this.player.posX, this.player.posY + (double) this.player.getEyeHeight(), this.player.posZ, 10.0F, (float) this.wolf.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(EntityPlayer entityhuman) {
        EnumHand[] aenumhand = EnumHand.values();
        int i = aenumhand.length;

        for (int j = 0; j < i; ++j) {
            EnumHand enumhand = aenumhand[j];
            ItemStack itemstack = entityhuman.getHeldItem(enumhand);

            if (this.wolf.isTamed() && itemstack.getItem() == Items.BONE) {
                return true;
            }

            if (this.wolf.isBreedingItem(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
