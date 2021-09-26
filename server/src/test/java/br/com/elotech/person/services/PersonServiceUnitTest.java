package br.com.elotech.person.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.elotech.person.models.Person;
import br.com.elotech.person.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServiceUnitTest {
  
  @InjectMocks
  PersonService service;
  @Mock
  PersonRepository repository;

  @Test
  void testFindAll()
  {
    Person person1 = new Person(1l, "Bob Odenkirk", "81644673088", LocalDate.of(1998, 1, 23), null);
    Person person2 = new Person(2l, "Carl Krank", "50023712058", LocalDate.of(1992, 4, 1), null);
  
    when(
      service.findAll("446", null)
    ).thenReturn(
      new PageImpl<>(Arrays.asList(person1))
    );

    when(
      service.findAll("1992-04-01", null)
    ).thenReturn(
      new PageImpl<>(Arrays.asList(person2))
    );

    Page<Person> response = service.findAll("446", null);
    assertThat(response.getContent().size()).isEqualTo(1);
    assertThat(response.getContent().get(0).getName()).isEqualTo(person1.getName());

    response = service.findAll("1992-04-01", null);
    
    assertThat(response.getContent().size()).isEqualTo(1);
    assertThat(response.getContent().get(0).getName()).isEqualTo(person2.getName());
  }

}
