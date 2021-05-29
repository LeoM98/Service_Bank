package com.bankservice.app.domain.dto;

import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO implements Serializable {

    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String address;
    private AccountType accountType;
    private LocalDate created;
    private Identification identification;

}
