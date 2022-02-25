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
@Table(name="credit_scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    //@JoinColumn(name = "national_no",referencedColumnName = "national_no")
    //@JsonIgnoreProperties({"name","surname","monthlyIncome","phone","id"})
    @Column(name="national_no")
    private String nationalNo;

    @Column(name="credit_score")
    private Integer creditScore;

}
