package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class CreateNotesFromCsvTest extends BaseTest {

    @Test
    void createNotesFromCsv() {

        // Arrange

        List<Note> notes = CsvReader.readNotes(
                "src/test/resources/notes.csv");

        String email = RandomDataGenerator.generateEmail();
        String name = RandomDataGenerator.generateName();
        String password = RandomDataGenerator.generatePassword();

        HomePage homePage = new HomePage(driver);
        homePage.open();

        RegisterPage registerPage = homePage.clickCreateAccount();
        registerPage.register(email, name, password);

        assertTrue(registerPage.getSuccessMessage()
                .contains("User account created successfully"));

        LoginPage loginPage = registerPage.clickLogin();

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        NotesPage notesPage = loginPage.clickLogin();

        // Act

        for (Note note : notes) {

            AddNoteModal addNoteModal = notesPage.clickAddNote();

            addNoteModal.selectCategory(note.getCategory());
            addNoteModal.enterTitle(note.getTitle());
            addNoteModal.enterDescription(note.getDescription());
            addNoteModal.clickCreate();
        }

        // Assert

        assertTrue(notesPage.isLoaded());

        notesPage.waitForNoteCount(notes.size());

        assertEquals(notes.size(), notesPage.getNoteCount());
    }
}