package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer {

    public GenLayerIsland(long i) {
        super(i);
    }

    public int[] func_75904_a(int i, int j, int k, int l) {
        int[] aint = IntCache.func_76445_a(k * l);

        for (int i1 = 0; i1 < l; ++i1) {
            for (int j1 = 0; j1 < k; ++j1) {
                this.func_75903_a((long) (i + j1), (long) (j + i1));
                aint[j1 + i1 * k] = this.func_75902_a(10) == 0 ? 1 : 0;
            }
        }

        if (i > -k && i <= 0 && j > -l && j <= 0) {
            aint[-i + -j * k] = 1;
        }

        return aint;
    }
}
