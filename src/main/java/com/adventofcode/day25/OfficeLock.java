package com.adventofcode.day25;

import com.adventofcode.common.StringMatrix;

import java.util.List;

class OfficeLock {
    long countKeysThatFit(List<String> locks, List<String> keys) {
        long count = 0;
        for (String lock : locks) {
            for (String key : keys) {
                StringMatrix lockMatrix = new StringMatrix(lock);
                StringMatrix keyMatrix = new StringMatrix(key);
                if (keyMatrix.streamIndices().noneMatch(p -> keyMatrix.at(p).equals("#") && lockMatrix.at(p).equals("#"))) {
                    count++;
                }
            }
        }
        return count;
    }
}
