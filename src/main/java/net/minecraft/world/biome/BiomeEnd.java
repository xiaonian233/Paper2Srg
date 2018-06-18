package net.minecraft.world.biome;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;


public class BiomeEnd extends Biome {

    public BiomeEnd(BiomeBase.a biomebase_a) {
        super(biomebase_a);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = Blocks.DIRT.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
        this.decorator = new BiomeEndDecorator();
    }
}
