package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver) {
        BasePage.driver = driver;
    }
    public MainPage(){

    }
    private final By personalArea = By.xpath(".//*[text()='Личный Кабинет']");
    private final By createOrderButton = By.xpath(".//*[text()='Оформить заказ']");

    public void clickPersonalArea() {
        driver.findElement(personalArea).click();
    }

    public boolean createOrderButtonVisible() {
        return driver.findElement(createOrderButton).isDisplayed();
    }
}
