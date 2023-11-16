package register;

import api.client.CourierApiClient;
import api.model.courier.DeleteCourierRequest;
import api.model.courier.LoginCourierRequest;
import api.model.courier.LoginCourierResponse;
import api.model.courier.User;
import core.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.RegisterPage;

import static api.helper.UserGenerator.getRandomUser;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        assertTrue(loginPage.enterButtonVisible());

        auth(email, password);

        assertTrue(mainPage.createOrderButtonVisible());

        deleteAccount();
    }

    @Test
    @DisplayName("Ошибка при создание курьера")
    public void createCourierWrong() {
        mainPage.clickPersonalArea();

        loginPage.clickRegistrationLink();

        setRegistration(user.getName(), user.getEmail(), user.getPassword().substring(0, 4));
        registerPage.clickRegisterButton();

        assertEquals("Некорректный пароль", registerPage.getTextErrorWrongPassword());
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
    private void deleteAccount(){
        CourierApiClient courierApiClient = new CourierApiClient();
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest(email, password);
        Response loginResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_OK, loginResponse.statusCode());

        LoginCourierResponse loginCourierResponse = loginResponse.as(LoginCourierResponse.class);
        String token = loginCourierResponse.getAccessToken();

        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest(email, password);
        Response deleteResponse = courierApiClient.deleteCourier(deleteCourierRequest, token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
    }
}
