package com.aaomidi.ciphers.simplesubstition.engine;

import com.aaomidi.ciphers.NGram;

import java.util.List;

public class Fitness {
    private final List<NGram> models;

    public Fitness() {
        models = NGram.getAllDefaultNGrams();
    }

    private List<NGram> getFitnessModels() {
        return models;
    }

    public double getAvgFitness(String s) {
        double sum = 0;
        for (NGram nGram : getFitnessModels()) {
            sum += nGram.getFitness(s);
        }
        return sum / getFitnessModels().size();
    }
}
