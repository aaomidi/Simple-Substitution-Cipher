package com.aaomidi.ciphers.engine.io;

import com.aaomidi.ciphers.engine.Frequency;
import com.aaomidi.ciphers.util.StreamHandler;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PlaintextParser extends RecursiveTask<Frequency> {
    private static final int THRESHOLD = 1000;
    private final List<String> input;

    private final int start;
    private final int end;
    private final int grouping;

    PlaintextParser(List<String> input, int start, int end, int grouping) {
        this.input = input;
        this.start = start;
        this.end = end;
        this.grouping = grouping;
    }


    @Override
    protected Frequency compute() {
        if (end - start <= THRESHOLD) {
            return process();
        } else {

            List<PlaintextParser> tasks = createSubtasks();
            tasks.forEach(PlaintextParser::fork);


            Map<String, Integer> result = tasks.stream().map(ForkJoinTask::join)
                    .map(Frequency::getFrequencyMap)
                    .flatMap(m -> m.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1 + v2));
            return new Frequency(result);
        }
    }

    private Frequency process() {
        List<String> strings = input.subList(start, end);

        return new Frequency(
                strings.stream()
                        .map(this::sanitize)
                        .filter(s -> !s.isEmpty())
                        .map(s -> Splitter.fixedLength(grouping).split(s))
                        .flatMap(s -> StreamSupport.stream(s.spliterator(), false))
                        .collect(Collectors.toMap(s -> s, s -> 1, (v1, v2) -> v1 + v2))
        );

    }

    private String sanitize(String input) {
        return StreamHandler.sanitize(input);
    }


    protected List<PlaintextParser> createSubtasks() {
        List<PlaintextParser> tasks = new ArrayList<>();

        int avg = (end + start) / 2;

        tasks.add(new PlaintextParser(input, start, avg, grouping));
        tasks.add(new PlaintextParser(input, avg, end, grouping));
        return tasks;
    }
}
