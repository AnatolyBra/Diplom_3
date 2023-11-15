package page;

import core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {
    public MainPage(WebDriver driver) {
        BasePage.driver = driver;
    }

    public MainPage() {

    }

    private final By personalArea = By.xpath(".//*[text()='Личный Кабинет']");
    private final By createOrderButton = By.xpath(".//*[text()='Оформить заказ']");

    public void scrollToDown(String text) {
        WebElement element = driver.findElement(By.xpath(".//h2[text()='" + text + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickPersonalArea() {
        driver.findElement(personalArea).click();
    }

    public boolean personalAreaVisible() {
        return driver.findElement(personalArea).isDisplayed();
    }

    public void clickIngredientButton(String ingredient) {
        driver.findElement(By.xpath(".//*[text()='" + ingredient + "']")).click();
    }

    public boolean ingredientVisible(String ingredient) {
        return driver.findElement(By.xpath(".//h2[text()='" + ingredient + "']")).isDisplayed();
    }

    public boolean createOrderButtonVisible() {
        return driver.findElement(createOrderButton).isDisplayed();
    }
}
