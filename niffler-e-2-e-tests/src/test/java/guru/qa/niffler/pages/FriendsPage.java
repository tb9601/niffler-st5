package guru.qa.niffler.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class FriendsPage {
    ElementsCollection people = $$(".main-content__section tbody tr");
    By USERNAME = By.cssSelector("tr td:nth-of-type(2)");
    By CONFIRMATION_BUTTON = By.cssSelector("button.button-icon_type_submit");
    By REMOVE_FRIEND_BUTTON = By.cssSelector("button.button-icon_type_close");
    By INVITATION_STATUS = By.cssSelector("td .abstract-table__buttons");
    SelenideElement WITHOUT_FRIENDS_STATUS = $("div[style*='20px']");

    public FriendsPage confirmationFriendship(UserJson userJson) {
        people.should(CollectionCondition.sizeGreaterThan(0));
        people.asDynamicIterable().stream().filter(user -> user.$(USERNAME).getText().equals(userJson.username()))
                .findFirst().get()
                .$(CONFIRMATION_BUTTON).scrollTo().click();
        verifyFriendship(userJson);
        return this;
    }

    public FriendsPage verifyFriendship(UserJson userJson) {
        people.should(CollectionCondition.sizeGreaterThan(0));
        people.asDynamicIterable().stream().filter(user -> user.$(USERNAME).getText().equals(userJson.username()))
                .findFirst().get()
                .$(INVITATION_STATUS).should(Condition.text("You are friends"));
        return this;
    }

    public FriendsPage removeAllFriends() {
        int friendsSize = people.size();
        for (int i = 0; i < friendsSize; i++) {
            people.get(i).$(REMOVE_FRIEND_BUTTON).click();
            new HeaderPage().popupTextShouldBe("Friend is deleted");
            people.should(CollectionCondition.size(friendsSize - 1));
            friendsSize-=1;
        }
        WITHOUT_FRIENDS_STATUS.should(Condition.text("There are no friends yet!"));
        return this;
    }
}
