package main;

import core.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class MainTest extends BaseTest {

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
        MainPage mainPage = new MainPage();
        mainPage.personalAreaVisible();
        mainPage.scrollToDown(ingredientToScroll);
        mainPage.clickIngredientButton(ingredient);
        assertTrue(mainPage.ingredientVisible(ingredient));
    }
}
