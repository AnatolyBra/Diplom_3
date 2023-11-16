package page;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
    /**
     *
     * /account/profile
     *
     */
    public ProfilePage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public ProfilePage() {
    }

    private final By profileLink = By.xpath(".//*[text()='Профиль']");
    private final By constructorButton = By.xpath(".//*[text()='Конструктор']");
    private final By exitButton = By.xpath(".//*[text()='Выход']");
    private final By logoButton = By.xpath(".//*[contains(@class,'logo')]");
    @Step("Отображается текст 'Профиль'")
    public boolean profileLinkVisible(){
        return driver.findElement(profileLink).isDisplayed();
    }
    @Step("Нажать на кнопку 'Конструктор'")
    public void clickConstructorButton(){
        driver.findElement(constructorButton).click();
    }
    @Step("Выйти из личного кабинета, нажав на кнопку 'Выход'")
    public void clickExitButton(){
        driver.findElement(exitButton).click();
    }
    @Step("Нажать на логотип")
    public void clickLogoButton(){
        driver.findElement(logoButton).click();
    }
}
