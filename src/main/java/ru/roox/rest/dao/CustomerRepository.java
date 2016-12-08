package ru.roox.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.roox.rest.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
