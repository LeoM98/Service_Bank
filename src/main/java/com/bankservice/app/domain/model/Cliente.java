package com.bankservice.app.domain.model;

import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;
import lombok.*;

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
    @Temporal(TemporalType.DATE)
    private Date created;
    private Identification identification;
}
