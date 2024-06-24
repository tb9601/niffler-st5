package guru.qa.niffler.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ReactCalendar extends guru.qa.niffler.page.BaseComponent<ReactCalendar> {

    public ReactCalendar(SelenideElement self) {
        super(self);
    }

    public ReactCalendar() {
        super($(".react-datepicker"));
    }

    private final By currentDate = By.cssSelector(".react-datepicker__current-month");
    private final By nextMonthButton = By.cssSelector(".react-datepicker__navigation--next");
    private final By previousMonthButton = By.cssSelector(".react-datepicker__navigation--previous");
    private final By dayOfMonth = By.cssSelector("[class*=react-datepicker__day--]");

    public ReactCalendar selectDate(LocalDate date) {
        int expectedYear = date.getYear();
        int expectedMonth = date.getMonth().getValue();
        while (getCurrentYear().getValue() > expectedYear) {
            int currentYear = getCurrentYear().getValue();
            IntStream.range(0, 12).forEach(i -> self.$(previousMonthButton).click());
            Assertions.assertEquals(currentYear - 1, getCurrentYear().getValue());
        }
        while (getCurrentYear().getValue() < expectedYear) {
            int currentYear = getCurrentYear().getValue();
            IntStream.range(0, 12).forEach(i -> self.$(nextMonthButton).click());
            Assertions.assertEquals(currentYear + 1, getCurrentYear().getValue());
        }

        while (getCurrentMonth().getValue() > expectedMonth) {
            int currentMonth = getCurrentMonth().getValue();
            self.$(previousMonthButton).click();
            Assertions.assertEquals(currentMonth - 1, getCurrentMonth().getValue());
        }

        while (getCurrentMonth().getValue() < expectedMonth) {
            int currentMonth = getCurrentMonth().getValue();
            self.$(nextMonthButton).click();
            Assertions.assertEquals(currentMonth + 1, getCurrentMonth().getValue());
        }

        $$(dayOfMonth).filter(Condition.innerText(String.valueOf(date.getDayOfMonth())))
                .asDynamicIterable()
                .stream().filter(element -> element.getAttribute("aria-label").contains(StringUtils.capitalize(date.getMonth().name().toLowerCase()))).findFirst().get()
                .click();


        return this;
    }

    public Year getCurrentYear() {
        return Year.parse(self.$(currentDate).getText().split(" ")[1]);
    }

    public Month getCurrentMonth() {
        return Month.valueOf(self.$(currentDate).getText().split(" ")[0].toUpperCase());
    }
}
