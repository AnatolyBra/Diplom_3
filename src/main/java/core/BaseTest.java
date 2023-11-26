package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static api.config.ConfigApp.*;
//@Log4j2
abstract public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        System.out.println("setUp старт");
//        String browserName = System.getProperty("browserName");
        String browserName = "yandex";

        switch (browserName) {
            case "chrome":
                new DesiredCapabilities();
                URL serverurl = new URL("http://localhost:8081/");
//                DesiredCapabilities capabilities = DesiredCapabilities.class;
//                 driver = new RemoteWebDriver(serverurl,this.createCapabilities());

                WebDriverManager.chromedriver().driverVersion(CHROME_DRIVER).setup();

                driver = WebDriverManager.chromedriver().browserInDocker()
                        .enableVnc().create();
                System.out.println(driver + " версия драйвера ");
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

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
