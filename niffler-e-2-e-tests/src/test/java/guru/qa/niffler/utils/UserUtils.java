package guru.qa.niffler.utils;

import com.github.javafaker.Faker;
import guru.qa.niffler.model.UserJson;

import java.util.UUID;

import static guru.qa.niffler.model.CurrencyValues.RUB;

public class UserUtils {
    private static final String DEFAULT_PASSWORD = "12345";

    private static final Faker FAKER = new Faker();

    public static UserJson generateRandomUserJson() {
        String userName = FAKER.name().username();
        String password = FAKER.cat().name();
        return UserJson.simpleUser(userName, password);
    }
}
