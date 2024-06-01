package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@WebTest
@ExtendWith(UserQueueExtension.class)
public class UsersQueueExampleTest {

    static {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
    }

    private final WelcomePage welcomePage = new WelcomePage();
    private final LoginPage loginPage = new LoginPage();
    private final HeaderPage headerPage = new HeaderPage();
    private final AllPeoplePage allPeoplePage = new AllPeoplePage();
    private final FriendsPage friendsPage = new FriendsPage();


    @Test
    void friendRequestTest(@User(selector = User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS) UserJson sentingUser,
                           @User(selector = User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS) UserJson recivingUser) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(sentingUser);

        headerPage.clickAllPeopleButton();
        allPeoplePage.addToFriends(recivingUser);
        headerPage.popupTextShouldBe("Invitation is sent");
    }

    @Test
    void friendRequestTest1(@User(selector = User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS) UserJson sentingUser,
                           @User(selector = User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS) UserJson recivingUser) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(sentingUser);

        headerPage.clickAllPeopleButton();
        allPeoplePage.addToFriends(recivingUser);
        headerPage.popupTextShouldBe("Invitation is sent");
    }

    @Test
    void confirmationFriendshipTest(@User(selector = User.Selector.INVITATION_SEND) UserJson sentingUser,
                           @User(selector = User.Selector.INVITATION_RECEIVED) UserJson recivingUser) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(recivingUser);

        headerPage.clickFriendsButton();
        friendsPage.confirmationFriendship(sentingUser);
        headerPage.popupTextShouldBe("Invitation is accepted").logout();

        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(recivingUser);
        headerPage.clickFriendsButton();
        friendsPage.verifyFriendship(sentingUser);
    }

    @Test
    void removeAllFriends(@User(selector = User.Selector.WITH_FRIENDS) UserJson user) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(user);

        headerPage.clickFriendsButton();
        friendsPage.removeAllFriends();
    }

    @Test
    void removeAllFriends1(@User(selector = User.Selector.WITH_FRIENDS) UserJson user) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage.loginBtnClick();
        loginPage.fillLoginPage(user);

        headerPage.clickFriendsButton();
        friendsPage.removeAllFriends();
    }
}