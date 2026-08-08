package hu.betti.automation.notesapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.AddNoteModal;
import hu.betti.automation.notesapp.pages.HomePage;
import hu.betti.automation.notesapp.pages.LoginPage;
import hu.betti.automation.notesapp.pages.NotesPage;
import hu.betti.automation.notesapp.pages.RegisterPage;
import hu.betti.automation.notesapp.utils.RandomDataGenerator;

public class SearchTest extends BaseTest {

    @Test
    void searchNote() {

        // Arrange

        String email = RandomDataGenerator.generateEmail();
        String name = RandomDataGenerator.generateName();
        String password = RandomDataGenerator.generatePassword();

        HomePage homePage = new HomePage(driver);
        homePage.open();

        RegisterPage registerPage = homePage.clickCreateAccount();
        registerPage.register(email, name, password);

        LoginPage loginPage = registerPage.clickLogin();

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        NotesPage notesPage = loginPage.clickLogin();

        String searchableTitle = "UNIQUE_SEARCH_TEST_12345";

        // Create 3 notes

        for (int i = 1; i <= 3; i++) {

            AddNoteModal addNoteModal = notesPage.clickAddNote();

            String title;

            if (i == 2) {
                title = searchableTitle;
            } else {
                title = "Other note " + i;
            }

            addNoteModal.selectCategory("Work");
            addNoteModal.enterTitle(title);
            addNoteModal.enterDescription("Search test note " + i);
            addNoteModal.clickCreate();
        }

        // Wait for all 3 notes

        notesPage.waitForNoteCount(3);

        // Act

        notesPage.search(searchableTitle);

        // Assert

        notesPage.waitForNoteCount(1);

        assertEquals(1, notesPage.getNoteCount());
        assertTrue(notesPage.isNoteDisplayed(searchableTitle));
    }
}
