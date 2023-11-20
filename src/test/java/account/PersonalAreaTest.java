package account;

import api.client.UserApiClient;
import api.model.user.CreateUserResponse;
import api.model.user.LoginUserRequest;
import api.model.user.LoginUserResponse;
import api.model.user.User;
import core.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;

import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonalAreaTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final ProfilePage profilePage = new ProfilePage();
    private final User user = getRandomUser();
    private final String email = user.getEmail();
    private final String password = user.getPassword();
    private final UserApiClient userApiClient = new UserApiClient();

    @Test
    @DisplayName("Переход в личный кабинет")
    public void personalArea() {
        createUser();

        mainPage.clickPersonalArea();

        auth(email, password);

        assertTrue(mainPage.createOrderButtonVisible());

        mainPage.clickPersonalArea();

        assertTrue(profilePage.profileLinkVisible());

        profilePage.clickConstructorButton();

        assertTrue(mainPage.createOrderButtonVisible());

        mainPage.clickPersonalArea();
        assertTrue(profilePage.profileLinkVisible());

        profilePage.clickLogoButton();

        assertTrue(mainPage.createOrderButtonVisible());

        mainPage.clickPersonalArea();

        profilePage.clickExitButton();

        assertTrue(loginPage.enterTitleVisible());

        deleteAccount(email, password);
    }

    @Step("Создание курьера")
    public void createUser() {
        Response createResponse = userApiClient.createUser(user);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateUserResponse createUserResponse = createResponse.as(CreateUserResponse.class);
        assertTrue(createUserResponse.getSuccess());
    }

    @Step("Удалить аккаунт созданного курьера")
    private void deleteAccount(String email, String password) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response loginResponse = userApiClient.loginUser(loginUserRequest);
        assertEquals(SC_OK, loginResponse.statusCode());

        LoginUserResponse loginUserResponse = loginResponse.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response deleteResponse = userApiClient.deleteUser(token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
    }

    @Step("Вход в личный кабинет")
    private void auth(String email, String password) {
        loginPage.clickEnterTitle();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();
    }
}
