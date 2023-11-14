package api.client;

import api.model.courier.CreateCourierRequest;
import api.model.courier.DeleteCourierRequest;
import api.model.courier.LoginCourierRequest;
import api.model.courier.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.config.ConfigApp.BASE_URL;

public class CourierApiClient extends BaseApiClient{
    @Step("Создание курьера")
    public Response createCourier(CreateCourierRequest createCourierRequest) {
        return getPostSpec()
                .body(createCourierRequest)
                .when()
                .post(BASE_URL + "/auth/register");
    }
    @Step("Авторизация курьера")
    public Response loginCourier(LoginCourierRequest loginCourierRequest) {
        return getPostSpec()
                .body(loginCourierRequest)
                .when()
                .post(BASE_URL + "/auth/login");
    }

    @Step("Удаление курьера")
    public Response deleteCourier(DeleteCourierRequest deleteCourierRequest, String accessToken) {
        return getPostSpec()
                .header("Authorization", accessToken)
                .body(deleteCourierRequest)
                .delete(BASE_URL + "/auth/user");
    }
}
