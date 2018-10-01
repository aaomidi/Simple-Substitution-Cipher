package com.aaomidi.ciphers.engine;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubstitutionCipher {
    private final FitnessRegistry fitness;
    private final String input;
    private final Random random = new Random();

    public SubstitutionCipher(FitnessRegistry fitness, String input) {
        this.fitness = fitness;
        this.input = input;
    }

    private void shuffleKey(List<String> key) {
        Collections.shuffle(key);
    }

    public void decrypt() {
        List<String> maxKey = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));

        double maxScore = Double.MIN_VALUE;

        while (true) {
            List<String> parentKey = new ArrayList<>(maxKey);
            shuffleKey(parentKey);

            String deciphered = decipher(parentKey);

            double parentScore = getScore(deciphered);

            int count = 0;
            while (count < 500) {
                int r1 = random.nextInt(26);
                int r2 = random.nextInt(26);
                List<String> key = new ArrayList<>(parentKey);
                swap(r1, r2, key);
                deciphered = decipher(key);
                double score = getScore(deciphered);

                if (score > parentScore) {
                    //System.out.println(count);
                    parentScore = score;
                    parentKey = key;
                    count = 0;
                }
                count++;
            }

            if (parentScore > maxScore) {
                maxScore = parentScore;
                maxKey = parentKey;


                System.out.printf("Result Found:\n\tTime: %d\n\tKey: %s\n\tPlain text: %s\n\t Score: %f\n",
                        System.currentTimeMillis(),
                        maxKey,
                        decipher(maxKey),
                        maxScore);
            }

        }
    }

    private void swap(int r1, int r2, List<String> key) {
        String temp = key.get(r1);
        key.set(r1, key.get(r2));
        key.set(r2, temp);
    }

    private double getScore(String deciphered) {
        return fitness.getAvgFitness(deciphered);
    }

    private String decipher(List<String> key) {
        Stream<Character> chars = input.chars().mapToObj(i -> (char) i);
        return chars.map(c -> String.valueOf(decipherChar(c, key))).collect(Collectors.joining());
    }

    private String decipherChar(char c, List<String> key) {
        int position = (int) c - (int) 'A';
        return key.get(position);
    }


}
