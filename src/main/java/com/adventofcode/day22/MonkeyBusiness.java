package com.adventofcode.day22;

import java.util.*;

class MonkeyBusiness {
    private record Key(long a, long b, long c, long d) {
    }

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

    long getMaxSum(List<Long> secrets) {
        Map<Long, Set<Key>> map = new HashMap<>();
        Map<Key, Long> totalSums = new HashMap<>();
        for (long secret : secrets) {
            map.put(secret, new HashSet<>());
            List<Long> changes = new LinkedList<>(List.of(secret));
            long newSecret = secret;
            for (int i = 0; i < 2000; i++) {
                newSecret = prune(mix(newSecret * 64, newSecret));
                newSecret = prune(mix((newSecret / 32), newSecret));
                newSecret = prune(mix(newSecret * 2048, newSecret));

                changes.add(newSecret % 10);

                if (changes.size() >= 5) {
                    Key key = new Key(changes.get(1) - changes.get(0), changes.get(2) - changes.get(1), changes.get(3) - changes.get(2), changes.get(4) - changes.get(3));
                    changes.removeFirst();
                    if (!map.get(secret).contains(key)) {
                        map.get(secret).add(key);
                        totalSums.put(key, totalSums.getOrDefault(key, 0L) + changes.getLast());
                    }
                }
            }
        }

        Map.Entry<Key, Long> keyLongEntry = totalSums.entrySet().stream().max(Comparator.comparingLong(Map.Entry<Key, Long>::getValue)).orElseThrow();
        return keyLongEntry.getValue();
    }

    private long mix(long value, long secret) {
        return (value ^ secret);
    }

    private long prune(long secret) {
        return (secret % 16777216);
    }
}
