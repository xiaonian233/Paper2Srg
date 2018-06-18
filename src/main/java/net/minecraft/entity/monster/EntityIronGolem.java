package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import org;

public class EntityIronGolem extends EntityGolem {

    protected static final DataParameter<Byte> PLAYER_CREATED = EntityDataManager.createKey(EntityIronGolem.class, DataSerializers.BYTE);
    private int homeCheckTimer;
    @Nullable
    Village village;
    private int attackTimer;
    private int holdRoseTick;

    public EntityIronGolem(World world) {
        super(world);
        this.setSize(1.4F, 2.7F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, new Predicate() {
            public boolean a(@Nullable EntityLiving entityinsentient) {
                return entityinsentient != null && IMob.VISIBLE_MOB_SELECTOR.apply(entityinsentient) && !(entityinsentient instanceof EntityCreeper);
            }

            public boolean apply(@Nullable Object object) {
                return this.a((EntityLiving) object);
            }
        }));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityIronGolem.PLAYER_CREATED, Byte.valueOf((byte) 0));
    }

    protected void updateAITasks() {
        if (--this.homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.village = this.world.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
            if (this.village == null) {
                this.detachHome();
            } else {
                BlockPos blockposition = this.village.getCenter();

                this.setHomePosAndDistance(blockposition, (int) ((float) this.village.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITasks();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    protected int decreaseAirSupply(int i) {
        return i;
    }

    protected void collideWithEntity(Entity entity) {
        if (entity instanceof IMob && !(entity instanceof EntityCreeper) && this.getRNG().nextInt(20) == 0) {
            this.setGoalTarget((EntityLivingBase) entity, org.bukkit.event.entity.EntityTargetLivingEntityEvent.TargetReason.COLLISION, true); // CraftBukkit - set reason
        }

        super.collideWithEntity(entity);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if (this.holdRoseTick > 0) {
            --this.holdRoseTick;
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor(this.posZ);
            IBlockState iblockdata = this.world.getBlockState(new BlockPos(i, j, k));

            if (iblockdata.getMaterial() != Material.AIR) {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double) this.rand.nextFloat() - 0.5D) * (double) this.width, 4.0D * ((double) this.rand.nextFloat() - 0.5D), 0.5D, ((double) this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockdata)});
            }
        }

    }

    public boolean canAttackClass(Class<? extends EntityLivingBase> oclass) {
        return this.isPlayerCreated() && EntityPlayer.class.isAssignableFrom(oclass) ? false : (oclass == EntityCreeper.class ? false : super.canAttackClass(oclass));
    }

    public static void registerFixesIronGolem(DataFixer dataconvertermanager) {
        EntityLiving.registerFixesMob(dataconvertermanager, EntityIronGolem.class);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
    }

    public boolean attackEntityAsMob(Entity entity) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) (7 + this.rand.nextInt(15)));

        if (flag) {
            entity.motionY += 0.4000000059604645D;
            this.applyEnchantments((EntityLivingBase) this, entity);
        }

        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    public Village getVillage() {
        return this.village;
    }

    public void setHoldingRose(boolean flag) {
        if (flag) {
            this.holdRoseTick = 400;
            this.world.setEntityState(this, (byte) 11);
        } else {
            this.holdRoseTick = 0;
            this.world.setEntityState(this, (byte) 34);
        }

    }

    protected SoundEvent getHurtSound(DamageSource damagesource) {
        return SoundEvents.ENTITY_IRONGOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRONGOLEM_DEATH;
    }

    protected void playStepSound(BlockPos blockposition, Block block) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_IRON_GOLEM;
    }

    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }

    public boolean isPlayerCreated() {
        return (((Byte) this.dataManager.get(EntityIronGolem.PLAYER_CREATED)).byteValue() & 1) != 0;
    }

    public void setPlayerCreated(boolean flag) {
        byte b0 = ((Byte) this.dataManager.get(EntityIronGolem.PLAYER_CREATED)).byteValue();

        if (flag) {
            this.dataManager.set(EntityIronGolem.PLAYER_CREATED, Byte.valueOf((byte) (b0 | 1)));
        } else {
            this.dataManager.set(EntityIronGolem.PLAYER_CREATED, Byte.valueOf((byte) (b0 & -2)));
        }

    }

    public void onDeath(DamageSource damagesource) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.village != null) {
            this.village.modifyPlayerReputation(this.attackingPlayer.getName(), -5);
        }

        super.onDeath(damagesource);
    }
}