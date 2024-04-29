package guru.qa.niffler.jupiter.extension;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.RegistrationPage;
import guru.qa.niffler.pages.WelcomePage;
import guru.qa.niffler.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static guru.qa.niffler.jupiter.annotation.User.Selector.*;


public class UserQueueExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        final WelcomePage welcomePage = new WelcomePage();
        final RegistrationPage registrationPage = new RegistrationPage();

        UserJson tempUser;
        for (int i = 0; i < 3; i++) {
            tempUser = UserUtils.generateRandomUserJson();
            Selenide.open("http://127.0.0.1:3000/main");
            welcomePage.registerBtnClick();
            registrationPage.fillRegistrationPage(tempUser)
                    .clickSignInButton();
            Queue<UserJson> tempList = USERS.get(User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS);
            tempList.add(tempUser);
            USERS.put(User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS, new ConcurrentLinkedQueue<>(
                    tempList));
        }
        Selenide.closeWebDriver();
    }

    static {
        USERS.put(User.Selector.WITHOUT_INVITATIONS_AND_FRIENDS, new ConcurrentLinkedQueue<>(
                new ArrayList<>())
        );
        USERS.put(INVITATION_SEND, new ConcurrentLinkedQueue<>(
                new ArrayList<>())
        );
        USERS.put(User.Selector.INVITATION_RECEIVED, new ConcurrentLinkedQueue<>(
                new ArrayList<>())
        );
        USERS.put(WITH_FRIENDS, new ConcurrentLinkedQueue<>(
                new ArrayList<>())
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) {

        List<Method> beforeEachMethods = Arrays.stream(
                context.getRequiredTestClass().getDeclaredMethods())
                .filter(i -> i.isAnnotationPresent(BeforeEach.class))
                .collect(Collectors.toList());

        List<Method> methods = new ArrayList<>(Collections.singleton(context.getRequiredTestMethod()));
        methods.addAll(beforeEachMethods);

        // Общий список парамтеров, которые мы хотим обработать
        List<Parameter> parameters = methods.stream()
                .flatMap(m -> Arrays.stream(m.getParameters()))
                .filter(p -> p.isAnnotationPresent(User.class))
                .toList();

        // Объект, где хранятся тип пользователя и сам пользователь. Далее будет сохранен в store
        Map<User.Selector, ArrayList<UserJson>> users = new HashMap<>();

        // Обрабатываем каждый из полученных параметров
        for (Parameter parameter : parameters) {
            User.Selector selector = parameter.getAnnotation(User.class).selector();

            UserJson userForTest = null;

            // Получение очереди с необходимым типом пользователей
            Queue<UserJson> queue = USERS.get(selector);

            // "Умное ожидание" пользователя
            while (userForTest == null) {
                userForTest = queue.poll();
            }
            ArrayList<UserJson> tempList = new ArrayList();
            if (users.keySet().contains(selector)) {
                tempList.addAll(users.get(selector));
            }
            tempList.add(userForTest);

            // Добавляем полученного из очереди пользователя в наш объект
            users.put(selector, tempList);
        }

        // Сохраняем данные о пользователях в store
        context.getStore(NAMESPACE).put(context.getUniqueId(), users);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        // Получаем мапу из хранилища
        Map<User.Selector, ArrayList<UserJson>> users = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);
        if (!context.getExecutionException().isPresent()) { // только если тест прошел успешно
            for (Map.Entry<User.Selector, ArrayList<UserJson>> user : users.entrySet()) {
                //в зависимости от теста добавить в соответствубщие очереди
                if (context.getTestMethod().get().getName().contains("friendRequestTest")) {
                    USERS.get(INVITATION_SEND).add(user.getValue().get(0));
                    USERS.get(INVITATION_RECEIVED).add(user.getValue().get(1));
                } else if (context.getTestMethod().get().getName().equals("confirmationFriendshipTest")) {
                    USERS.get(WITH_FRIENDS).addAll(user.getValue());
                } else if (context.getTestMethod().get().getName().contains("removeAllFriends")) {
                    USERS.get(WITHOUT_INVITATIONS_AND_FRIENDS).addAll(user.getValue());
                }
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        String testName = parameterContext.getParameter().getDeclaringExecutable().getName();

        Set<User.Selector> testParametersTypes = Arrays.stream(extensionContext.getRequiredTestClass().getDeclaredMethods())
                .filter(method -> method.getName().equals(testName))
                .flatMap(m -> Arrays.stream(m.getParameters()))
                .map(parameter -> parameter.getAnnotation(User.class).selector())
                .collect(Collectors.toSet());

        User.Selector selector = parameterContext.getParameter().getAnnotation(User.class).selector();

        if (testParametersTypes.size() == 1) { //если все параметры одного типа селектора
            return ((ArrayList) extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(selector)).get(parameterContext.getIndex());
        } else {
            return ((ArrayList) extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(selector)).get(0);
        }
    }
}
