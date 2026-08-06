package hu.betti.automation.notesapp.utils;

import java.util.UUID;

public class RandomDataGenerator {

    public static String generateEmail() {
        return "test_" + UUID.randomUUID() + "@example.com";
    }

    public static String generateName() {
        return "Test User";
    }

    public static String generatePassword() {
        return "Password123!";
    }

}