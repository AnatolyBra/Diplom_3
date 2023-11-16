package main;

import core.BaseTest;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MainTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final String ingredient;
    private final String ingredientToScroll;

    public MainTest(String ingredient, String ingredientToScroll) {
        this.ingredient = ingredient;
        this.ingredientToScroll = ingredientToScroll;
    }

    @Parameterized.Parameters
    public static Object[][] clickAndCheck() {
        return new Object[][]{
                {"Начинки", "Булки"},
                {"Соусы", "Начинки"},
                {"Булки", "Начинки"}
        };
    }

    @Test
    public void chooseMenu() {
        mainPage.personalAreaVisible();
        mainPage.scrollToDown(ingredientToScroll);

        clickIngredient(ingredient);

        assertIngredientVisible(ingredient);
    }

    @Step("Кликнуть на кнопку ингредиента '{ingredient}'")
    public void clickIngredient(String ingredient){
        mainPage.clickIngredientButton(ingredient);
    }

    @Step("Проверить что ингредиент '{ingredient}' отображается в меню")
    public void assertIngredientVisible(String ingredient){
        assertTrue(mainPage.ingredientVisible(ingredient));
    }
}
