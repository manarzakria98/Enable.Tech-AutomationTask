package EnableTask;

import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class Shopping {
    private WebDriver driver;
    private WebDriverWait wait;

    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
    }

    public void addToCart(String item) {
        driver.findElement(By.id("add-to-cart-" + item)).click();
    }

    public void checkout(String firstName, String lastName, String postalCode) {
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='btn btn_action btn_medium cart_button']")));
        finishButton.click();
        driver.findElement(By.className("complete-header"));
    }

    @Test
    public void testEnableTask() {
        setUp();

        login("standard_user", "secret_sauce");
        addToCart("sauce-labs-backpack");
        addToCart("test.allthethings()-t-shirt-(red)");
        checkout("John", "Mark", "Arizona");

        tearDown();
    }
}
