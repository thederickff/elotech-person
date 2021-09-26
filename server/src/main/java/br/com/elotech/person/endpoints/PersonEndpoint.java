package br.com.elotech.person.endpoints;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.elotech.person.models.Person;
import br.com.elotech.person.services.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonEndpoint {

  private final PersonService service;

  @Autowired
  public PersonEndpoint(PersonService service)
  {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<Page<Person>> findAll(@Param("term") String term, Pageable pageable)
  {
    return ResponseEntity.ok(service.findAll(term, pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<Optional<Person>> findById(@PathVariable("id") Long id)
  {
    return ResponseEntity.ok(service.findById(id));
  }
}