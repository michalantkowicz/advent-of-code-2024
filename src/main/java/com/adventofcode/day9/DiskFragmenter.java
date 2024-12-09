package com.adventofcode.day9;

import java.util.List;

class DiskFragmenter {
    public long compactAndGetChecksum(List<Integer> blocks) {
        long checksum = 0;
        long position = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int blockSize = blocks.get(i);
            if (isFile(i)) {
                for (long p = 0; p < blockSize; p++) {
                    checksum += (position * getId(i));
                    position++;
                }
            } else {
                while (i < blocks.size() && blockSize > 0) {
                    int lastBlockIndex = blocks.size() - 1;
                    if (!isFile(lastBlockIndex)) {
                        blocks.remove(lastBlockIndex);
                    } else {
                        int lastBlockSize = blocks.get(lastBlockIndex);
                        for (int p = 0; p < Math.min(blockSize, lastBlockSize); p++) {
                            checksum += (position * getId(lastBlockIndex));
                            position++;
                        }
                        if (blockSize >= lastBlockSize) {
                            blocks.removeLast();
                        } else {
                            blocks.set(lastBlockIndex, lastBlockSize - blockSize);
                        }
                        blockSize -= lastBlockSize;
                    }
                }
            }
        }
        return checksum;
    }

    private static int getId(int i) {
        return i / 2;
    }

    private static boolean isFile(int i) {
        return i % 2 == 0;
    }
}
