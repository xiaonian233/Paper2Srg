package net.minecraft.block.material;

public class MaterialTransparent extends Material {

    public MaterialTransparent(MapColor materialmapcolor) {
        super(materialmapcolor);
        this.setReplaceable();
    }

    public boolean isSolid() {
        return false;
    }

    public boolean blocksLight() {
        return false;
    }

    public boolean blocksMovement() {
        return false;
    }
}