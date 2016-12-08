package ru.roox.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.roox.rest.dao.CustomerRepository;
import ru.roox.rest.dao.PartnerMappingRepository;
import ru.roox.rest.model.Customer;
import ru.roox.rest.model.PartnerMapping;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    PartnerMappingRepository partnerMappingRepository;

    public Customer getCustomer(long customerId) {
        return customerRepository.findOne(customerId);
    }

    public List<PartnerMapping> getAllCustomerPartnerMappings(long customerId) {
        return customerRepository.getOne(customerId).getMappings();
    }

    public PartnerMapping getCustomerPartnerMappingById(long customerId, long mappingId) {
        return customerRepository.getOne(customerId).getMappings().stream().filter(mapping -> mapping.getId() == mappingId).findAny().orElse(null);
    }

    public PartnerMapping createCustomerPartnerMapping(long customerId, PartnerMapping partnerMapping) {
        Customer customer = customerRepository.getOne(customerId);
        partnerMapping.setCustomer(customer);
        return partnerMappingRepository.saveAndFlush(partnerMapping);
    }

    public PartnerMapping updateCustomerPartnerMapping(long customerId, long mappingId, PartnerMapping partnerMapping) {
        Customer customer = customerRepository.getOne(customerId);
        Optional<PartnerMapping> pMapping = customer.getMappings().stream().filter(mapping -> mapping.getId() == mappingId).findAny();
        if (!pMapping.isPresent()) {
            return null;
        } else {
            partnerMapping.setId(mappingId);
            partnerMapping.setCustomer(customer);
            return partnerMappingRepository.saveAndFlush(partnerMapping);
        }
    }

    public boolean removeCustomerPartnerMapping(long customerId, long mappingId) {
        Optional<PartnerMapping> pMapping = customerRepository.getOne(customerId).getMappings().stream().filter(mapping -> mapping.getId() == mappingId).findAny();
        if (!pMapping.isPresent()) {
            return false;
        } else {
            partnerMappingRepository.delete(mappingId);
            return true;
        }
    }

    @Override
    public boolean isCustomerExist(long customerId) {
        return customerRepository.exists(customerId);
    }
}
