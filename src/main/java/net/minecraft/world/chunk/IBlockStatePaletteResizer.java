package net.minecraft.world.chunk;
import net.minecraft.block.state.IBlockState;


interface IBlockStatePaletteResizer {

    int onResize(int i, IBlockState iblockdata);
}
