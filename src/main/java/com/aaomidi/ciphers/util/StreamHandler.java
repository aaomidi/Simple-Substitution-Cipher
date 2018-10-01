package com.aaomidi.ciphers.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamHandler {

    public static List<String> isToList(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            List<String> strings = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
            return strings;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String sanitize(String input){
        return input.toUpperCase().chars().filter(s -> s >= (int) 'A').filter(s -> s <= (int) 'Z').mapToObj(s -> String.valueOf((char) s)).collect(Collectors.joining());

    }

}
