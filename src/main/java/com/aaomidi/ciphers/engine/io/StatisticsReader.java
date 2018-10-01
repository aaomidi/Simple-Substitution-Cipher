package com.aaomidi.ciphers.engine.io;

import com.aaomidi.ciphers.engine.Frequency;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class StatisticsReader {

    private static final ForkJoinPool pool = new ForkJoinPool(8);

    public static Frequency readFile(List<String> lines) {
        return new Frequency(
                lines.stream()
                        .filter((s) -> s.length() >= 4)
                        .map((s) -> s.split(" "))
                        .collect(Collectors.toMap(
                                (arr) -> arr[0], // Key
                                (arr) -> Integer.valueOf(arr[1])) // Value
                        )
        );
    }

    public static Frequency parseText(List<String> lines, int grouping) {
        return pool.invoke(new PlaintextParser(lines, 0, lines.size(), grouping));
    }
}


