import org.junit.After;
import resources.*;
import pageobject.*;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;

public class RegistrationNegativeTest {
    TestData testData;
    HTTPClient httpClient;
    MainPage mainPage;
    LoginPage loginPage;
    RegistrationPage registrationPage;
    AccountPage accountPage;
    String fiveCharacterPassword = "12345";
    private String new_email;

    @Before
    public void initializationData() {
        testData = new TestData();
        httpClient = new HTTPClient(testData);
        System.setProperty("selenide.browser", "Chrome");
        //System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/yandexdriver.exe");
        Configuration.browserSize = "1920x1080";
        mainPage = open(testData.getMainPageURL(), MainPage.class);
        loginPage = page(LoginPage.class);
        registrationPage = page(RegistrationPage.class);
        accountPage = page(AccountPage.class);
    }

    @Test
    @DisplayName("Тест невозможной регистрации пользователя")
    @Description("Проверка невозможности регистрации пользователя при вводе пароля длиной менее 6 символов")
    public void isRegistrationWithFiveCharactersPasswordImpossibleTest() {
        testData.setTestPassword(fiveCharacterPassword);
        mainPage.doAccountButtonClick();
        loginPage.doRegistrationLinkClick();
        new_email = testData.getTestEmail();
        registrationPage.doFillRegistrationFields(0, testData.getTestName())
                .doFillRegistrationFields(1, new_email)
                .doFillRegistrationFields(2, testData.getTestPassword())
                .doRegistrationButtonClick();
        boolean expected = true;
        boolean actual = registrationPage.isInvalidPasswordErrorDisplayed();
        assertEquals(expected, actual);
    }

    @After
    public void deleteData() {
        try {
            httpClient.doDeleteUserRequestForNegativeTest(new_email);
        } catch (Exception exception) {
            System.out.println("Нет пользователя на удаление!");
        }
    }
}


