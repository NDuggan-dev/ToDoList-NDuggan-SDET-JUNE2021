package com.qa.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class CategoryCrud {
	
	private WebDriver driver;
	
	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		this.driver = new ChromeDriver();
		driver.manage().window().maximize();
	
	}
	
	@Test
	public void categoryCrud() throws InterruptedException {
		driver.get("http://127.0.0.1:5500/index.html");
		
		String categoryNameInput = "Shopping";
		String categoryNameUpdateInput = " list";
		String categoryNameUpdateExpected = "Shopping list";
		
		WebElement categoryIcon = driver.findElement(By.id("categories-icon"));
		categoryIcon.click();
		
		WebElement categoryName = driver.findElement(By.id("category-name"));
		categoryName.sendKeys(categoryNameInput);
		
		// explicit wait condition
	    WebDriverWait w = new WebDriverWait(driver,3);
	    // presenceOfElementLocated condition
	    w.until(ExpectedConditions.presenceOfElementLocated (By.id("color-picker-input")));
		WebElement categoryColour = driver.findElement(By.id("color-picker-input"));
		categoryColour.click();
		
		WebElement categorySubmit = driver.findElement(By.id("submit-category"));
		categorySubmit.click();
		
		w.until(ExpectedConditions.presenceOfElementLocated (By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/span[1]")));
		
		WebElement categoryListItem = driver.findElement(By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/span[1]"));
		assertTrue(categoryListItem.getText().equals(categoryNameInput));
	
		WebElement categoryEdit = driver.findElement(By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/div/i[1]"));
		categoryEdit.click();
		
		categoryName.sendKeys(categoryNameUpdateInput);
		WebElement categoryUpdate = driver.findElement(By.id("update-category"));
		categoryUpdate.click();
		
		w.until(ExpectedConditions.presenceOfElementLocated (By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/span[1]")));
		WebElement categoryListItemUpdated = driver.findElement(By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/span[1]"));
		System.out.println(categoryListItemUpdated.getText());
		
		assertEquals(categoryListItemUpdated.getText(), categoryNameUpdateExpected);
		
		WebElement categoryDeleteButton = driver.findElement(By.xpath("/html/body/div/aside/ul/div[1]/li[4]/a/div/i[2]"));
		categoryDeleteButton.click();	
	}
	
	@AfterEach
	public void afterTest() {
		driver.close();
	}
	

}
