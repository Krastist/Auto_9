package ru.netology.card;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {
    private String getDate(int addDay, String pattern) {
        LocalDate date = LocalDate.now();
        LocalDate futureDate = date.plusDays(addDay);
        String formatDate = futureDate.format(DateTimeFormatter.ofPattern(pattern));
        return formatDate;
    }

    @BeforeEach
    public void startJar() {

        open("http://localhost:9999/");
    }

    @Test
    void validTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(13));
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + getDate(3, "dd.MM.yyyy")));
    }

    @Test
    void noCityTest() {
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void cityNotValidTest() {
        $("[data-test-id=city] input").setValue("Moskva");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void noDataTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=date] span.input_invalid span.input__sub").shouldHave(exactText("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void dataNotValidTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(0, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=date] span.input_invalid span.input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void noNameTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void nameNotValidTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Ivan");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void noPhoneTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void phoneNotValidTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+7123456789");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void noAgreementTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("button.button").click();
        $("[data-test-id=agreement].input_invalid");
    }

    @Test
    void validMenuCityTest() {
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item").find(exactText("Москва")).click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(13));
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + getDate(3, "dd.MM.yyyy")));
    }

    @Test
    void notValidMenuCityTest() {
        $("[data-test-id=city] input").setValue("Mo");
//        $(".menu").shouldBe(invisible);
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(getDate(3, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void validCalendarTest() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] button").click();
        if (!getDate(27,"MM").equals(getDate(3, "MM"))) {
            $("[data-step=1]").click();
        }
        $$(".calendar__day").find(exactText(getDate(7, "d"))).click();
        $("[data-test-id=name] input").setValue("Иван Иванович");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(13));
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + getDate(3, "dd.MM.yyyy")));
    }
}
