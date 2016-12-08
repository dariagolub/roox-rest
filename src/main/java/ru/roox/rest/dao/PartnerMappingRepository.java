package ru.roox.rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.roox.rest.model.PartnerMapping;

public interface PartnerMappingRepository extends JpaRepository<PartnerMapping, Long> {
}
