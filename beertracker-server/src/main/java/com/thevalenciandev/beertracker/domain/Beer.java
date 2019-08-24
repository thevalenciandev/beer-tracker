package com.thevalenciandev.beertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor // Hibernate/JPA require empty constructor
@AllArgsConstructor
public class Beer extends BaseEntity {

    private String name;
    private String type; // TODO: should be an enum (LARGER, ALE, PALE ALE...) and should go into a separate table
    private double ABV;

}
