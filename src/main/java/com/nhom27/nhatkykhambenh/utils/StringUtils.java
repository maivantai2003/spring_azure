package com.nhom27.nhatkykhambenh.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    public static String padWithZeros(String input, int length) {
        return String.format("%0" + length + "d", Integer.parseInt(input));
    }

    public String formatDate(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public String getInitials(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }

        String[] words = fullName.trim().split("\\s+");
        if (words.length == 1) {
            return String.valueOf(words[0].charAt(0)).toUpperCase();
        }

        char firstInitial = words[0].charAt(0);
        char lastInitial = words[words.length - 1].charAt(0);

        return ("" + firstInitial + lastInitial).toUpperCase();
    }

}

