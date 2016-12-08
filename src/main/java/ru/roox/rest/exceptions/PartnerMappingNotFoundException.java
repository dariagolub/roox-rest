package ru.roox.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Partner mapping does not exist")
public class PartnerMappingNotFoundException extends RuntimeException {
    public PartnerMappingNotFoundException(long customerId, long mappingId) {
        super("Partner mapping " + mappingId + " does not exist for customer: " + customerId);
    }
}
