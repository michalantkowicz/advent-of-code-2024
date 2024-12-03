package com.adventofcode.day2;

import java.util.ArrayList;
import java.util.List;

class UnusualReports {
    long countSafe(List<List<Integer>> reports) {
        return reports.stream().mapToLong(r -> isSafe(r) ? 1 : 0).sum();
    }

    long countSafeWithProblemDampener(List<List<Integer>> reports) {
        return reports.stream().mapToLong(r -> isSafeWithDampener(r) ? 1 : 0).sum();
    }

    private boolean isSafe(List<Integer> report) {
        boolean isGreater = false;
        boolean isLower = false;

        for (int i = 1; i < report.size(); i++) {
            int sub = report.get(i) - report.get(i - 1);
            if (sub == 0 || Math.abs(sub) > 3) {
                return false;
            } else if (sub < 0) {
                isGreater = true;
            } else {
                isLower = true;
            }
        }

        return isGreater ^ isLower;
    }

    private boolean isSafeWithDampener(List<Integer> report) {
        boolean result = isSafe(report);
        for (int i = 0; i < report.size(); i++) result |= dampenProblem(report, i);
        return result;
    }

    private boolean dampenProblem(List<Integer> report, int problemIndex) {
        List<Integer> dampenedReport = new ArrayList<>(report);
        dampenedReport.remove(problemIndex);
        return isSafe(dampenedReport);
    }
}
