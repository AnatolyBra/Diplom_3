package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static api.config.ConfigApp.*;
@Slf4j
abstract public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
//        String browserName = System.getProperty("browserName");
        String browserName = "chrome";

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().driverVersion(CHROME_DRIVER).setup();

                driver = new ChromeDriver();

                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                BasePage.setDriver(driver);
                driver.get(BASE_URL);
                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", YANDEX_DRIVER_PATH);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                BasePage.setDriver(driver);
                driver.get(BASE_URL);

                break;
            default:
                throw new RuntimeException("Browser is not detected");
        }
    }

    @AfterEach
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
