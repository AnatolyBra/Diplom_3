package api.client;

import api.model.user.CreateUserRequest;
import api.model.user.LoginUserRequest;
import api.model.user.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserApiClient extends BaseApiClient {
    @Step("Создание полльзователя")
    public Response createUser(User user) {
        return getPostSpec()
                .body(user)
                .post("/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response loginUser(LoginUserRequest loginUserRequest) {
        return getPostSpec()
                .body(loginUserRequest)
                .post("/auth/login");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return getPostSpec()
                .header("Authorization", accessToken)
                .delete("/auth/user");
    }
}
