import resources.*;
import pageobject.*;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;

public class LoginTest {
    TestData testData;
    HTTPClient httpClient;
    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registrationPage;
    PasswordRecoveryPage passwordRecoveryPage;

    @Before
    public void initializationData() {
        testData = new TestData();
        httpClient = new HTTPClient(testData);
        System.setProperty("selenide.browser", "Chrome");
        //System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/yandexdriver.exe");
        Configuration.browserSize = "1920x1080";
        mainPage = page(MainPage.class);
        loginPage = page(LoginPage.class);
        registrationPage = page(RegistrationPage.class);
        passwordRecoveryPage = page(PasswordRecoveryPage.class);
        httpClient.doCreateUserRequest();
        open(testData.getMainPageURL(), MainPage.class);
    }

    @After
    public void deleteData() {
        httpClient.doDeleteUserRequest();
    }

    @Test
    @DisplayName("Тест авторизации пользователя с использованием кнопки на главной странице")
    @Description("Проверка возможности авторизации через нажатие кнопки входа на главной странице")
    public void isLoginWithMainPageLoginButtonTest() {
        mainPage.isLoginButtonVisible()
                .doLoginButtonClick();
        loginPage.loginUsingTestData(testData);
        mainPage.isOrderButtonVisible();
        boolean expected = true;
        boolean actual = mainPage.isOrderButtonDisplayed();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест авторизации пользователя с использованием кнопки личного кабинета")
    @Description("Проверка возможности авторизации через клавишу перехода в личный кабинет")
    public void isLoginWithMainPageAccountButtonTest() {
        mainPage.isLoginButtonVisible()
                .doLoginButtonClick();
        loginPage.loginUsingTestData(testData);
        mainPage.isOrderButtonVisible();
        boolean expected = true;
        boolean actual = mainPage.isOrderButtonDisplayed();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест авторизации пользователя с использованием ссылки на странице регистрации")
    @Description("Проверка возможности авторизации через ссылку на странице регистрации")
    public void isLoginWithRegistrationPageLoginLinkTest() {
        mainPage.isLoginButtonVisible()
                .doLoginButtonClick();
        loginPage.doRegistrationLinkClick();
        registrationPage.doLoginLinkClick();
        loginPage.loginUsingTestData(testData);
        mainPage.isOrderButtonVisible();
        boolean expected = true;
        boolean actual = mainPage.isOrderButtonDisplayed();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест авторизации пользователя с использованием ссылки на странице восстановления пароля")
    @Description("Проверка возможности авторизации через ссылку на странице восстановления пароля")
    public void isLoginWithPasswordRecoveryPageLoginButtonTest() {
        mainPage.isLoginButtonVisible()
                .doLoginButtonClick();
        loginPage.doPasswordRecoveryLinkClick();
        passwordRecoveryPage.doLoginLinkClick();
        loginPage.loginUsingTestData(testData);
        mainPage.isOrderButtonVisible();
        boolean expected = true;
        boolean actual = mainPage.isOrderButtonDisplayed();
        assertEquals(expected, actual);
    }
}