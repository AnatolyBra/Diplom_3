package account;

import api.client.UserApiClient;
import api.model.user.CreateUserResponse;
import api.model.user.DeleteUserResponse;
import api.model.user.User;
import core.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import page.LoginPage;
import page.MainPage;

import java.util.stream.Stream;

import static api.config.ConfigApp.*;
import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AccountTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();


    public static Stream<Arguments> getEnterAccount() {
        return Stream.of(
                arguments(BASE_URL, "Личный Кабинет"),
                arguments(BASE_URL, "Войти в аккаунт"),
                arguments(BASE_URL_REGISTER, "Войти"),
                arguments(BASE_URL_FORGOT_PASSWORD, "Войти")
        );
    }

    @ParameterizedTest
    @MethodSource("getEnterAccount")
    @DisplayName("Тестируем точки входа в аккаунт")
    public void accountAnyCheck(String url, String button) {
        UserApiClient userApiClient = new UserApiClient();
        User createUserRequest = getRandomUser();
        Response createResponse = userApiClient.createUser(createUserRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateUserResponse createUserResponse = createResponse.as(CreateUserResponse.class);
        assertTrue(createUserResponse.getSuccess());
        String token = createUserResponse.getAccessToken();
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();

        driver.get(url);

        driver.findElement(By.xpath(".//*[text()='" + button + "']")).click();

        loginPage.enterButtonVisible();

        loginPage.clickEnterTitle();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();

        assertTrue(mainPage.createOrderButtonVisible());

        Response deleteResponse = userApiClient.deleteUser(token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
        DeleteUserResponse deleteUserResponse = deleteResponse.as(DeleteUserResponse.class);
        assertTrue(deleteUserResponse.getSuccess());
    }

}
