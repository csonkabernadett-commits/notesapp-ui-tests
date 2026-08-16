package hu.betti.automation.notesapp.utils;

import java.util.UUID;

public class RandomDataGenerator {

	// ==================== Methods ====================

	public static String generateEmail() {
		return "test_" + UUID.randomUUID() + "@example.com";
	}

	public static String generateName() {
		return "Test User";
	}

	public static String generatePassword() {
		return "Password123!";
	}

	public static String generateNoteTitle() {
		return "Automated note " + UUID.randomUUID().toString().substring(0, 8);
	}
}