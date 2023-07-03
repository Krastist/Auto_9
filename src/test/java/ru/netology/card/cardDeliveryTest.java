package ru.netology.card;

import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class cardDeliveryTest {

    @BeforeEach
    public void startJar() {
        open("http://localhost:9999/");
    }
}
