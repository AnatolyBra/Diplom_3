package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    /**
     *
     * /login
     *
     */
    public LoginPage(WebDriver driver) {
        BasePage.driver = driver;
    }
    public LoginPage(){

    }
    private final By registrationLink = By.xpath(".//*[text()='Зарегистрироваться']");

    private final By emailInput = By.xpath(".//*[text()='Email']/parent::div/input");
    private final By passwordInput = By.xpath(".//*[text()='Пароль']/parent::div/input");
    private final By enterButton = By.xpath(".//*[text()='Войти']");
    private final By enterTitle = By.xpath(".//h2[text()='Вход']");
    @Step("Кликнуть на ссылку 'Зарегестрироваться'")
    public void clickRegistrationLink(){
        driver.findElement(registrationLink).click();
    }
    @Step("Кликнуть на кнопку 'Войти'")
    public void clickEnterButton(){
        driver.findElement(enterButton).click();
    }
    @Step("Кликнуть на заголовок 'Войти'")
    public void clickEnterTitle(){
        driver.findElement(enterTitle).click();
    }
    @Step("Отображается заголовок 'Войти'")
    public boolean enterTitleVisible(){
        return driver.findElement(enterTitle).isDisplayed();
    }
    @Step("Ввести в поле ввода email {email}")
    public void setEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }
    @Step("Ввести в поле ввода пароль {password}")
    public void setPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }
    @Step("Отображается заголовок 'Войти'")
    public boolean enterButtonVisible() {
        return driver.findElement(enterButton).isDisplayed();
    }
}
