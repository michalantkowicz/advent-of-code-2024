package com.adventofcode.day22;

import java.util.List;

class MonkeyBusiness {
    long sum2000Generations(List<Long> secrets) {
        long sum = 0;
        for (long secret : secrets) {
            for (int i = 0; i < 2000; i++) {
                secret = prune(mix(secret * 64, secret));
                secret = prune(mix((secret / 32), secret));
                secret = prune(mix(secret * 2048, secret));
            }
            sum += secret;
        }
        return sum;
    }

    private long mix(long value, long secret) {
        return (value ^ secret);
    }

    private long prune(long secret) {
        return (secret % 16777216);
    }
}
