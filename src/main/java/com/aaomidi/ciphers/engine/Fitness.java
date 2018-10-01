package com.aaomidi.ciphers.engine;

import com.aaomidi.ciphers.engine.io.StatisticsReader;
import com.aaomidi.ciphers.util.StreamHandler;

import java.util.List;

public class Fitness {
    private final int length;
    private Frequency frequency;

    public Fitness(int length, String fileName) {
        this.length = length;
        init(fileName);
    }

    public Fitness(Frequency frequency, int length) {
        this.length = length;
        this.frequency = frequency;
    }

    private void init(String fileName) {
        List<String> lines = StreamHandler.isToList(this.getClass().getClassLoader().getResourceAsStream(fileName));
        this.frequency = StatisticsReader.readFile(lines);
    }

    public double getFitness(String input) {
        double result = 0.0;
        for (String s : split(input, length)) {
            if (s.length() != length) continue;
            double fitness = getComponentFitness(s);
            result += fitness;
        }

        return result;
    }

    private double getComponentFitness(String s) {
        return Math.log10(getProbability(s));
    }

    private double getProbability(String s) {
        double value = frequency.getFitness(s);
        if (value == 0) {
            value = 0.01;
        }

        return value / (double) frequency.getSize();
    }

    private String[] split(String s, int length) {
        int arrayLen = (int) Math.ceil((s.length() / (double) length));
        String[] result = new String[arrayLen];

        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + length);
            j += length;
        }

        result[lastIndex] = s.substring(j);

        return result;
    }
}
