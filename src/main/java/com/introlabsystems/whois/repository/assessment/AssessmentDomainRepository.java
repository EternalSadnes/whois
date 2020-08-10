package com.introlabsystems.whois.repository.assessment;

import com.introlabsystems.whois.model.assessment.AssessmentDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentDomainRepository extends JpaRepository<AssessmentDomain, Long> {

    @Query(value = "select domain from AssessmentDomain where domain like '%.com' or domain like '%.net' or domain like '%.edu'")
    List<String> findAllByDomainEnding();
}
