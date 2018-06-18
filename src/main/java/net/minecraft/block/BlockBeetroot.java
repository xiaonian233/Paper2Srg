package net.minecraft.block;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBeetroot extends BlockCrops {

    public static final PropertyInteger BEETROOT_AGE = PropertyInteger.create("age", 0, 3);
    private static final AxisAlignedBB[] BEETROOT_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D)};

    public BlockBeetroot() {}

    protected PropertyInteger getAgeProperty() {
        return BlockBeetroot.BEETROOT_AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected Item getSeed() {
        return Items.BEETROOT_SEEDS;
    }

    protected Item getCrop() {
        return Items.BEETROOT;
    }

    public void updateTick(World world, BlockPos blockposition, IBlockState iblockdata, Random random) {
        if (random.nextInt(3) == 0) {
            this.checkAndDropBlock(world, blockposition, iblockdata);
        } else {
            super.updateTick(world, blockposition, iblockdata, random);
        }

    }

    protected int getBonemealAgeIncrease(World world) {
        return super.getBonemealAgeIncrease(world) / 3;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { BlockBeetroot.BEETROOT_AGE});
    }

    public AxisAlignedBB getBoundingBox(IBlockState iblockdata, IBlockAccess iblockaccess, BlockPos blockposition) {
        return BlockBeetroot.BEETROOT_AABB[((Integer) iblockdata.getValue(this.getAgeProperty())).intValue()];
    }
}