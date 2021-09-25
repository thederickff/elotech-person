package br.com.elotech.person.endpoints;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.elotech.person.repositories.PersonRepository;

@RestController
@RequestMapping("/api/persons")
public class PersonEndpoint {

  private final PersonRepository repository;

  @Autowired
  public PersonEndpoint(PersonRepository repository)
  {
    this.repository = repository;
  }

  @GetMapping
  public ResponseEntity<?> findAll()
  {
    return ResponseEntity.ok(repository.findAll());
  }
}