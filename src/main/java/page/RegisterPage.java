package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage {
    /**
     *
     * /register
     *
     */
    public RegisterPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public RegisterPage() {
    }

    private final By nameInput = By.xpath(".//*[text()='Имя']/parent::div/input");
    private final By emailInput = By.xpath(".//*[text()='Email']/parent::div/input");
    private final By passwordInput = By.xpath(".//*[text()='Пароль']/parent::div/input");
    private final By registerButton = By.xpath(".//*[text()='Зарегистрироваться']");
    private final By errorWrongPassword = By.xpath(".//p[text()='Некорректный пароль']");

    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickRegisterButton() {
        waitRegisterButton();
        driver.findElement(registerButton).click();
    }

    public String getTextErrorWrongPassword() {
        return driver.findElement(errorWrongPassword).getText();
    }


    public boolean waitRegisterButton() {
        // ждем 8 секунд, пока появится веб-элемент с нужным текстом
        return driver.findElement(registerButton).isDisplayed();
//                WebDriverWait(driver, Duration.ofSeconds(8))
//                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    public boolean assertInputText(String text) {
        // ждем 8 секунд, пока появится веб-элемент с нужным текстом
return driver.findElement(By.xpath(".//input[@value='" + text + "']")).isDisplayed();
//        new WebDriverWait(driver, Duration.ofSeconds(8))
//                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@value='" + text + "']")));
    }
}
