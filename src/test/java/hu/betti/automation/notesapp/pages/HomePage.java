package hu.betti.automation.notesapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import hu.betti.automation.notesapp.base.BasePage;

public class HomePage extends BasePage {

	// ==================== Fields ====================

	private static final String URL = "https://practice.expandtesting.com/notes/app/";

	private final By loginButton = By.linkText("Login");

	private final By createAccountButton = By.cssSelector("[data-testid='open-register-view']");

	private final By welcomeTitle = By.tagName("h1");

	private final By privacyIcon = By.cssSelector("button[aria-label='Toggle privacy and legal settings display']");

	private final By privacySettingsButton = By.cssSelector(".ft-reg-message-button");

	// ==================== Constructor ====================

	public HomePage(WebDriver driver) {
		super(driver);
	}

	// ==================== Navigation ====================

	public void open() {
		driver.get(URL);
	}

	// ==================== Interaction ====================

	public LoginPage clickLogin() {
		click(loginButton);
		return new LoginPage(driver);
	}

	public RegisterPage clickCreateAccount() {
		click(createAccountButton);
		return new RegisterPage(driver);
	}

	// ==================== Privacy ====================

	// Find and click the privacy icon inside the Shadow DOM.
	public void clickPrivacyIcon() {

		WebElement button = waitForPrivacyElement(privacyIcon, 20);

		button.click();
	}

	public void scrollToPrivacy() {

		for (int i = 0; i < 3; i++) {

			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// ==================== Verification Privacy ====================

	public boolean isPrivacySettingsDisplayed() {

		return waitForPrivacyElement(privacySettingsButton, 10).isDisplayed();
	}

	// ==================== Verification General ====================

	public String getWelcomeTitle() {
		return getText(welcomeTitle);
	}
}
