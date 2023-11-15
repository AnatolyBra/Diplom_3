package account;

import api.client.CourierApiClient;
import api.model.courier.CreateCourierRequest;
import api.model.courier.CreateCourierResponse;
import api.model.courier.DeleteCourierRequest;
import core.BaseTest;
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

    @Test
    @DisplayName("Переход в личный кабинет")
    public void personalArea() {
        CourierApiClient courierApiClient = new CourierApiClient();
        CreateCourierRequest createCourierRequest = getRandomCourier();
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getSuccess());

        String token = createCourierResponse.getAccessToken();
        String email = createCourierRequest.getEmail();
        String password = createCourierRequest.getPassword();

        mainPage.clickPersonalArea();

        loginPage.clickEnterTitle();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();

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

        DeleteCourierRequest deleteCourierRequest = new DeleteCourierRequest(email, password);
        Response deleteResponse = courierApiClient.deleteCourier(deleteCourierRequest, token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
    }
}
