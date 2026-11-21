package engine.utils.spatial.core;

public class Cells {

    // region Fields
    final int[] idxs;
    int count;
    // endregion

    // region Constructors
    Cells(int maxCellsPerBody) {
        this.idxs = new int[maxCellsPerBody];
        this.count = 0;
    }
    // endregion

    // *** PUBLIC ***

    void updateFrom(int[] src, int newCount) {
        if (src == null) {
            throw new IllegalArgumentException("src is null");
        }
        if (newCount < 0 || newCount > idxs.length) {
            throw new IllegalArgumentException(
                    "Invalid newCount=" + newCount + ", capacity=" + idxs.length);
        }
        if (newCount > src.length) {
            throw new IllegalArgumentException(
                    "src.length (" + src.length + ") < newCount (" + newCount + ")");
        }

        this.count = newCount;
        for (int i = 0; i < newCount; i++) {
            this.idxs[i] = src[i];
        }
    }
}
