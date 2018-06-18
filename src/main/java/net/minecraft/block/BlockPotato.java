package net.minecraft.block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class BlockPotato extends BlockCrops {

    private static final AxisAlignedBB[] POTATO_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)};

    public BlockPotato() {}

    protected Item getSeed() {
        return Items.POTATO;
    }

    protected Item getCrop() {
        return Items.POTATO;
    }

    public void dropBlockAsItemWithChance(World world, BlockPos blockposition, IBlockState iblockdata, float f, int i) {
        super.dropBlockAsItemWithChance(world, blockposition, iblockdata, f, i);
        if (!world.isRemote) {
            if (this.isMaxAge(iblockdata) && world.rand.nextInt(50) == 0) {
                spawnAsEntity(world, blockposition, new ItemStack(Items.POISONOUS_POTATO));
            }

        }
    }

    public AxisAlignedBB getBoundingBox(IBlockState iblockdata, IBlockAccess iblockaccess, BlockPos blockposition) {
        return BlockPotato.POTATO_AABB[((Integer) iblockdata.getValue(this.getAgeProperty())).intValue()];
    }
}
