package com.project.creditmangement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="application_results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "national_no")
    @Size(min=11,max=11,message = "National Identity Number must be 11 characters")
    private String nationalNo;

    @Column(name="application_result")
    private String applicationResult;

    @Column(name="credit_limit")
    private Integer limit;



}
