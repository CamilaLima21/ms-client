package br.com.fiap.msclients.domain.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public boolean validateCpf(String cpf) {
        // Verifica se o CPF possui 11 dígitos e se é composto apenas por números
        if (cpf == null || !Pattern.matches("\\d{11}", cpf)) {
            return false;
        }

        // Calcula os dígitos verificadores
        int[] multiplyFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] multiplySecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        if (calculateCheckDigit(cpf.substring(0, 9), multiplyFirstDigit) != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }
        if (calculateCheckDigit(cpf.substring(0, 10), multiplySecondDigit) != Character.getNumericValue(cpf.charAt(10))) {
            return false;
        }

        return true;
    }

    
	private int calculateCheckDigit(String cpfParcial, int[] multipliers) {
        int total = 0;
        for (int i = 0; i < cpfParcial.length(); i++) {
            total += Character.getNumericValue(cpfParcial.charAt(i)) * multipliers[i];
        }
        int rest = total % 11;
        return (rest < 2) ? 0 : (11 - rest);
    }
}
