package com.project.creditmangement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    //@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @Column(name = "national_no")
    //@JsonIgnoreProperties({"name","surname","monthlyIncome","phone","id"})
    private String nationalNo;

    @Column(name="application_result")
    private String applicationResult;

    @Column(name="credit_limit")
    private Integer limit;



}
