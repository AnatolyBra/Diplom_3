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
//        for testing in Chrome Browser
//        WebDriverManager.chromedriver().driverVersion("119.0.6045.123").setup();
//
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
//        BasePage.setDriver(driver);
//        driver.get("https://stellarburgers.nomoreparties.site/");

//         for testing in Yandex Browser
        System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
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
