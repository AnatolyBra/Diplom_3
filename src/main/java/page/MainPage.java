package page;

import core.BasePage;
import io.qameta.allure.Step;
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
    @Step("Скролл до {text} в меню")
    public void scrollToDown(String text) {
        WebElement element = driver.findElement(By.xpath(".//h2[text()='" + text + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }
    @Step("Кликнуть на кнопку 'Личный Кабинет'")
    public void clickPersonalArea() {
        driver.findElement(personalArea).click();
    }
    @Step("Отображается кнопка 'Личный Кабинет'")
    public boolean personalAreaVisible() {
        return driver.findElement(personalArea).isDisplayed();
    }
    @Step("Кликнуть на кнопку {ingredient}")
    public void clickIngredientButton(String ingredient) {
        driver.findElement(By.xpath(".//*[text()='" + ingredient + "']")).click();
    }
    @Step("В меню отображается {ingredient}")
    public boolean ingredientVisible(String ingredient) {
        return driver.findElement(By.xpath(".//h2[text()='" + ingredient + "']")).isDisplayed();
    }
    @Step("Отображается кнопка создать заказ")
    public boolean createOrderButtonVisible() {
        return driver.findElement(createOrderButton).isDisplayed();
    }
}
