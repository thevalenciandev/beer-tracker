package com.thevalenciandev.beertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor // Hibernate/JPA require empty constructor
@AllArgsConstructor
@Builder
public class Beer {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String style; // TODO: should be an enum (LARGER, ALE, PALE ALE...) and should go into a separate table
    private double ABV;

    @ManyToOne(cascade = CascadeType.ALL)
    private Brewery brewery;

}
