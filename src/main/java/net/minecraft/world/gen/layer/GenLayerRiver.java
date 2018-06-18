package net.minecraft.world.gen.layer;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;


public class GenLayerRiver extends GenLayer {

    public GenLayerRiver(long i, GenLayer genlayer) {
        super(i);
        super.parent = genlayer;
    }

    public int[] getInts(int i, int j, int k, int l) {
        int i1 = i - 1;
        int j1 = j - 1;
        int k1 = k + 2;
        int l1 = l + 2;
        int[] aint = this.parent.getInts(i1, j1, k1, l1);
        int[] aint1 = IntCache.getIntCache(k * l);

        for (int i2 = 0; i2 < l; ++i2) {
            for (int j2 = 0; j2 < k; ++j2) {
                int k2 = this.riverFilter(aint[j2 + 0 + (i2 + 1) * k1]);
                int l2 = this.riverFilter(aint[j2 + 2 + (i2 + 1) * k1]);
                int i3 = this.riverFilter(aint[j2 + 1 + (i2 + 0) * k1]);
                int j3 = this.riverFilter(aint[j2 + 1 + (i2 + 2) * k1]);
                int k3 = this.riverFilter(aint[j2 + 1 + (i2 + 1) * k1]);

                if (k3 == k2 && k3 == i3 && k3 == l2 && k3 == j3) {
                    aint1[j2 + i2 * k] = -1;
                } else {
                    aint1[j2 + i2 * k] = Biome.getIdForBiome(Biomes.RIVER);
                }
            }
        }

        return aint1;
    }

    private int riverFilter(int i) {
        return i >= 2 ? 2 + (i & 1) : i;
    }
}
