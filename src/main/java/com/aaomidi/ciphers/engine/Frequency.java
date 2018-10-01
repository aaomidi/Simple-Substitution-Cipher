package com.aaomidi.ciphers.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Frequency {
    private final Map<String, Integer> frequencyMap;

    public Frequency(Frequency f1, Frequency f2) {
        frequencyMap = Stream.concat(f1.getFrequencyMap().entrySet().stream(), f2.getFrequencyMap().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (count1, count2) -> count1 + count2
                        )
                );

        System.out.println(getFrequencyMap());
    }

    public Frequency() {
        frequencyMap = new HashMap<>();
    }

    public Frequency(Map<String, Integer> map) {
        frequencyMap = map;
    }

    public int getFitness(String s){
        return frequencyMap.getOrDefault(s, 0);
    }

    public int getSize(){
        return frequencyMap.size();
    }

    public Map<String, Integer> getFrequencyMap() {
        return frequencyMap;
    }
}
