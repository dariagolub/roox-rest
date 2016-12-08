package ru.roox.rest.service;

import ru.roox.rest.model.PartnerMapping;
import ru.roox.rest.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(long id);

    List<PartnerMapping> getAllCustomerPartnerMappings(long customerId);

    PartnerMapping getCustomerPartnerMappingById(long customerId, long mappingId);

    PartnerMapping createCustomerPartnerMapping(long customerId, PartnerMapping partnerMapping);

    PartnerMapping updateCustomerPartnerMapping(long customerId, long mappingId, PartnerMapping partnerMapping);

    boolean removeCustomerPartnerMapping(long customerId, long mappingId);

    boolean isCustomerExist(long customerId);
}
