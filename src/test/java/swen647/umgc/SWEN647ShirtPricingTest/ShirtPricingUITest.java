package swen647.umgc.SWEN647ShirtPricingTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ShirtPricingUITest {
	private WebDriver driver;

	@BeforeTest
	public void setup() {
	    System.setProperty("webdriver.chrome.driver", "/Users/ashenafigebreeziabhere/Downloads/chrome-mac-arm64");
	    ChromeOptions options = new ChromeOptions();
	    options.setBinary("/Users/ashenafigebreeziabhere/Downloads/chrome-mac-arm64");

	    driver = new ChromeDriver(options);

	    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60)); // Increase page load timeout
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));  // Implicit wait for elements

	    // WAIT for Spring Boot to start
	    waitForSpringBootToStart();

	    driver.get("http://localhost:8080/index.html");
	    new WebDriverWait(driver, Duration.ofSeconds(20))
	        .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
	}

	// Helper method to check if the app is running before tests start
	private void waitForSpringBootToStart() {
	    int retries = 10;
	    while (retries > 0) {
	        try {
	            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/index.html").openConnection();
	            connection.setRequestMethod("GET");
	            connection.connect();

	            if (connection.getResponseCode() == 200) {
	                System.out.println("Spring Boot is UP and Running...");
	                return;
	            }
	        } catch (IOException ignored) {
	        }

	        System.out.println("Waiting for Spring Boot to start...");
	        try {
	            Thread.sleep(5000); // Wait 5 seconds before retrying
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        retries--;
	    }
	    throw new RuntimeException("Spring Boot did not start in time!");
	}

    @Test
    public void testMinimumInput() {
        driver.findElement(By.id("waist")).sendKeys("1");
        driver.findElement(By.id("length")).sendKeys("1");
        driver.findElement(By.cssSelector("button")).click();
        
        WebElement priceElement = driver.findElement(By.id("price"));
        Assert.assertEquals(priceElement.getText(), "$0.09");
    }

    @Test
    public void testMaximumInput() {
        driver.findElement(By.id("waist")).sendKeys("100");
        driver.findElement(By.id("length")).sendKeys("100");
        driver.findElement(By.id("collar")).click();
        driver.findElement(By.id("sleeves")).click();

        Select currencyDropdown = new Select(driver.findElement(By.id("currency")));
        currencyDropdown.selectByValue("EUR");

        driver.findElement(By.cssSelector("button")).click();
        
        WebElement priceElement = driver.findElement(By.id("price"));
        Assert.assertEquals(priceElement.getText(), "€807.84");
    }

    @Test
    public void testInvalidInput() {
        driver.findElement(By.id("waist")).sendKeys("abc");
        driver.findElement(By.id("length")).sendKeys("50");
        driver.findElement(By.cssSelector("button")).click();
        
        WebElement errorMessage = driver.findElement(By.id("error-message"));
        Assert.assertEquals(errorMessage.getText(), "Please enter a valid number.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}