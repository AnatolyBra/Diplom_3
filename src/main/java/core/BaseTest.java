package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import static api.config.ConfigApp.*;
@Slf4j
abstract public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp()  throws Exception{
//        String browserName = System.getProperty("browserName");
        String browserName = "chrome";

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().driverVersion(CHROME_DRIVER).setup();
//                WebDriverManager.firefoxdriver().browserInDocker().setup();
//                WebDriverManager.chromedriver().browserInDocker().setup();

                ChromeOptions options = new ChromeOptions();

                options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

                driver = new RemoteWebDriver( new URL("http://selenium-hub:4444/"), options);
                driver.manage().window().maximize();
                driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                BasePage.setDriver(driver);
                driver.get(BASE_URL);
                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", YANDEX_DRIVER_PATH);
                ChromeOptions options1 = new ChromeOptions();
                options1.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options1);
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

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
