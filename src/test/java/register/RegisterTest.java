package register;

import api.client.CourierApiClient;
import api.model.courier.DeleteCourierRequest;
import api.model.courier.LoginCourierRequest;
import api.model.courier.LoginCourierResponse;
import api.model.courier.User;
import core.BaseTest;
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

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierSuccess() {
        mainPage.clickPersonalArea();

        loginPage.clickRegistrationLink();

        registerPage.setPassword(user.getPassword());
        registerPage.setName(user.getName());
        registerPage.setEmail(user.getEmail());

        registerPage.assertInputText(user.getName());
        registerPage.assertInputText(user.getEmail());

        registerPage.clickRegisterButton();

        assertTrue(loginPage.enterButtonVisible());

        loginPage.clickEnterTitle();

        loginPage.setEmail(user.getEmail());
        loginPage.setPassword(user.getPassword());
        loginPage.clickEnterButton();

        assertTrue(mainPage.createOrderButtonVisible());

        CourierApiClient courierApiClient = new CourierApiClient();
        LoginCourierRequest loginCourierRequest = new LoginCourierRequest(user.getEmail(), user.getPassword());
        Response loginResponse = courierApiClient.loginCourier(loginCourierRequest);
        assertEquals(SC_OK, loginResponse.statusCode());

        LoginCourierResponse loginCourierResponse = loginResponse.as(LoginCourierResponse.class);
        String token = loginCourierResponse.getAccessToken();

        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest(user.getEmail(), user.getPassword());
        Response deleteResponse = courierApiClient.deleteCourier(deleteCourierRequest, token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
    }

    @Test
    @DisplayName("Ошибка при создание курьера")
    public void createCourierWrong() {
        mainPage.clickPersonalArea();

        loginPage.clickRegistrationLink();

        registerPage.setEmail(user.getEmail());
        registerPage.setName(user.getName());
        registerPage.setPassword(user.getPassword().substring(0, 4));

        registerPage.clickRegisterButton();

        assertEquals("Некорректный пароль", registerPage.getTextErrorWrongPassword());
    }

}
