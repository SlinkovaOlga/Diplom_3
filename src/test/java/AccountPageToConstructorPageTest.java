import Resources.*;
import PageObject.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;

public class AccountPageToConstructorPageTest {
    TestData testData;
    HTTPClient httpClient;
    MainPage mainPage;
    LoginPage loginPage;
    AccountPage accountPage;
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
        passwordRecoveryPage = page(PasswordRecoveryPage.class);
        accountPage = page(AccountPage.class);
        httpClient.doCreateUserRequest();
        open(testData.getMainPageURL(), MainPage.class);
        mainPage.doLoginButtonClick();
        loginPage.loginUsingTestData(testData);
        mainPage.doAccountButtonClick();
    }

    @After
    public void deleteData() {
        httpClient.doDeleteUserRequest();
    }

    @Test
    @DisplayName("Тест перехода на страницу конструктора по кнопке 'Конструктор'")
    @Description("Проверка перехода на страницу конструктора после нажатия на кнопку 'Конструктор'")
    public void isConstructorReachableViaConstructorButtonTest() {
        accountPage.doConstructorButtonClick();
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        String expectedURL = testData.getMainPageURL();
        assertEquals(expectedURL, actualURL);
    }

    @Test
    @DisplayName("Тест перехода на страницу конструктора при нажатии на логотип")
    @Description("Проверка перехода на страницу конструктора после нажатия на логотип")
    public void isConstructorReachableViaHeaderLogoButtonTest() {
        accountPage.doHeaderLogoClick();
        String actualURL = WebDriverRunner.getWebDriver().getCurrentUrl();
        String expectedURL = testData.getMainPageURL();
        assertEquals(expectedURL, actualURL);
    }
}