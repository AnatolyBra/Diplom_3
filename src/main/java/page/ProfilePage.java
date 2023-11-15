package page;

import core.BasePage;
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

    public boolean profileLinkVisible(){
        return driver.findElement(profileLink).isDisplayed();
    }

    public void clickConstructorButton(){
        driver.findElement(constructorButton).click();
    }

    public void clickExitButton(){
        driver.findElement(exitButton).click();
    }

    public void clickLogoButton(){
        driver.findElement(logoButton).click();
    }
}
