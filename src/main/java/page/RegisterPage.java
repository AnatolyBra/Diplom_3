package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    /**
     * /register
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
    @Step("Ввести в поле ввода имя '{name}'")
    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }
    @Step("Ввести в поле ввода email '{email}'")
    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }
    @Step("Ввести в поле ввода пароль '{password}'")
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку 'Зарегестрироваться'")
    public void clickRegisterButton() {
        registerButtonVisible();
        driver.findElement(registerButton).click();
    }

    @Step("Получить текст ошибки подсказки")
    public String getTextErrorWrongPassword() {
        return driver.findElement(errorWrongPassword).getText();
    }

    @Step("Кнопка 'Зарегистрироваться' видна")
    public boolean registerButtonVisible() {
        return driver.findElement(registerButton).isDisplayed();
    }
    @Step("В поле ввода отображается '{text}'")
    public boolean assertInputText(String text) {
        return driver.findElement(By.xpath(".//input[@value='" + text + "']")).isDisplayed();
    }
}
