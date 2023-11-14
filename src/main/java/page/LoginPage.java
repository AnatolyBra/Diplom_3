package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
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

    public void clickRegistrationLink(){
        driver.findElement(registrationLink).click();
    }
    public void clickEnterButton(){
        driver.findElement(enterButton).click();
    }
    public void clickEnterTitle(){
        driver.findElement(enterTitle).click();
    }
    public void setEmail(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }
    public void setPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public boolean enterButtonVisible() {
        // ждем 8 секунд, пока появится веб-элемент с нужным текстом
        return driver.findElement(By.xpath(".//*[text()='Войти']")).isDisplayed();
    }
}
