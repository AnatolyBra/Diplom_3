package api.helper;

import api.model.courier.CreateCourierRequest;
import com.github.javafaker.Faker;

public class CourierGenerator {
    public static CreateCourierRequest getRandomCourier() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = faker.animal().name() + name;
        return new CreateCourierRequest(email, password, name);
    }
}
