package net.minecraft.entity.passive;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityChicken extends EntityAnimal {

    private static final Set<Item> field_184761_bD = Sets.newHashSet(new Item[] { Items.field_151014_N, Items.field_151081_bc, Items.field_151080_bb, Items.field_185163_cU});
    public float field_70886_e;
    public float field_70883_f;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;
    public int field_70887_j;
    public boolean field_152118_bv;

    public EntityChicken(World world) {
        super(world);
        this.func_70105_a(0.4F, 0.7F);
        this.field_70887_j = this.field_70146_Z.nextInt(6000) + 6000;
        this.func_184644_a(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void func_184651_r() {
        this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.4D));
        this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
        this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.0D, false, EntityChicken.field_184761_bD));
        this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.1D));
        this.field_70714_bg.func_75776_a(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.field_70714_bg.func_75776_a(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(7, new EntityAILookIdle(this));
    }

    @Override
    public float func_70047_e() {
        return this.field_70131_O;
    }

    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    }

    @Override
    public void func_70636_d() {
        // CraftBukkit start
        if (this.func_152116_bZ()) {
            this.field_82179_bU = !this.func_70692_ba();
        }
        // CraftBukkit end
        super.func_70636_d();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.field_70883_f;
        this.field_70883_f = (float) (this.field_70883_f + (this.field_70122_E ? -1 : 4) * 0.3D);
        this.field_70883_f = MathHelper.func_76131_a(this.field_70883_f, 0.0F, 1.0F);
        if (!this.field_70122_E && this.field_70889_i < 1.0F) {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float) (this.field_70889_i * 0.9D);
        if (!this.field_70122_E && this.field_70181_x < 0.0D) {
            this.field_70181_x *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
        if (!this.field_70170_p.field_72995_K && !this.func_70631_g_() && !this.func_152116_bZ() && --this.field_70887_j <= 0) {
            this.func_184185_a(SoundEvents.field_187665_Y, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
            this.forceDrops = true; // CraftBukkit
            this.func_145779_a(Items.field_151110_aK, 1);
            this.forceDrops = false; // CraftBukkit
            this.field_70887_j = this.field_70146_Z.nextInt(6000) + 6000;
        }

    }

    @Override
    public void func_180430_e(float f, float f1) {}

    @Override
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187660_W;
    }

    @Override
    protected SoundEvent func_184601_bQ(DamageSource damagesource) {
        return SoundEvents.field_187666_Z;
    }

    @Override
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187663_X;
    }

    @Override
    protected void func_180429_a(BlockPos blockposition, Block block) {
        this.func_184185_a(SoundEvents.field_187538_aa, 0.15F, 1.0F);
    }

    @Override
    @Nullable
    protected ResourceLocation func_184647_J() {
        return LootTableList.field_186394_B;
    }

    @Override
    public EntityChicken func_90011_a(EntityAgeable entityageable) {
        return new EntityChicken(this.field_70170_p);
    }

    @Override
    public boolean func_70877_b(ItemStack itemstack) {
        return EntityChicken.field_184761_bD.contains(itemstack.func_77973_b());
    }

    @Override
    protected int func_70693_a(EntityPlayer entityhuman) {
        return this.func_152116_bZ() ? 10 : super.func_70693_a(entityhuman);
    }

    public static void func_189789_b(DataFixer dataconvertermanager) {
        EntityLiving.func_189752_a(dataconvertermanager, EntityChicken.class);
    }

    @Override
    public void func_70037_a(NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        this.field_152118_bv = nbttagcompound.func_74767_n("IsChickenJockey");
        if (nbttagcompound.func_74764_b("EggLayTime")) {
            this.field_70887_j = nbttagcompound.func_74762_e("EggLayTime");
        }

    }

    @Override
    public void func_70014_b(NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.func_74757_a("IsChickenJockey", this.field_152118_bv);
        nbttagcompound.func_74768_a("EggLayTime", this.field_70887_j);
    }

    @Override
    public boolean func_70692_ba() {
        return this.func_152116_bZ() && !this.func_184207_aI();
    }

    @Override
    public void func_184232_k(Entity entity) {
        super.func_184232_k(entity);
        float f = MathHelper.func_76126_a(this.field_70761_aq * 0.017453292F);
        float f1 = MathHelper.func_76134_b(this.field_70761_aq * 0.017453292F);
        float f2 = 0.1F;
        float f3 = 0.0F;

        entity.func_70107_b(this.field_70165_t + 0.1F * f, this.field_70163_u + this.field_70131_O * 0.5F + entity.func_70033_W() + 0.0D, this.field_70161_v - 0.1F * f1);
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase) entity).field_70761_aq = this.field_70761_aq;
        }

    }

    public boolean func_152116_bZ() {
        return this.field_152118_bv;
    }

    public void func_152117_i(boolean flag) {
        this.field_152118_bv = flag;
    }
}
