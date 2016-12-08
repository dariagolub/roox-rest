package ru.roox.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.roox.rest.exceptions.PartnerMappingNotFoundException;
import ru.roox.rest.exceptions.CustomerNotFoundException;
import ru.roox.rest.model.PartnerMapping;
import ru.roox.rest.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/customerapi")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     *
     * @param customerId
     * @return Customer details by Id if customer exists. Status code 404 if customer is not found
     */
    @GetMapping(value = "/{customer_id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getCustomer(@PathVariable(value = "customer_id") long customerId) {
        throwIfCustomerDoesNotExist(customerId);
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    /**
     *
     * @param customerId
     * @return List of partner mappings for customer by Id. Status code 404 if customer is not found
     * @
     */
    @GetMapping(value = "/{customer_id}/mappings", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getAllCustomerMappings(@PathVariable(value = "customer_id") long customerId) {
        throwIfCustomerDoesNotExist(customerId);
        return ResponseEntity.ok(customerService.getAllCustomerPartnerMappings(customerId));
    }

    /**
     *
     * @param customerId
     * @param partnerMappingId
     * @return Partner mapping details by Id for customer. Status code 404 if mapping is not found
     */
    @GetMapping(value = "/{customer_id}/mappings/{mapping_id}")
    public ResponseEntity getCustomerMappingById(@PathVariable(value = "customer_id") long customerId, @PathVariable(value = "mapping_id") long partnerMappingId) {
        throwIfCustomerDoesNotExist(customerId);
        PartnerMapping partnerMapping = customerService.getCustomerPartnerMappingById(customerId, partnerMappingId);
        if (partnerMapping == null) {
            throw new PartnerMappingNotFoundException(customerId, partnerMappingId);
        } else {
            return ResponseEntity.ok(partnerMapping);
        }
    }

    /**
     *
     * @param customerId
     * @param partnerMapping
     * @return Creates partner mapping for customer.
     */
    @PostMapping(value = "/{customer_id}/mappings", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomerMapping(@PathVariable(value = "customer_id") long customerId, @Valid @RequestBody PartnerMapping partnerMapping, HttpServletRequest request) throws URISyntaxException {
        throwIfCustomerDoesNotExist(customerId);
        PartnerMapping createdMapping = customerService.createCustomerPartnerMapping(customerId, partnerMapping);
        return ResponseEntity.created(new URI(request.getRequestURI() + "/" + createdMapping.getId())).body(createdMapping);
    }

    /**
     *
     * @param customerId
     * @param partnerMappingId
     * @param partnerMapping
     * @return Updates partner mapping by Id. Status code 404 if mapping is not found
     */
    @PutMapping(value = "/{customer_id}/mappings/{mapping_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCustomerMapping(@PathVariable(value = "customer_id") long customerId, @PathVariable(value = "mapping_id") long partnerMappingId, @Valid @RequestBody PartnerMapping partnerMapping) {
        throwIfCustomerDoesNotExist(customerId);
        PartnerMapping mapping = customerService.updateCustomerPartnerMapping(customerId, partnerMappingId, partnerMapping);
        if (mapping == null) {
            throw new PartnerMappingNotFoundException(customerId, partnerMappingId);
        } else {
            return ResponseEntity.ok(mapping);
        }
    }

    /**
     *
     * @param customerId
     * @param partnerMappingId
     * @return Delete partner mapping by Id. Status code 404 if mapping is not found
     */
    @DeleteMapping(value = "/{customer_id}/mappings/{mapping_id}")
    public ResponseEntity deleteCustomerMapping(@PathVariable(value = "customer_id") long customerId, @PathVariable(value = "mapping_id") long partnerMappingId) {
        throwIfCustomerDoesNotExist(customerId);
        boolean isDeleted = customerService.removeCustomerPartnerMapping(customerId, partnerMappingId);
        if (!isDeleted) {
            throw new PartnerMappingNotFoundException(customerId, partnerMappingId);
        }
        return ResponseEntity.accepted().build();
    }

    private void throwIfCustomerDoesNotExist(@PathVariable(value = "customer_id") long customerId) {
        if (!customerService.isCustomerExist(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
    }
}