package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import hu.betti.automation.notesapp.base.BasePage;
import java.util.List;
import org.openqa.selenium.WebElement;

public class NotesPage extends BasePage {
	
	  // Locators
    private final By addNoteButton =
            By.cssSelector("[data-testid='add-new-note']");

    private final By profileButton =
            By.cssSelector("[data-testid='profile']");

    private final By logoutButton =
            By.cssSelector("[data-testid='logout']");
    
    private final By searchField =
            By.cssSelector("[data-testid='search-input']");

    private final By searchButton =
            By.cssSelector("[data-testid='search-btn']");
    
    private final By noteCards =
            By.cssSelector("[data-testid='note-card']");
          
    private final By deleteConfirmButton =
            By.cssSelector("[data-testid='note-delete-confirm']");

    private final By deleteCancelButton =
            By.cssSelector("[data-testid='note-delete-cancel-2']");
    
    private final By progressInfo =
            By.cssSelector("[data-testid='progress-info']");
    
       
    // Constructor
	public NotesPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isLoaded() {
	     return waitVisible(addNoteButton).isDisplayed();
	    }

	public ProfilePage clickProfile() {
	      click(profileButton);
	      return new ProfilePage(driver);
	    }

	 public HomePage clickLogout() {
	      click(logoutButton);
	      return new HomePage(driver);
	    }
	 
	 public void search(String text) {
		    type(searchField, text);
		    click(searchButton);
		}
	 
	 public AddNoteModal clickAddNote() {
		    click(addNoteButton);
		    return new AddNoteModal(driver);
		}
	 
	 public boolean isNoteDisplayed(String title) {
		    By noteTitle = By.xpath(
		        "//div[@data-testid='note-card-title' and normalize-space()='" + title + "']"
		    );
		    return waitVisible(noteTitle).isDisplayed();
		}
	 
	 public int getNoteCount() {
		    return driver.findElements(noteCards).size();
		}
	 
	 public boolean areNotesDisplayed(List<String> expectedTitles) {

		    List<WebElement> cards = driver.findElements(noteCards);

		    for (String title : expectedTitles) {

		        boolean found = false;

		        for (WebElement card : cards) {

		            String cardTitle = card.findElement(
		                    By.cssSelector("[data-testid='note-card-title']")
		            ).getText();

		            if (cardTitle.equals(title)) {
		                found = true;
		                break;
		            }
		        }

		        if (!found) {
		            return false;
		        }
		    }

		    return true;
		}
	 
	 
	 public void waitForNoteCount(int expectedCount) {
		    waitForNumberOfElements(noteCards, expectedCount);
		}
	 
	 public void deleteNote(String title) {

		    By deleteButton = By.xpath(
		        "//div[@data-testid='note-card']"
		        + "[.//div[@data-testid='note-card-title' "
		        + "and normalize-space()='" + title + "']"
		        + "]"
		        + "//button[@data-testid='note-delete']"
		    );

		    click(deleteButton);
		}
	 
	 public EditNoteModal editNote(String title) {

		    By editButton = By.xpath(
		        "//div[@data-testid='note-card']"
		        + "[.//div[@data-testid='note-card-title' "
		        + "and normalize-space()='" + title + "']"
		        + "]"
		        + "//button[@data-testid='note-edit']"
		    );

		    click(editButton);

		    return new EditNoteModal(driver);
		}
	 
	 public void confirmDelete() {
		    click(deleteConfirmButton);
		}
	 
	 public void cancelDelete() {
		    click(deleteCancelButton);
		}
	 
	 public void logout() {
		    click(logoutButton);
		}
	 
	 public void selectCategory(String category) {
		    By categoryButton = By.cssSelector(
		        "[data-testid='category-" + category.toLowerCase() + "']"
		    );

		    click(categoryButton);
		}

		public String getProgressInfo() {
		    return getText(progressInfo);
		}
}
