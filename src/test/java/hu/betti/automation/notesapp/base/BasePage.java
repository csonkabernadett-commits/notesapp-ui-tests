package hu.betti.automation.notesapp.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	// ==================== Fields ====================

	// Shared WebDriver and explicit wait used by all Page Objects.
	protected WebDriver driver;
	protected WebDriverWait wait;

	// ==================== Constructor ====================

	// Initialize the shared WebDriver and explicit wait.
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// ==================== Find ====================

	// Find an element using the provided locator.
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}

	// ==================== Wait ====================

	// Wait until the element is visible.
	protected WebElement waitVisible(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Wait until the element is clickable.
	protected WebElement waitClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// ==================== Interaction ====================

	/*
	 * Wait for the element, scroll to it and click it. Retry up to 3 times if the
	 * DOM changes before the click.
	 */
	protected void click(By locator) {

		for (int i = 0; i < 3; i++) {
			try {
				WebElement element = waitClickable(locator);

				scrollIntoView(element);

				element.click();
				return;

			} catch (StaleElementReferenceException e) {
				// DOM changed, so find the element again.
			}
		}

		throw new StaleElementReferenceException("Element remained stale after 3 attempts: " + locator);
	}

	protected void type(By locator, String text) {
		waitVisible(locator).sendKeys(text);
	}

	protected String getText(By locator) {
		return waitVisible(locator).getText();
	}

	protected String getTitle() {
		return driver.getTitle();
	}

	// Clear the current value of the input field.
	protected void clear(By locator) {
		waitVisible(locator).clear();
	}

	// ==================== Shadow DOM ====================

	// Wait for an element inside the privacy component Shadow DOM.
	protected WebElement waitForPrivacyElement(By elementLocator, int seconds) {

		WebDriverWait shadowWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

		return shadowWait.until(driver -> {

			try {
				List<WebElement> elements = driver.findElements(By.cssSelector("body > div"));

				for (WebElement element : elements) {

					try {
						SearchContext shadowRoot = element.getShadowRoot();

						WebElement toolbar = shadowRoot.findElement(By.cssSelector("#ft-floating-toolbar"));

						return toolbar.findElement(elementLocator);

					} catch (Exception e) {
						// This element is not the privacy component.
					}
				}

			} catch (Exception e) {
				// Privacy component is not available yet.
			}

			return null;
		});
	}

	// ==================== Scrolling ====================

	// Scroll the element into the center of the viewport.
	protected void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
	}

	// ==================== Special Wait ====================

	// Wait until the expected number of elements is rendered in the DOM.
	protected void waitForNumberOfElements(By locator, int number) {
		wait.until(ExpectedConditions.numberOfElementsToBe(locator, number));
	}
}
