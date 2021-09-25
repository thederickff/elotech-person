package br.com.elotech.person.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.elotech.person.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

  @Query(value = "select * from persons p where concat(p.date_of_birth, ' ', p.name, ' ', p.social_security_number) ilike %:term%", nativeQuery = true)
  Page<Person> findAll(String term, Pageable pageable);

}
