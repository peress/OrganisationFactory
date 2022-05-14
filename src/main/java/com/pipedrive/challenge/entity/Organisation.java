package com.pipedrive.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "organisation", schema = "public")
@AllArgsConstructor
@RequiredArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organisation_id_seq")
    @SequenceGenerator(name = "organisation_id_seq", sequenceName = "organisation_id_seq", allocationSize = 1)
    private Long id;

    private String name;
}
