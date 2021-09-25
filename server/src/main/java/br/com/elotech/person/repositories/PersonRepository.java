package br.com.elotech.person.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elotech.person.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
