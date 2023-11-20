package api.helper;

import api.model.user.CreateUserRequest;
import com.github.javafaker.Faker;

public class CourierGenerator {
    public static CreateUserRequest getRandomCourier() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = faker.animal().name() + name;
        return new CreateUserRequest(email, password, name);
    }
}
