package com.adventofcode.day9;

import com.adventofcode.common.Pair;

import java.util.ArrayList;
import java.util.List;

class DiskFragmenter {
    long compactAndGetChecksum(List<Integer> blocks) {
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

    long compactFullFilesAndGetChecksum(List<Pair<Integer>> blocksWithIds) {
        long checksum = 0;
        if ((blocksWithIds.size() - 1) % 2 != 0) {
            throw new IllegalArgumentException("Expected even size of the collection");
        }
        int currentFileIndex = blocksWithIds.size() - 1;
        int currentFileId = blocksWithIds.get(currentFileIndex).b();
        while (currentFileId > 0) {
            for (int i = 0; i < currentFileIndex; i++) {
                if (blocksWithIds.get(i).b().equals(-1)) {
                    Integer emptyBlockSize = blocksWithIds.get(i).a();
                    Integer fileBlockSize = blocksWithIds.get(currentFileIndex).a();
                    if (emptyBlockSize >= fileBlockSize) {
                        blocksWithIds.add(i, blocksWithIds.get(currentFileIndex));
                        blocksWithIds.set(i + 1, new Pair<>(emptyBlockSize - fileBlockSize, -1));
                        blocksWithIds.set(currentFileIndex + 1, new Pair<>(fileBlockSize, -1));
                        break;
                    }
                }
            }

            blocksWithIds = formatBlocksWithIds(blocksWithIds);

            currentFileId--;
            for (int i = blocksWithIds.size() - 1; i >= 0; i--) {
                if (blocksWithIds.get(i).b().equals(currentFileId)) {
                    currentFileIndex = i;
                    break;
                }
            }
        }
        int position = 0;
        for(Pair<Integer> block : blocksWithIds) {
            for(int i = 0; i < block.a(); i++) {
                if(block.b() != -1) {
                    checksum += (long) position * block.b();
                }
                position++;
            }
        }
        return checksum;
    }

    private static List<Pair<Integer>> formatBlocksWithIds(List<Pair<Integer>> blocksWithIds) {
        List<Pair<Integer>> formattedBlocksWithIds = new ArrayList<>();
        int emptyBlockSizeSum = 0;
        for (Pair<Integer> blocksWithId : blocksWithIds) {
            if (blocksWithId.b() >= 0) {
                if (emptyBlockSizeSum > 0) {
                    formattedBlocksWithIds.add(new Pair<>(emptyBlockSizeSum, -1));
                    emptyBlockSizeSum = 0;
                }
                formattedBlocksWithIds.add(blocksWithId);
            } else {
                emptyBlockSizeSum += blocksWithId.a();
            }
        }
        if (emptyBlockSizeSum > 0) {
            formattedBlocksWithIds.add(new Pair<>(emptyBlockSizeSum, -1));
        }
        return formattedBlocksWithIds;
    }

    private static int getId(int i) {
        return i / 2;
    }

    private static boolean isFile(int i) {
        return i % 2 == 0;
    }
}
