package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.jupiter.extension.SpendExtension;
import guru.qa.niffler.model.CurrencyValues;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ExtendWith(SpendExtension.class)
public @interface GenerateSpend {
    String category();

    CurrencyValues currency();

    double amount();

    String description();

    String username();
}
