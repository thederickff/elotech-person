package br.com.elotech.person.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.elotech.person.models.Person;
import br.com.elotech.person.repositories.PersonRepository;

@Service
public class PersonService {
  
  private final PersonRepository repository;

  @Autowired
  public PersonService(PersonRepository repository)
  {
    this.repository = repository;
  }

  public Page<Person> findAll(String term, Pageable pageable)
  {
    if (term != null) {
      return repository.findAll(term, pageable);
    }

    return repository.findAll(pageable);
  }

}
