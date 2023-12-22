package main;

import core.BaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import page.MainPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MainTest extends BaseTest {
    private final MainPage mainPage = new MainPage();

    public static Stream<Arguments> clickAndCheck() {
        return Stream.of(
                arguments("Начинки", "Булки"),
                arguments("Соусы", "Начинки"),
                arguments("Булки", "Начинки")
        );
    }

    @ParameterizedTest
    @MethodSource("clickAndCheck")
    public void chooseMenu(String ingredient, String ingredientToScroll) {
        mainPage.personalAreaVisible();
        mainPage.scrollToDown(ingredientToScroll);

        clickIngredient(ingredient);

        assertIngredientVisible(ingredient);
    }

    @Step("Кликнуть на кнопку ингредиента '{ingredient}'")
    public void clickIngredient(String ingredient) {
        mainPage.clickIngredientButton(ingredient);
    }

    @Step("Проверить что ингредиент '{ingredient}' отображается в меню")
    public void assertIngredientVisible(String ingredient) {
        assertTrue(mainPage.ingredientVisible(ingredient));
    }
}
