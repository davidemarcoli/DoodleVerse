package com.dala.data.person;

import com.dala.data.building.house.House;
import com.dala.data.generator.FakeGenerator;
import com.dala.utils.MathUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private double money;

    @ManyToOne
    private House house;

    public Person(String firstName, String lastName, double money) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.money = money;
    }

    @PrePersist
    private void setMoneyBeforePersist() {
        if (money == 0)
            money = MathUtils.getInstance().round(FakeGenerator.getInstance().randomBetween(10000, 100000), 2);
    }
}
