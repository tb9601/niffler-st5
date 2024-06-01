package guru.qa.niffler.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class HeaderPage {
    SelenideElement ALL_PEOPLE_BUTTON = $(".header [data-tooltip-id='people']");
    SelenideElement FRIENDS_BUTTON = $(".header [data-tooltip-id='friends']");
    SelenideElement POPUP = $("div.Toastify__toast-body");
    SelenideElement LOGOUT_BUTTON = $("button.button-icon_type_logout");

    public HeaderPage clickAllPeopleButton() {
        ALL_PEOPLE_BUTTON.click();
        return this;
    }

    public HeaderPage clickFriendsButton() {
        FRIENDS_BUTTON.click();
        return this;
    }

    public HeaderPage popupTextShouldBe(String text) {
        POPUP.shouldBe(Condition.text(text));
        return this;
    }

    public HeaderPage logout() {
        LOGOUT_BUTTON.should(Condition.clickable);
        LOGOUT_BUTTON.click();
        return this;
    }
}
