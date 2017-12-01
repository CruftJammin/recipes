package com.creating.bugs.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by steve on 15/11/17.
 */
@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
