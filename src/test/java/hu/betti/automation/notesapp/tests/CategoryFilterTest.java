package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.models.Note;
import hu.betti.automation.notesapp.pages.AddNoteModal;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.CsvReader;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class CategoryFilterTest extends BaseTest {

	@Test
	void filterNotesByCategory() {

		// ==================== Arrange ====================

		String email = RandomDataGenerator.generateEmail();
		String name = RandomDataGenerator.generateName();
		String password = RandomDataGenerator.generatePassword();

		HomePage homePage = new HomePage(driver);
		homePage.open();

		RegisterPage registerPage = homePage.clickCreateAccount();

		registerPage.register(email, name, password);

		assertEquals("User account created successfully", registerPage.getSuccessMessage());

		LoginPage loginPage = registerPage.clickLogin();

		NotesPage notesPage = loginPage.login(email, password);

		List<Note> notes = CsvReader.readNotes("src/test/resources/notes.csv");

		List<String> workTitles = new ArrayList<>();
		List<String> homeTitles = new ArrayList<>();
		List<String> personalTitles = new ArrayList<>();

		// Create notes in different categories.
		for (Note note : notes) {

			AddNoteModal addNoteModal = notesPage.clickAddNote();

			addNoteModal.selectCategory(note.getCategory());
			addNoteModal.enterTitle(note.getTitle());
			addNoteModal.enterDescription(note.getDescription());
			addNoteModal.clickCreate();
		}

		// Group note titles by category.
		for (Note note : notes) {

			switch (note.getCategory()) {

			case "Work":
				workTitles.add(note.getTitle());
				break;

			case "Home":
				homeTitles.add(note.getTitle());
				break;

			case "Personal":
				personalTitles.add(note.getTitle());
				break;
			}
		}

		// ==================== Assert ====================

		// Verify Work category.
		notesPage.selectCategory("Work");
		assertTrue(notesPage.getProgressInfo().contains("work category"));
		assertEquals(workTitles.size(), notesPage.getNoteCount());
		assertTrue(notesPage.areNotesDisplayed(workTitles));

		// Verify Home category.
		notesPage.selectCategory("Home");
		assertTrue(notesPage.getProgressInfo().contains("home category"));
		assertEquals(homeTitles.size(), notesPage.getNoteCount());
		assertTrue(notesPage.areNotesDisplayed(homeTitles));

		// Verify Personal category.
		notesPage.selectCategory("Personal");
		assertTrue(notesPage.getProgressInfo().contains("personal category"));
		assertEquals(personalTitles.size(), notesPage.getNoteCount());
		assertTrue(notesPage.areNotesDisplayed(personalTitles));
	}
}