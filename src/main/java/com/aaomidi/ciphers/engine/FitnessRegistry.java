package com.aaomidi.ciphers.engine;

import com.aaomidi.ciphers.engine.io.StatisticsReader;
import com.aaomidi.ciphers.util.StreamHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FitnessRegistry {
    private final List<String> plaintext;

    private Fitness monoGramFitness;
    private Fitness biGramFitness;
    private Fitness triGramFitness;
    private Fitness quadGramFitness;

    public FitnessRegistry(InputStream inputStream) {
        this.plaintext = StreamHandler.isToList(inputStream);
    }

    public FitnessRegistry() {
        this.plaintext = null;
    }

    public Fitness getMonoGramFitness() {
        if (monoGramFitness == null) {
            if (plaintext == null) {
                monoGramFitness = new Fitness(1, "english_monograms.txt");
            } else {
                monoGramFitness = new Fitness(StatisticsReader.parseText(plaintext, 1), 1);
            }
        }

        return monoGramFitness;
    }

    public Fitness getBiGramFitness() {
        if (biGramFitness == null) {
            if (plaintext == null) {
                biGramFitness = new Fitness(2, "english_bigrams.txt");
            } else {
                biGramFitness = new Fitness(StatisticsReader.parseText(plaintext, 2), 2);
            }
        }

        return biGramFitness;
    }

    public Fitness getTriGramFitness() {
        if (triGramFitness == null) {
            if (plaintext == null) {
                triGramFitness = new Fitness(3, "english_trigrams.txt");
            } else {
                triGramFitness = new Fitness(StatisticsReader.parseText(plaintext, 3), 3);

            }

        }

        return triGramFitness;
    }

    public Fitness getQuadGramFitness() {
        if (quadGramFitness == null) {
            if (plaintext == null) {
                quadGramFitness = new Fitness(4, "english_quadgrams.txt");
            } else {
                quadGramFitness = new Fitness(StatisticsReader.parseText(plaintext, 4), 4);

            }

        }

        return quadGramFitness;
    }

    public List<Fitness> getFitnessModels() {
        return new ArrayList<>(Arrays.asList(getMonoGramFitness(), getBiGramFitness(), getTriGramFitness(), getQuadGramFitness()));
    }

    public double getAvgFitness(String s) {
        double sum = 0;
        for (Fitness fitness : getFitnessModels()) {
            sum += fitness.getFitness(s);
        }
        return sum / getFitnessModels().size();
    }
}
