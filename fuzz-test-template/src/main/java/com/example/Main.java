package com.example;

public class Main {
    private static final int SECRET = 12345678;
    public static boolean checkSecret(int secret) {
        return secret == SECRET;
    }
}