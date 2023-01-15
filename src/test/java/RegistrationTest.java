import PageObject.AccountPage;
import PageObject.LoginPage;
import PageObject.MainPage;
import PageObject.RegistrationPage;
import Resources.HTTPClient;
import Resources.TestData;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;

public class RegistrationTest {
    TestData testData;
    HTTPClient httpClient;
    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registrationPage;
    AccountPage accountPage;

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
        accountPage = page(AccountPage.class);
        open(testData.getMainPageURL(), MainPage.class);
    }

    @After
    public void deleteData() {
        httpClient.doDeleteUserRequest();
    }

    @Test
    @DisplayName("Тест возможной регистрации пользователя")
    @Description("Проверка возмозности регистрации при при заполнении обязательных полей валидными данными")
    public void registrationTest() {
        mainPage.doAccountButtonClick();
        loginPage.doRegistrationLinkClick();
        registrationPage.doFillRegistrationFields(0, testData.getTestName())
                .doFillRegistrationFields(1, testData.getTestEmail())
                .doFillRegistrationFields(2, testData.getTestPassword())
                .doRegistrationButtonClick();
        loginPage.isLoginHeaderVisible()
                .doFillLoginFields(0, testData.getTestEmail())
                .doFillLoginFields(1, testData.getTestPassword())
                .doLoginButtonClick();

        mainPage.isOrderButtonVisible();
        boolean expected = true;
        boolean actual = mainPage.isOrderButtonDisplayed();
        assertEquals(expected, actual);
    }
}

