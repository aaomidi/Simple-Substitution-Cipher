package com.aaomidi.ciphers.util;

import java.util.stream.Collectors;

public class StreamHandler {

    public static String sanitize(String input){
        return input.toUpperCase().chars().filter(s -> s >= (int) 'A').filter(s -> s <= (int) 'Z').mapToObj(s -> String.valueOf((char) s)).collect(Collectors.joining());

    }

}
