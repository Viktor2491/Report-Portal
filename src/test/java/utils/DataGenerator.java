package utils;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Authorization {
        private static RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        private Authorization() {
        }


        private static utils.ClientData generateUser(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new utils.ClientData(
                    faker.name().username(),
                    faker.internet().password(),
                    status);
        }


        public static utils.ClientData registerUser(String locale, String status) {
            utils.ClientData user = generateUser(locale, status);
            // сам запрос
            given()// "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(user) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
            return user;

        }

        public static String invalidLogin() {
            Faker faker = new Faker(new Locale("en"));
            return faker.name().username();
        }

        public static String invalidPassword() {
            Faker faker = new Faker(new Locale("en"));
            return faker.internet().password();
        }
    }
}
