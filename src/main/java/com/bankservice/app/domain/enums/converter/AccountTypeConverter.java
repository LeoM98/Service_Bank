package com.bankservice.app.domain.enums.converter;

import com.bankservice.app.domain.enums.AccountType;
import com.bankservice.app.domain.enums.Identification;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, String> {

    @Override
    public String convertToDatabaseColumn(AccountType ac) {
        switch (ac){
            case AHORROS:
                return "AH";
            case CORRIENTE:
                return "C";
            default:
                throw new IllegalArgumentException("Unknown "+ ac);
        }
    }

    @Override
    public AccountType convertToEntityAttribute(String dbData) {
        switch (dbData){
            case "AH":
                return AccountType.AHORROS;
            case "C":
                return AccountType.CORRIENTE;
            default:
                throw new IllegalArgumentException("Unknown "+ dbData.toUpperCase());
        }
    }
}
