package com.project.creditmangement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.creditmangement.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="application_results")
public class Result extends Cache {


    @Id
    @Column(name = "national_no")
    @Size(min=11,max=11,message = "National Identity Number must be 11 characters")
    private String nationalNo;

    @Column(name="application_result")
    private String applicationResult;

    @Column(name="credit_limit")
    private Integer limit;


    @Nullable
    @Override
    public String getKey() {
        return String.format("%s",this.nationalNo);
    }
}
