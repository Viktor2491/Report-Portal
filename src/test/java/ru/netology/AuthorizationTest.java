package ru.netology;

import com.codeborne.selenide.Selenide;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthorizationTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }
    @Test
    @DisplayName("Валидные логин и пароль, статус active")
    void shouldLogin() {
        val user = DataGenerator.Authorization.registerUser("en","active");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("body div#root h2").shouldHave(text(" Личный кабинет"));
    }

    @Test
    @DisplayName("Невалидные логин и пароль")
    void shouldNotifyAboutInvalidLoginOrPassword() {
        val user = DataGenerator.Authorization.registerUser("en","active");
        $("[name='login']").setValue(DataGenerator.Authorization.invalidLogin());
        $("[name='password']").setValue(DataGenerator.Authorization.invalidPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Валидный логин и невалидный пароль, статус blocked")
    void shouldNotifyAboutInvalidPasswordForBlockedUser() {
        val user = DataGenerator.Authorization.registerUser("en","blocked");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(DataGenerator.Authorization.invalidPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Валидный логин и невалидный пароль, статус active")
    void shouldNotifyAboutInvalidPasswordForActiveUser() {
        val user = DataGenerator.Authorization.registerUser("en","active");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(DataGenerator.Authorization.invalidPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Неалидный логин и валидный пароль, статус active")
    void shouldNotifyAboutInvalidLoginForActiveUser() {
        val user = DataGenerator.Authorization.registerUser("en","active");
        $("[name='login']").setValue(DataGenerator.Authorization.invalidLogin());
        $("[name='password']").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Неалидный логин и валидный пароль, статус blocked")
    void shouldNotifyAboutInvalidLoginForBlockedUser() {
        val user = DataGenerator.Authorization.registerUser("en","blocked");
        $("[name='login']").setValue(DataGenerator.Authorization.invalidLogin());
        $("[name='password']").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Валидные логин и пароль, статус blocked")
    void shouldNotifyAboutUserBlocked() {
        val user = DataGenerator.Authorization.registerUser("en","blocked");
        $("[name='login']").setValue(user.getLogin());
        $("[name='password']").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(text("Пользователь заблокирован"));
    }

}
