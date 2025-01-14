package tech.guilhermekaua.otisoftwaredesafiocep.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CepValidator implements ConstraintValidator<Cep, String> {
    public static final Pattern CEP_PATTERN = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");

    @Override
    public boolean isValid(String cep, final ConstraintValidatorContext context) {
        if (cep == null) {
            return true;
        }

        if (cep.isEmpty()) {
            return false;
        }

        final Matcher matcher = CEP_PATTERN.matcher(cep);
        return matcher.find();
    }

}