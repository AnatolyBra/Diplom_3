package account;

import api.client.CourierApiClient;
import api.model.courier.*;
import core.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import page.LoginPage;
import page.MainPage;
import page.ProfilePage;

import static api.helper.CourierGenerator.getRandomCourier;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonalAreaTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final ProfilePage profilePage = new ProfilePage();
    private final CreateCourierRequest createCourierRequest = getRandomCourier();
    private final String email = createCourierRequest.getEmail();
    private final String password = createCourierRequest.getPassword();

    @Test
    @DisplayName("Переход в личный кабинет")
    public void personalArea() {
        createCourier();

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
    public void createCourier() {
        CourierApiClient courierApiClient = new CourierApiClient();
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getSuccess());
    }

    @Step("Удалить аккаунт созданного курьера")
    private void deleteAccount(String email, String password) {
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

    @Step("Вход в личный кабинет")
    private void auth(String email, String password) {
        loginPage.clickEnterTitle();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();
    }
}
