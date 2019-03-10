package com.trackorjargh;

import static java.lang.invoke.MethodHandles.lookup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

public class TestE2E {
	
	final static Logger log = getLogger(lookup().lookupClass());
	
	public void loginUser(WebDriver driver, String name, String pass) {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		
		//Wait show form login
		wait.withTimeout(1, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.name("user")));
		
		//Load form
		WebElement userField = driver.findElement(By.name("user"));
		WebElement passField = driver.findElement(By.name("password"));
		
		//Write credentials
		userField.sendKeys(name);
		passField.sendKeys(pass);
		
		driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
		
		//Check login
		wait.withTimeout(1, TimeUnit.SECONDS).until(ExpectedConditions.visibilityOfElementLocated(By.className("carousel-content")));
		WebElement menuBar = driver.findElement(By.id("menuList"));
		List<WebElement> elementsBar = menuBar.findElements(By.tagName("a"));
		assertThat(elementsBar.get(5).getText()).isEqualToIgnoringCase(name);
				
		log.info("Loggin successful, user {}", name);
	}
	
	public void logout(WebDriver driver) {
		String name = "";
		
		//Click logout
		WebElement menuBar = driver.findElement(By.id("menuList"));
		List<WebElement> elementsBar = menuBar.findElements(By.tagName("a"));
		WebElement loginUser = elementsBar.get(elementsBar.size() - 2);
		WebElement logoutButton = elementsBar.get(elementsBar.size() - 1);
		
		//Check user is login
		assertThat(logoutButton.getText()).isEqualToIgnoringCase("logout");
		name = loginUser.getText(); 
		
		//Click logout button
		logoutButton.click();
		
		//Check logout
		menuBar = driver.findElement(By.id("menuList"));
		elementsBar = menuBar.findElements(By.tagName("a"));
		logoutButton = elementsBar.get(elementsBar.size() - 1);
		
		assertThat(logoutButton.getText()).isEqualToIgnoringCase("login");	
		
		log.info("Loggout successful, user {}", name);
	}
	
}
