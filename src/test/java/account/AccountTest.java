package account;

import api.client.UserApiClient;
import api.model.user.CreateUserResponse;
import api.model.user.DeleteUserResponse;
import api.model.user.User;
import core.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import page.LoginPage;
import page.MainPage;

import static api.config.ConfigApp.*;
import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AccountTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final String url;
    private final String button;

    private UserApiClient userApiClient;
    private String token;
    private String email;
    private String password;

    public AccountTest(String url, String button) {
        this.url = url;
        this.button = button;
    }

    @Parameterized.Parameters
    public static Object[][] getEnterAccount() {
        return new Object[][]{
                {BASE_URL, "Личный Кабинет"},
                {BASE_URL, "Войти в аккаунт"},
                {BASE_URL_REGISTER, "Войти"},
                {BASE_URL_FORGOT_PASSWORD, "Войти"}
        };
    }

    @Test
    @DisplayName("Тестируем точки входа в аккаунт")
    public void accountAnyCheck() {
        userApiClient = new UserApiClient();
        User createUserRequest = getRandomUser();
        Response createResponse = userApiClient.createUser(createUserRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateUserResponse createUserResponse = createResponse.as(CreateUserResponse.class);
        assertTrue(createUserResponse.getSuccess());
        token = createUserResponse.getAccessToken();
        email = createUserRequest.getEmail();
        password = createUserRequest.getPassword();

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
