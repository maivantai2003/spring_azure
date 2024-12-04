package com.nhom27.nhatkykhambenh.utils;

import java.util.regex.Pattern;

public class Validation {

    public static boolean checkEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public static boolean checkPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }

    public static boolean checkPhone(String sdt) {
        String sdtRegex = "^\\d{10,11}$";
        Pattern pattern = Pattern.compile(sdtRegex);
        if (sdt == null) {
            return false;
        }
        return pattern.matcher(sdt).matches();
    }

    public static boolean checkPasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);
    }
}
