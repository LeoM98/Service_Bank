package com.bankservice.app.domain.enums.converter;

import com.bankservice.app.domain.enums.Identification;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class IdentificationConverter implements AttributeConverter<Identification, String> {

    @Override
    public String convertToDatabaseColumn(Identification id) {
        switch (id){
            case ACTA_NACIMIENTO:
                return "AN";
            case CARNET:
                return "C";
            case CÉDULA:
                return "CC";
            case TARJETA_IDENTIDAD:
                return "TI";
            default:
                throw new IllegalArgumentException("Unknown "+ id);
        }
    }

    @Override
    public Identification convertToEntityAttribute(String dbData) {

        switch (dbData){
            case "AN":
                return Identification.ACTA_NACIMIENTO;
            case "C":
                return Identification.CARNET;
            case "CC":
                return Identification.CÉDULA;
            case "TI":
                return Identification.TARJETA_IDENTIDAD;
            default:
                throw new IllegalArgumentException("Unknown "+ dbData.toUpperCase());
        }    }
}
