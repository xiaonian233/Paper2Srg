package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.InventoryView;

// CraftBukkit start
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.InventoryView;
// CraftBukkit end

public class EntitySheep extends EntityAnimal {

    private static final DataParameter<Byte> field_184774_bv = EntityDataManager.func_187226_a(EntitySheep.class, DataSerializers.field_187191_a);
    private final InventoryCrafting field_90016_e = new InventoryCrafting(new Container() {
        @Override
        public boolean func_75145_c(EntityPlayer entityhuman) {
            return false;
        }

        // CraftBukkit start
        @Override
        public InventoryView getBukkitView() {
            return null; // TODO: O.O
        }
        // CraftBukkit end
    }, 2, 1);
    private static final Map<EnumDyeColor, float[]> field_175514_bm = Maps.newEnumMap(EnumDyeColor.class);
    private int field_70899_e;
    private EntityAIEatGrass field_146087_bs;

    private static float[] func_192020_c(EnumDyeColor enumcolor) {
        float[] afloat = enumcolor.func_193349_f();
        float f = 0.75F;

        return new float[] { afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
    }

    public EntitySheep(World world) {
        super(world);
        this.func_70105_a(0.9F, 1.3F);
        this.field_90016_e.func_70299_a(0, new ItemStack(Items.field_151100_aR));
        this.field_90016_e.func_70299_a(1, new ItemStack(Items.field_151100_aR));
        this.field_90016_e.resultInventory = new InventoryCraftResult(); // CraftBukkit - add result slot for event
    }

    @Override
    protected void func_184651_r() {
        this.field_146087_bs = new EntityAIEatGrass(this);
        this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
        this.field_70714_bg.func_75776_a(1, new EntityAIPanic(this, 1.25D));
        this.field_70714_bg.func_75776_a(2, new EntityAIMate(this, 1.0D));
        this.field_70714_bg.func_75776_a(3, new EntityAITempt(this, 1.1D, Items.field_151015_O, false));
        this.field_70714_bg.func_75776_a(4, new EntityAIFollowParent(this, 1.1D));
        this.field_70714_bg.func_75776_a(5, this.field_146087_bs);
        this.field_70714_bg.func_75776_a(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.field_70714_bg.func_75776_a(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.field_70714_bg.func_75776_a(8, new EntityAILookIdle(this));
    }

    @Override
    protected void func_70619_bc() {
        this.field_70899_e = this.field_146087_bs.func_151499_f();
        super.func_70619_bc();
    }

    @Override
    public void func_70636_d() {
        if (this.field_70170_p.field_72995_K) {
            this.field_70899_e = Math.max(0, this.field_70899_e - 1);
        }

        super.func_70636_d();
    }

    @Override
    protected void func_110147_ax() {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
    }

    @Override
    protected void func_70088_a() {
        super.func_70088_a();
        this.field_70180_af.func_187214_a(EntitySheep.field_184774_bv, Byte.valueOf((byte) 0));
    }

    @Override
    @Nullable
    protected ResourceLocation func_184647_J() {
        if (this.func_70892_o()) {
            return LootTableList.field_186403_K;
        } else {
            switch (this.func_175509_cj()) {
            case WHITE:
            default:
                return LootTableList.field_186404_L;

            case ORANGE:
                return LootTableList.field_186405_M;

            case MAGENTA:
                return LootTableList.field_186406_N;

            case LIGHT_BLUE:
                return LootTableList.field_186407_O;

            case YELLOW:
                return LootTableList.field_186408_P;

            case LIME:
                return LootTableList.field_186409_Q;

            case PINK:
                return LootTableList.field_186410_R;

            case GRAY:
                return LootTableList.field_186411_S;

            case SILVER:
                return LootTableList.field_186412_T;

            case CYAN:
                return LootTableList.field_186413_U;

            case PURPLE:
                return LootTableList.field_186414_V;

            case BLUE:
                return LootTableList.field_186415_W;

            case BROWN:
                return LootTableList.field_186416_X;

            case GREEN:
                return LootTableList.field_186417_Y;

            case RED:
                return LootTableList.field_186418_Z;

            case BLACK:
                return LootTableList.field_186376_aa;
            }
        }
    }

    @Override
    public boolean func_184645_a(EntityPlayer entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.func_184586_b(enumhand);

        if (itemstack.func_77973_b() == Items.field_151097_aZ && !this.func_70892_o() && !this.func_70631_g_()) {
            if (!this.field_70170_p.field_72995_K) {
                // CraftBukkit start
                PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player) entityhuman.getBukkitEntity(), this.getBukkitEntity());
                this.field_70170_p.getServer().getPluginManager().callEvent(event);

                if (event.isCancelled()) {
                    return false;
                }
                // CraftBukkit end

                this.func_70893_e(true);
                int i = 1 + this.field_70146_Z.nextInt(3);

                for (int j = 0; j < i; ++j) {
                    this.forceDrops = true; // CraftBukkit
                    EntityItem entityitem = this.func_70099_a(new ItemStack(Item.func_150898_a(Blocks.field_150325_L), 1, this.func_175509_cj().func_176765_a()), 1.0F);
                    this.forceDrops = false; // CraftBukkit

                    entityitem.field_70181_x += this.field_70146_Z.nextFloat() * 0.05F;
                    entityitem.field_70159_w += (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F;
                    entityitem.field_70179_y += (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F;
                }
            }

            itemstack.func_77972_a(1, entityhuman);
            this.func_184185_a(SoundEvents.field_187763_eJ, 1.0F, 1.0F);
        }

        return super.func_184645_a(entityhuman, enumhand);
    }

    public static void func_189802_b(DataFixer dataconvertermanager) {
        EntityLiving.func_189752_a(dataconvertermanager, EntitySheep.class);
    }

    @Override
    public void func_70014_b(NBTTagCompound nbttagcompound) {
        super.func_70014_b(nbttagcompound);
        nbttagcompound.func_74757_a("Sheared", this.func_70892_o());
        nbttagcompound.func_74774_a("Color", (byte) this.func_175509_cj().func_176765_a());
    }

    @Override
    public void func_70037_a(NBTTagCompound nbttagcompound) {
        super.func_70037_a(nbttagcompound);
        this.func_70893_e(nbttagcompound.func_74767_n("Sheared"));
        this.func_175512_b(EnumDyeColor.func_176764_b(nbttagcompound.func_74771_c("Color")));
    }

    @Override
    protected SoundEvent func_184639_G() {
        return SoundEvents.field_187757_eG;
    }

    @Override
    protected SoundEvent func_184601_bQ(DamageSource damagesource) {
        return SoundEvents.field_187761_eI;
    }

    @Override
    protected SoundEvent func_184615_bR() {
        return SoundEvents.field_187759_eH;
    }

    @Override
    protected void func_180429_a(BlockPos blockposition, Block block) {
        this.func_184185_a(SoundEvents.field_187765_eK, 0.15F, 1.0F);
    }

    public EnumDyeColor func_175509_cj() {
        return EnumDyeColor.func_176764_b(this.field_70180_af.func_187225_a(EntitySheep.field_184774_bv).byteValue() & 15);
    }

    public void func_175512_b(EnumDyeColor enumcolor) {
        byte b0 = this.field_70180_af.func_187225_a(EntitySheep.field_184774_bv).byteValue();

        this.field_70180_af.func_187227_b(EntitySheep.field_184774_bv, Byte.valueOf((byte) (b0 & 240 | enumcolor.func_176765_a() & 15)));
    }

    public boolean func_70892_o() {
        return (this.field_70180_af.func_187225_a(EntitySheep.field_184774_bv).byteValue() & 16) != 0;
    }

    public void func_70893_e(boolean flag) {
        byte b0 = this.field_70180_af.func_187225_a(EntitySheep.field_184774_bv).byteValue();

        if (flag) {
            this.field_70180_af.func_187227_b(EntitySheep.field_184774_bv, Byte.valueOf((byte) (b0 | 16)));
        } else {
            this.field_70180_af.func_187227_b(EntitySheep.field_184774_bv, Byte.valueOf((byte) (b0 & -17)));
        }

    }

    public static EnumDyeColor func_175510_a(Random random) {
        int i = random.nextInt(100);

        return i < 5 ? EnumDyeColor.BLACK : (i < 10 ? EnumDyeColor.GRAY : (i < 15 ? EnumDyeColor.SILVER : (i < 18 ? EnumDyeColor.BROWN : (random.nextInt(500) == 0 ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
    }

    @Override
    public EntitySheep func_90011_a(EntityAgeable entityageable) {
        EntitySheep entitysheep = (EntitySheep) entityageable;
        EntitySheep entitysheep1 = new EntitySheep(this.field_70170_p);

        entitysheep1.func_175512_b(this.func_175511_a(this, entitysheep));
        return entitysheep1;
    }

    @Override
    public void func_70615_aA() {
        // CraftBukkit start
        SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((org.bukkit.entity.Sheep) this.getBukkitEntity());
        this.field_70170_p.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        // CraftBukkit end
        this.func_70893_e(false);
        if (this.func_70631_g_()) {
            this.func_110195_a(60);
        }

    }

    @Override
    @Nullable
    public IEntityLivingData func_180482_a(DifficultyInstance difficultydamagescaler, @Nullable IEntityLivingData groupdataentity) {
        groupdataentity = super.func_180482_a(difficultydamagescaler, groupdataentity);
        this.func_175512_b(func_175510_a(this.field_70170_p.field_73012_v));
        return groupdataentity;
    }

    private EnumDyeColor func_175511_a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
        int i = ((EntitySheep) entityanimal).func_175509_cj().func_176767_b();
        int j = ((EntitySheep) entityanimal1).func_175509_cj().func_176767_b();

        this.field_90016_e.func_70301_a(0).func_77964_b(i);
        this.field_90016_e.func_70301_a(1).func_77964_b(j);
        ItemStack itemstack = CraftingManager.func_82787_a(this.field_90016_e, ((EntitySheep) entityanimal).field_70170_p);
        int k;

        if (itemstack.func_77973_b() == Items.field_151100_aR) {
            k = itemstack.func_77960_j();
        } else {
            k = this.field_70170_p.field_73012_v.nextBoolean() ? i : j;
        }

        return EnumDyeColor.func_176766_a(k);
    }

    @Override
    public float func_70047_e() {
        return 0.95F * this.field_70131_O;
    }

    static {
        EnumDyeColor[] aenumcolor = EnumDyeColor.values();
        int i = aenumcolor.length;

        for (int j = 0; j < i; ++j) {
            EnumDyeColor enumcolor = aenumcolor[j];

            EntitySheep.field_175514_bm.put(enumcolor, func_192020_c(enumcolor));
        }

        EntitySheep.field_175514_bm.put(EnumDyeColor.WHITE, new float[] { 0.9019608F, 0.9019608F, 0.9019608F});
    }
}
