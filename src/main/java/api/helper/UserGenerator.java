package api.helper;

import com.github.javafaker.Faker;
import api.model.courier.User;

public class UserGenerator {
    public static User getRandomUser() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String name = faker.name().firstName();
        String password = faker.name().username() + "name";
        return new User(email, name, password);
    }
}
