package com.thevalenciandev.beertracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brewery {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

}
