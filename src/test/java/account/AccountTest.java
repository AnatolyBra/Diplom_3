package account;

import api.client.CourierApiClient;
import api.model.courier.CreateCourierRequest;
import api.model.courier.CreateCourierResponse;
import api.model.courier.DeleteCourierRequest;
import api.model.courier.DeleteCourierResponse;
import core.BaseTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import page.LoginPage;
import page.MainPage;

import static api.helper.CourierGenerator.getRandomCourier;
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

    private DeleteCourierRequest deleteCourierRequest;
    private CourierApiClient courierApiClient;
    private String token;
    private String email;
    private String password;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        CreateCourierRequest createCourierRequest = getRandomCourier();
        Response createResponse = courierApiClient.createCourier(createCourierRequest);
        assertEquals(SC_OK, createResponse.statusCode());
        CreateCourierResponse createCourierResponse = createResponse.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.getSuccess());
        token = createCourierResponse.getAccessToken();

        email = createCourierRequest.getEmail();
        password = createCourierRequest.getPassword();

        deleteCourierRequest = new DeleteCourierRequest(email, password);
    }

    @After
    public void down() {
        Response deleteResponse = courierApiClient.deleteCourier(deleteCourierRequest, token);
        assertEquals(SC_ACCEPTED, deleteResponse.statusCode());
        DeleteCourierResponse deleteCourierResponse = deleteResponse.as(DeleteCourierResponse.class);
        assertTrue(deleteCourierResponse.getSuccess());
    }

    public AccountTest(String url, String button) {
        this.url = url;
        this.button = button;
    }

    @Parameterized.Parameters
    public static Object[][] getEnterAccount() {
        return new Object[][]{
                {"https://stellarburgers.nomoreparties.site/", "Личный кабинет"},
                {"https://stellarburgers.nomoreparties.site/", "Войти в аккаунт"},
                {"https://stellarburgers.nomoreparties.site/register", "Войти"},
                {"https://stellarburgers.nomoreparties.site/forgot-password", "Войти"}
        };
    }

    @Test
    @DisplayName("Тестируем вход в личный кабинет")
    public void accountAnyCheck() {
        driver.get(url);

        driver.findElement(By.xpath(".//*[text()='" + button + "']")).click();

        loginPage.clickEnterTitle();

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickEnterButton();

        assertTrue(mainPage.createOrderButtonVisible());
    }

}
