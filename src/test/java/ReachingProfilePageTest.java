import Resources.*;
import PageObject.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.assertEquals;

public class ReachingProfilePageTest {
    TestData testData;
    HTTPClient httpClient;
    MainPage mainPage;
    LoginPage loginPage;
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
        accountPage = page(AccountPage.class);
        httpClient.doCreateUserRequest();
        open(testData.getMainPageURL(), MainPage.class);
    }

    @After
    public void deleteData() {
        httpClient.doDeleteUserRequest();
        closeWindow();
    }

    @Test
    @DisplayName("Тест перехода на страницу профиля")
    @Description("Проверка возможности перехода на страницу профиля авторизованным пользователем")
    public void isReachingProfilePageTest() {
        mainPage.doLoginButtonClick();
        loginPage.loginUsingTestData(testData);
        mainPage.doAccountButtonClick();
        Boolean expected = true;
        assertEquals(expected, accountPage.isProfileTabButtonVisible());
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        String expectedURL = testData.getAccountPageURL();
        assertEquals(expectedURL, actualURL);
    }
}