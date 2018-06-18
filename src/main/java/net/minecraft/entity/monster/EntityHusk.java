package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityHusk extends EntityZombie {

    public EntityHusk(World world) {
        super(world);
    }

    public static void registerFixesHusk(DataFixer dataconvertermanager) {
        EntityLiving.registerFixesMob(dataconvertermanager, EntityHusk.class);
    }

    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HUSK_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damagesource) {
        return SoundEvents.ENTITY_HUSK_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HUSK_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_HUSK_STEP;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_HUSK;
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);

        if (flag && this.getHeldItemMainhand().isEmpty() && entity instanceof EntityLivingBase) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 140 * (int) f));
        }

        return flag;
    }

    protected ItemStack getSkullDrop() {
        return ItemStack.EMPTY;
    }
}
