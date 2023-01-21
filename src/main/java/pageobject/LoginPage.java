package pageobject;

import resources.TestData;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public class LoginPage {

    @FindBy(how = How.XPATH, using = "//a[text()='Зарегистрироваться']")
    private SelenideElement registrationLink;
    @FindBy(how = How.XPATH, using = "//h2[text()=\"Вход\"]")
    private SelenideElement loginHeader;
    @FindBy(how = How.XPATH, using = "(//input)")
    private ElementsCollection registrationFields;
    @FindBy(how = How.XPATH, using = "//button[text()='Войти']")
    private SelenideElement loginButton;
    @FindBy(how = How.XPATH, using = "//a[@href='/forgot-password']")
    private SelenideElement passwordRecoveryLink;

    @Step("Нажать на ссылку регистрации")
    public void doRegistrationLinkClick() {
        registrationLink.click();
    }

    @Step("Проверить видимость логотипа")
    public LoginPage isLoginHeaderVisible() {
        loginHeader.shouldBe(visible);
        return this;
    }

    @Step("Зполнить поле авторизации")
    public LoginPage doFillLoginFields(int fieldOrderNumber, String filling) {
        registrationFields.get(fieldOrderNumber).sendKeys(filling);
        return this;
    }// Метод использует нумерацию полей с нуля, по аналогии с массивом

    @Step("Нажать клавишу входа")
    public void doLoginButtonClick() {
        loginButton.click();
    }

    @Step("Дождаться видимости клавиши входа")
    public void waitLoginButton() {
        loginButton.shouldBe(visible, Duration.ofSeconds(10));
    }

    @Step("Нажать на ссылку восстановления пароля")
    public void doPasswordRecoveryLinkClick() {
        passwordRecoveryLink.click();
    }

    @Step("Войти в систему, используя тестовые данные")
    public void loginUsingTestData(TestData testData) {
        isLoginHeaderVisible()
                .doFillLoginFields(0, testData.getTestEmail())
                .doFillLoginFields(1, testData.getTestPassword())
                .doLoginButtonClick();
    }
}
