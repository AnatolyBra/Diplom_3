package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

abstract public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp(){
//        WebDriverManager.chromedriver().driverVersion("119.0.6045.123").setup();
//
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
//        BasePage.setDriver(driver);
//        driver.get("https://stellarburgers.nomoreparties.site/");

//         for testing in Yandex Browser
//        WebDriverManager.chromedriver().driverVersion("src/main/resources/yandexdriver").setup();
        System.setProperty("webdriver.chrome.driver", "/Applications/Yandex.app/Contents/MacOS/Yandex");
        ChromeOptions options = new ChromeOptions();
//        options.setBinary("");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-dev-shm-usage");// overcome limited resource problems
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        BasePage.setDriver(driver);
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @After
    public void tearDown(){
        driver.close();
        driver.quit();
    }
}
