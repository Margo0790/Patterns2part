package ru.netology.testmode.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:7777");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("button").click();
        $("div .heading").shouldHave(text("Личный кабинет")).should(visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldShowErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .should(visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldShowErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован"))
                .should(visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldShowErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("button").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .should(visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldShowErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] .input__control").setValue(wrongPassword);
        $("button").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Неверно указан логин или пароль"))
                .should(visible);
    }
}