package register;

import api.client.UserApiClient;
import api.model.user.LoginUserRequest;
import api.model.user.LoginUserResponse;
import api.model.user.User;
import core.BaseTest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.MainPage;
import page.RegisterPage;

import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final RegisterPage registerPage = new RegisterPage();
    private final User user = getRandomUser();
    private final String name = user.getName();
    private final String email = user.getEmail();
    private final String password = user.getPassword();

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierSuccess() {
        mainPage.clickPersonalArea();

        loginPage.clickRegistrationLink();

        setRegistration(name, email, password);

        registerPage.assertInputText(name);
        registerPage.assertInputText(email);

        registerPage.clickRegisterButton();

        assertThat(loginPage.enterButtonVisible()).isTrue();

        auth(email, password);

        assertThat(mainPage.createOrderButtonVisible()).isTrue();

        deleteAccount();
    }

    @Test
    @DisplayName("Ошибка при создание курьера")
    public void createCourierWrong() {
        mainPage.clickPersonalArea();

        loginPage.clickRegistrationLink();

        setRegistration(user.getName(), user.getEmail(), user.getPassword().substring(0, 4));
        registerPage.clickRegisterButton();
        assertThat(registerPage.getTextErrorWrongPassword()).isEqualTo("Некорректный пароль");
    }

    @Step("Ввод данных для регистрации")
    private void setRegistration(String name, String email, String password) {
        registerPage.setEmail(email);
        registerPage.setName(name);
        registerPage.setPassword(password);
    }

    @Step("Вход в личный кабинет")
    private void auth(String email, String password) {
        loginPage.clickEnterTitle();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();
    }

    @Step("Удалить аккаунт созданного курьера")
    private void deleteAccount() {
        UserApiClient userApiClient = new UserApiClient();
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response loginResponse = userApiClient.loginUser(loginUserRequest);
        assertThat(loginResponse.statusCode()).isEqualTo(SC_OK);

        LoginUserResponse loginUserResponse = loginResponse.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response deleteResponse = userApiClient.deleteUser(token);
        assertThat(deleteResponse.statusCode()).isEqualTo(SC_ACCEPTED);
    }
}
