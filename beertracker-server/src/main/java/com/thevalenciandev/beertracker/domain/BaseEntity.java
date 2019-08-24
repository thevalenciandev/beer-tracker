package com.thevalenciandev.beertracker.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
}
