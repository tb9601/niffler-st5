package guru.qa.niffler.pages;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import org.openqa.selenium.By;

public class AllPeoplePage {
    ElementsCollection people = $$(".main-content__section tbody tr");
    By ADD_TO_FRIENDS_BUTTON = By.cssSelector("[data-tooltip-id='add-friend']");
    By USERNAME = By.cssSelector("tr td:nth-of-type(2)");
    By INVITATION_STATUS = By.cssSelector("td .abstract-table__buttons");


    public AllPeoplePage addToFriends(UserJson userJson) {
        people.should(CollectionCondition.sizeGreaterThan(0));
        people.asDynamicIterable().stream().filter(user -> user.$(USERNAME).getText().equals(userJson.username()))
                .findFirst().get()
                .$(ADD_TO_FRIENDS_BUTTON).scrollTo().click();


        people.asDynamicIterable().stream().filter(user -> user.$(USERNAME).getText().equals(userJson.username()))
                .findFirst().get()
                .$(INVITATION_STATUS).should(Condition.text("Pending invitation"));

        return this;
    }
}
