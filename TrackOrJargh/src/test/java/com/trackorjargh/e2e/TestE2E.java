package com.trackorjargh.e2e;

import static java.lang.invoke.MethodHandles.lookup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;

public class TestE2E {

	final static Logger log = getLogger(lookup().lookupClass());

	public void loginUser(Browser browser, String name, String pass) {
		// Wait show form login
		browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.name("user")), "No login page", 1);

		// Load form
		WebElement userField = browser.getDriver().findElement(By.name("user"));
		WebElement passField = browser.getDriver().findElement(By.name("password"));

		// Write credentials
		userField.sendKeys(name);
		passField.sendKeys(pass);

		browser.getDriver().findElement(By.name("password")).sendKeys(Keys.ENTER);

		// Check login
		browser.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.className("carousel-content")), "Login failed", 2);

		WebElement menuBar = browser.getDriver().findElement(By.id("menuList"));
		List<WebElement> elementsBar = menuBar.findElements(By.tagName("a"));
		assertThat(elementsBar.get(5).getText()).isEqualToIgnoringCase(name);

		log.info("Loggin successful, user {}", name);
	}

	public void logout(Browser browser) {
		String name = "";

		// Click logout
		WebElement menuBar = browser.getDriver().findElement(By.id("menuList"));
		List<WebElement> elementsBar = menuBar.findElements(By.tagName("a"));
		WebElement loginUser = elementsBar.get(elementsBar.size() - 2);
		WebElement logoutButton = elementsBar.get(elementsBar.size() - 1);

		// Check user is login
		assertThat(logoutButton.getText()).isEqualToIgnoringCase("logout");
		name = loginUser.getText();

		// Click logout button
		logoutButton.click();

		// Check logout
		menuBar = browser.getDriver().findElement(By.id("menuList"));
		elementsBar = menuBar.findElements(By.tagName("a"));
		logoutButton = elementsBar.get(elementsBar.size() - 1);

		assertThat(logoutButton.getText()).isEqualToIgnoringCase("login");

		log.info("Loggout successful, user {}", name);
	}

}
