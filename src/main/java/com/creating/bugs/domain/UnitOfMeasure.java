package com.creating.bugs.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by steve on 15/11/17.
 */
@Data
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

}
