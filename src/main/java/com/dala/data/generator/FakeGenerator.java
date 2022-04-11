package com.dala.data.generator;

import com.dala.data.person.Person;
import com.dala.utils.MathUtils;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class FakeGenerator {
    FakeValuesService fakeValuesService = new FakeValuesService(new Locale("de-CH.yml"), new RandomService());
    Faker faker = new Faker(new Locale("de-CH"));
    Random random = new Random();

    public ArrayList<Person> generateRandomPersons(int count) {
        ArrayList<Person> persons = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            persons.add(new Person(faker.name().firstName(), faker.name().lastName(), MathUtils.getInstance().round(randomBetween(1000.0, 10000.0), 2)));
        }

        return persons;
    }

    public double randomBetween(double min, double max) {
        return random.nextDouble(max - min + 1) + min;
    }

    private static FakeGenerator instance;

    public static FakeGenerator getInstance() {
        if (instance == null) {
            instance = new FakeGenerator();
        }
        return instance;
    }

    private FakeGenerator() {
    }
}
