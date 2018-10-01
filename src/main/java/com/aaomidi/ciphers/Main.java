package com.aaomidi.ciphers;

import com.aaomidi.ciphers.engine.FitnessRegistry;
import com.aaomidi.ciphers.engine.SubstitutionCipher;
import com.aaomidi.ciphers.util.StreamHandler;

import java.io.InputStream;

public class Main {
    private static final String input = "GBSXUCGSZQGKGSQPKQKGLSKASPCGBGBKGUKGCEUKUZKGGBSQEICACGKGCEUERWKLKUPKQQGCIICUAEUVSHQKGCEUPCBGCGQOEVSHUNSUGKUZCGQSNLSHEHIEEDCUOGEPKHZGBSNKCUGSUKUASERLSKASCUGBSLKACRCACUZSSZEUSBEXHKRGSHWKLKUSQSKCHQTXKZHEUQBKZAENNSUASZFENFCUOCUEKBXGBSWKLKUSQSKNFKQQKZEHGEGBSXUCGSZQGKGSQKUZBCQAEIISKOXSZSICVSHSZGEGBSQSAHSGKHMERQGKGSKREHNKIHSLIMGEKHSASUGKNSHCAKUNSQQKOSPBCISGBCQHSLIMQGKGSZGBKGCGQSSNSZXQSISQQGEAEUGCUXSGBSSJCQGCUOZCLIENKGCAUSOEGCKGCEUQCGAEUGKCUSZUEBGHSKGEHBCUGERPKHEHKHNSZKGGKAD";

    private Main(String... args) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("book2.txt");

        FitnessRegistry registry = new FitnessRegistry();
        String input = Main.input;
        if (args.length > 0) {
            input = StreamHandler.sanitize(args[0]);
            System.out.println(input);
        }

        SubstitutionCipher cipher = new SubstitutionCipher(registry, input);
        System.out.println("Start: " + System.currentTimeMillis());
        cipher.decrypt();
    }

    public static void main(String... args) {
        new Main(args);
    }
}
