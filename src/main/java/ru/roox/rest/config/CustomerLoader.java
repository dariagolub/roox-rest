package ru.roox.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.roox.rest.model.CustomerStatus;
import ru.roox.rest.dao.CustomerRepository;
import ru.roox.rest.model.Customer;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomerLoader {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void initCustomerData() {
        Customer customerIvanov = new Customer("Ivanov", "Ivan", "Ivanovich", 1200, CustomerStatus.ACTIVE, "ivanov", "pass");
        Customer customerPetrov = new Customer("Ivanov", "Ivan", "Ivanovich", -100, CustomerStatus.BLOCKED, "petrov", "pass");
        Customer customerOlegov = new Customer("Olegov", "Oleg", "Olegovich", 0, CustomerStatus.ACTIVE, "olegov", "pass");
        List<Customer> customers = Arrays.asList(customerIvanov, customerPetrov, customerOlegov);
        customerRepository.save(customers);
        customerRepository.flush();
    }
}
