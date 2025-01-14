package tech.guilhermekaua.otisoftwaredesafiocep;

import org.junit.jupiter.api.Test;
import tech.guilhermekaua.otisoftwaredesafiocep.utils.Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class UtilsTests {
    @Test
    void unformatCep_shouldUnformatCep() {
        String cep = "45.400-000";
        String unformattedCep = Utils.unformatCep(cep);

        assertEquals("45400000", unformattedCep);

        cep = "45400-000";
        unformattedCep = Utils.unformatCep(cep);

        assertEquals("45400000", unformattedCep);
    }

    @Test
    void unformatCep_shouldThrowErrorIfInvalidCep() {
        String cep = "45.400-000999";
        assertThrowsExactly(IllegalArgumentException.class, () -> Utils.unformatCep(cep));
    }

    @Test
    void formatCep_shouldFormatCep() {
        String cep = "45400000";
        String formattedCep = Utils.formatCep(cep);

        assertEquals("45.400-000", formattedCep);

        cep = "45400-000";
        formattedCep = Utils.formatCep(cep);

        assertEquals("45.400-000", formattedCep);
    }

    @Test
    void formatCep_shouldThrowErrorIfInvalidCep() {
        String cep = "45.400-000999";
        assertThrowsExactly(IllegalArgumentException.class, () -> Utils.formatCep(cep));
    }
}
