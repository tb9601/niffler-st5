package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.UserQueueExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(UserQueueExtension.class)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface User {
    Selector selector();

    enum Selector {
        WITHOUT_INVITATIONS_AND_FRIENDS, INVITATION_SEND, INVITATION_RECEIVED, WITH_FRIENDS
    }
}
