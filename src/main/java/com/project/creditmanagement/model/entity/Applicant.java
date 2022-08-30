package com.project.creditmanagement.model.entity;

import com.project.creditmanagement.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "applicants")
public class Applicant extends Cache implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surname;


    @NotNull
    @Size(min=11,max=11,message = "National Identity Number must be 11 characters")
    @Column(name="national_no")
    public String nationalNo;

    @NotNull
    private Integer monthlyIncome;

    @NotNull
    private String phone;

    @Nullable
    @Override
    public String getKey() {
        return String.format("%s",this.nationalNo);
    }
}