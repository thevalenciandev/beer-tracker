package com.thevalenciandev.beertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type; // TODO: should be an enum (LARGER, ALE, PALE ALE...) and should go into a separate table
    private double ABV;

}
