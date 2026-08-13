package hu.betti.automation.notesapp.tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hu.betti.automation.notesapp.base.BaseTest;
import hu.betti.automation.notesapp.pages.HomePage;

@Disabled("External cookie consent component is not stable in automation environment")

public class PrivacyTest extends BaseTest {

	
	@Test
	void privacySettingsTest() {

	    HomePage homePage = new HomePage(driver);
	    homePage.open();
	    
	    homePage.scrollToBottom();
	    homePage.clickPrivacySettings();
	}
}
