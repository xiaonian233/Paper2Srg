package net.minecraft.block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockHay extends BlockRotatedPillar {

    public BlockHay() {
        super(Material.GRASS, MapColor.YELLOW);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHay.AXIS, EnumFacing.Axis.Y));
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void onFallenUpon(World world, BlockPos blockposition, Entity entity, float f) {
        entity.fall(f, 0.2F);
    }
}