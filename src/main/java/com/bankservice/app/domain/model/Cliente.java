package com.bankservice.app.domain.model;

import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "LastName cannot be blank")
    private String lastname;
    private String phone;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    private AccountType accountType;
    @JsonIgnore
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date created;
    private Identification identification;
    @NotBlank(message = "identification number cannot be blank")
    @Column(name = "identification_number")
    private String numeroIdentificacion;
    @Column(name = "cantidad_dinero")
    @Min(value = 100000, message = "Money cannot be smaller than 100000")
    private Long montoDinero;
}
