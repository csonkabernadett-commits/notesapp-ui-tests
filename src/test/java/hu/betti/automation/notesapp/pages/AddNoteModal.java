package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import hu.betti.automation.notesapp.base.BasePage;

public class AddNoteModal extends BasePage {

    // Locators
    private final By categorySelect =
            By.cssSelector("[data-testid='note-category']");

    private final By completedCheckbox =
            By.cssSelector("[data-testid='note-completed']");

    private final By titleField =
            By.cssSelector("[data-testid='note-title']");

    private final By descriptionField =
            By.cssSelector("[data-testid='note-description']");

    private final By createButton =
            By.cssSelector("[data-testid='note-submit']");

    private final By cancelButton =
            By.cssSelector("[data-testid='note-cancel']");

    private final By closeButton =
            By.cssSelector("[aria-label='Close']");


    // Constructor
    public AddNoteModal(WebDriver driver) {
        super(driver);
    }

    public void selectCategory(String category) {
        Select select = new Select(waitVisible(categorySelect));
        select.selectByVisibleText(category);
    }

    public void enterTitle(String title) {
        type(titleField, title);
    }

    public void enterDescription(String description) {
        type(descriptionField, description);
    }

    public void markCompleted() {
        if (!waitVisible(completedCheckbox).isSelected()) {
            click(completedCheckbox);
        }
    }

    public void clickCreate() {
        click(createButton);
    }

    public void clickCancel() {
        click(cancelButton);
    }

    public void close() {
        click(closeButton);
    }
}	
	

