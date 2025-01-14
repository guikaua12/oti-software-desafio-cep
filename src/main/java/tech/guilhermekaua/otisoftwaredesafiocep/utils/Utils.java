package tech.guilhermekaua.otisoftwaredesafiocep.utils;

import static tech.guilhermekaua.otisoftwaredesafiocep.validation.CepValidator.CEP_PATTERN;

public final class Utils {
    public static String unformatCep(String cep) {
        if (!CEP_PATTERN.matcher(cep).matches()) {
            throw new IllegalArgumentException("Invalid CEP: " + cep);
        }

        return cep.replaceAll("\\D", "");
    }

    public static String formatCep(String cep) {
        final String cleanCep = unformatCep(cep);

        return cleanCep.replaceAll("([0-9]{2})([0-9]{3})([0-9]{3})", "$1.$2-$3");
    }
}
