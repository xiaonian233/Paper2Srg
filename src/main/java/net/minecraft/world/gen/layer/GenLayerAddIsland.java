package net.minecraft.world.gen.layer;

public class GenLayerAddIsland extends GenLayer {

    public GenLayerAddIsland(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
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
                int k2 = aint[j2 + 0 + (i2 + 0) * k1];
                int l2 = aint[j2 + 2 + (i2 + 0) * k1];
                int i3 = aint[j2 + 0 + (i2 + 2) * k1];
                int j3 = aint[j2 + 2 + (i2 + 2) * k1];
                int k3 = aint[j2 + 1 + (i2 + 1) * k1];

                this.initChunkSeed((long) (j2 + i), (long) (i2 + j));
                if (k3 == 0 && (k2 != 0 || l2 != 0 || i3 != 0 || j3 != 0)) {
                    int l3 = 1;
                    int i4 = 1;

                    if (k2 != 0 && this.nextInt(l3++) == 0) {
                        i4 = k2;
                    }

                    if (l2 != 0 && this.nextInt(l3++) == 0) {
                        i4 = l2;
                    }

                    if (i3 != 0 && this.nextInt(l3++) == 0) {
                        i4 = i3;
                    }

                    if (j3 != 0 && this.nextInt(l3++) == 0) {
                        i4 = j3;
                    }

                    if (this.nextInt(3) == 0) {
                        aint1[j2 + i2 * k] = i4;
                    } else if (i4 == 4) {
                        aint1[j2 + i2 * k] = 4;
                    } else {
                        aint1[j2 + i2 * k] = 0;
                    }
                } else if (k3 > 0 && (k2 == 0 || l2 == 0 || i3 == 0 || j3 == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (k3 == 4) {
                            aint1[j2 + i2 * k] = 4;
                        } else {
                            aint1[j2 + i2 * k] = 0;
                        }
                    } else {
                        aint1[j2 + i2 * k] = k3;
                    }
                } else {
                    aint1[j2 + i2 * k] = k3;
                }
            }
        }

        return aint1;
    }
}