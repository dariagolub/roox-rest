package ru.roox.rest;

import org.junit.Test;
import ru.roox.rest.model.PartnerMapping;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by DGolub on 08.12.2016.
 */
public class ValidationTest {

    @Test
    public void partnerMappingValidatorTestNotValidIds() {
        PartnerMapping mapping = new PartnerMapping(0, 0, "we", "we", "we", "http://test.com/test.jpg");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PartnerMapping>> violations = validator.validate(mapping);
        assertEquals(2, violations.size());
        assertEquals(0L, violations.iterator().next().getInvalidValue());
    }

    @Test
    public void partnerMappingValidatorTestNotValidNames() {
        PartnerMapping mapping = new PartnerMapping(1, 1, null, null, null, "http://test.com/test.jpg");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PartnerMapping>> violations = validator.validate(mapping);
        assertEquals(3, violations.size());
    }

    @Test
    public void partnerMappingValidatorTestNotValidAvatar() {
        PartnerMapping mapping = new PartnerMapping(1, 1, "we", "we", "we", "http://not_valid");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PartnerMapping>> violations = validator.validate(mapping);
        assertEquals(1, violations.size());
        assertEquals("http://not_valid", violations.iterator().next().getInvalidValue());
    }
}
