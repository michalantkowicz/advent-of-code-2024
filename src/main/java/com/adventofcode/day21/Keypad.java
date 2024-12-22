package com.adventofcode.day21;

import com.adventofcode.common.Direction;
import com.adventofcode.common.Pair;
import com.adventofcode.common.StringMatrix;

import java.util.*;

abstract class Keypad {
    public static final String ENTER = "A";

    protected StringMatrix keypad;
    protected Pair<Integer> currentPointer;
    protected Map<Pair<Pair<Integer>>, List<String>> paths = new HashMap<>();
    private final Map<String, Optional<Pair<Integer>>> positions = new HashMap<>();

    Keypad(StringMatrix keypad, Pair<Integer> initialPointer) {
        this.keypad = keypad;
        this.currentPointer = initialPointer;

        initPositions();
        calculatePaths();
        optimizePaths();
    }

    void overridePaths(Map<Pair<Pair<Integer>>, List<String>> override) {
        paths.putAll(override);
    }

    List<Map<Pair<Pair<Integer>>, List<String>>> possibleVariants() {
        List<Pair<Pair<Integer>>> reversibles = paths.keySet().stream().filter(k -> paths.get(k).size() > 1 && !paths.get(k).getFirst().equals(paths.get(k).getLast()) && checkPath(k.a(), k.b(), paths.get(k).reversed())).toList();
        return generateCombinations(paths, reversibles);
    }

    public static List<Map<Pair<Pair<Integer>>, List<String>>> generateCombinations(
            Map<Pair<Pair<Integer>>, List<String>> x,
            List<Pair<Pair<Integer>>> reversibles) {

        List<Map<Pair<Pair<Integer>>, List<String>>> result = new ArrayList<>();
        generateCombinationsRecursive(x, reversibles, 0, new HashMap<>(), result);
        return result;
    }

    private static void generateCombinationsRecursive(
            Map<Pair<Pair<Integer>>, List<String>> x,
            List<Pair<Pair<Integer>>> reversibles,
            int index,
            Map<Pair<Pair<Integer>>, List<String>> currentMap,
            List<Map<Pair<Pair<Integer>>, List<String>>> result) {

        if (index == reversibles.size()) {
            result.add(new HashMap<>(currentMap));
            return;
        }

        Pair<Pair<Integer>> pair = reversibles.get(index);
        List<String> originalList = x.get(pair);

        if (originalList != null) {
            currentMap.put(pair, new ArrayList<>(originalList));
            generateCombinationsRecursive(x, reversibles, index + 1, currentMap, result);

            List<String> reversedList = new ArrayList<>(originalList);
            Collections.reverse(reversedList);
            currentMap.put(pair, reversedList);
            generateCombinationsRecursive(x, reversibles, index + 1, currentMap, result);

            currentMap.remove(pair);
        }
    }

    private void calculatePaths() {
        for (Pair<Integer> start : keypad.streamIndices().filter(this::isNotWall).toList()) {
            Map<Pair<Integer>, Long> distance = new HashMap<>();
            Map<Pair<Integer>, Pair<Integer>> previous = new HashMap<>();
            Set<Pair<Integer>> Q = new HashSet<>(List.of(start));

            keypad.streamIndices().filter(this::isNotWall).forEach(p -> {
                distance.put(p, Long.MAX_VALUE);
                Q.add(p);
            });
            distance.put(start, 0L);

            while (!Q.isEmpty()) {
                Pair<Integer> p = getMin(Q, distance);
                Q.remove(p);

                Arrays.stream(Direction.values())
                        .map(d -> d.moveFrom(p))
                        .filter(this::isNotWall)
                        .filter(x -> keypad.at(x) != null)
                        .toList()
                        .forEach(n -> {
                            if (distance.get(n) > distance.get(p) + 1) {
                                distance.put(n, distance.get(p) + 1);
                                previous.put(n, p);
                            }
                        });
            }

            for (Pair<Integer> key : keypad.streamIndices().filter(this::isNotWall).toList()) {
                if (!key.equals(start)) {
                    Pair<Integer> end = new Pair<>(key.a(), key.b());
                    Pair<Integer> current = previous.get(end);
                    List<String> path = new ArrayList<>();
                    while (previous.containsKey(end)) {
                        path.addFirst(getDirectionString(current, end));
                        end = previous.get(end);
                        current = previous.get(end);
                    }
                    paths.put(new Pair<>(start, key), path);
                }
            }
            paths.put(new Pair<>(start, start), Collections.emptyList());
        }
    }

    private String getDirectionString(Pair<Integer> current, Pair<Integer> end) {
        if (Direction.RIGHT.moveFrom(current).equals(end)) {
            return ">";
        } else if (Direction.DOWN.moveFrom(current).equals(end)) {
            return "v";
        } else if (Direction.LEFT.moveFrom(current).equals(end)) {
            return "<";
        } else if (Direction.TOP.moveFrom(current).equals(end)) {
            return "^";
        } else {
            throw new IllegalStateException(end + " is not accessible from " + current);
        }
    }

    protected Direction getDirectionFromString(String s) {
        return switch (s) {
            case ">" -> Direction.RIGHT;
            case "<" -> Direction.LEFT;
            case "v" -> Direction.DOWN;
            case "^" -> Direction.TOP;
            default -> throw new IllegalArgumentException("Unexpected argument: " + s);
        };
    }

    private Pair<Integer> getMin(Set<Pair<Integer>> p, Map<Pair<Integer>, Long> distance) {
        return p.stream().min(Comparator.comparing(distance::get)).orElseThrow();
    }

    private void optimizePaths() {
        for (Pair<Pair<Integer>> k : paths.keySet()) {
            List<String> pathK = paths.get(k);
            List<String> pathAsc = pathK.stream().sorted().toList();
            List<String> pathDesc = pathK.stream().sorted(Comparator.reverseOrder()).toList();
            if (checkPath(k.a(), k.b(), pathAsc)) {
                paths.put(k, pathAsc);
            } else if (checkPath(k.a(), k.b(), pathDesc)) {
                paths.put(k, pathDesc);
            }

            if (this instanceof NumericKeypad) {
                if (paths.get(k).size() > 1 && paths.get(k).getLast().equals("v") && checkPath(k.a(), k.b(), paths.get(k).reversed())) {
                    paths.put(k, paths.get(k).reversed());
                }
                if (paths.get(k).size() > 1 && paths.get(k).getFirst().equals(">") && paths.get(k).getLast().equals("^")) {
                    paths.put(k, paths.get(k).reversed());
                }
            }
        }
    }

    private boolean checkPath(Pair<Integer> start, Pair<Integer> end, List<String> path) {
        for (String step : path) {
            Direction d = getDirectionFromString(step);
            start = d.moveFrom(start);
            if (keypad.at(start) == null || "#".equals(keypad.at(start))) {
                return false;
            }
        }
        return start.equals(end);
    }

    private void initPositions() {
        keypad.streamIndices().forEach(p -> positions.put(keypad.at(p), Optional.of(p)));
        positions.put("#", Optional.empty());
    }

    protected Pair<Integer> getPosition(String s) {
        return positions.get(s).orElseThrow();
    }

    private boolean isNotWall(Pair<Integer> p) {
        return !"#".equals(keypad.at(p));
    }

    abstract List<String> process(String input);

    abstract String validate(List<String> input);
}
