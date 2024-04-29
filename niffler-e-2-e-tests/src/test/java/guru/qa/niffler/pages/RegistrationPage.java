package guru.qa.niffler.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;

import java.util.Collection;

import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {
    private final SelenideElement userNameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitPasswordInput = $("#passwordSubmit");
    private final SelenideElement signUpButton = $(".form__submit");
    private final SelenideElement FORM_TITLE = $(".form__paragraph");
    private final SelenideElement SIGN_IN_BUTTON = $(".form__paragraph a[href]");

    public RegistrationPage fillRegistrationPage(UserJson userJson) {
        userNameInput.val(userJson.username());
        passwordInput.val(userJson.testData().password());
        submitPasswordInput.val(userJson.testData().password());

        signUpButton.should(Condition.clickable);
        signUpButton.click();
        FORM_TITLE.should(Condition.text("Congratulations! You've registered!"));
        return this;
    }

    public RegistrationPage clickSignInButton() {
        $(SIGN_IN_BUTTON).click();
        return this;
    }
}
