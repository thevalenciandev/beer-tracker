package com.thevalenciandev.beertracker.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@Entity
public class Beer {

    @Id
    @GeneratedValue
    private final Long id;
    private final String name;
    private final String type; // TODO: should be an enum (LARGER, ALE, PALE ALE...)
    private final double ABV;

}
