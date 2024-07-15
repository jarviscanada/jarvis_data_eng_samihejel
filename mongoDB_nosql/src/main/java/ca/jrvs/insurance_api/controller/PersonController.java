package ca.jrvs.insurance_api.controller;

import ca.jrvs.insurance_api.model.Person;
import ca.jrvs.insurance_api.service.PersonService;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance_api")
public class PersonController {

	@Autowired
	private final PersonService service;
	
	public PersonController(PersonService service) {
		this.service = service;
	}
	
	@PostMapping("person")
	@ResponseStatus(HttpStatus.CREATED)
	public void postPerson(@RequestBody Person person) {
		service.savePerson(person);
	}
	
	@GetMapping("people")
	public List<Person> getPeople() {
		return service.findAllPeople();
	}
	
	@GetMapping("person/{id}")
	public ResponseEntity<Person> getPerson(@PathVariable ObjectId id) {
		Optional<Person> o = service.findPersonById(id);
		if (o.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(o.get());
	}
	
	@DeleteMapping("person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable ObjectId id) {
        service.deletePersonById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("people")
    public ResponseEntity<Void> deleteAllPeople() {
        service.deleteAllPeople();
        return ResponseEntity.ok().build();
    }

    @PutMapping("person/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable ObjectId id, @RequestBody Person person) {
        person.setId(id);
        service.updatePerson(person);
        return ResponseEntity.ok().build();
    }

    @GetMapping("count")
    public ResponseEntity<Long> countPeople() {
        long count = service.countPeople();
        return ResponseEntity.ok(count);
    }

    @GetMapping("averageAge")
    public ResponseEntity<Double> getAverageAge() {
        double averageAge = service.getAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("maxCars")
    public ResponseEntity<Integer> getMaxCars() {
        int maxCars = service.getMaxNumberOfCars();
        return ResponseEntity.ok(maxCars);
    }

    @PostMapping("people")
    @ResponseStatus(HttpStatus.CREATED)
    public void postPeople(@RequestBody List<Person> people) {
        service.savePeople(people);
    }

    @GetMapping("people/{ids}")
    public ResponseEntity<List<Person>> getPeopleByIds(@PathVariable List<ObjectId> ids) {
        List<Person> people = service.findPeopleByIds(ids);
        return ResponseEntity.ok(people);
    }

    @DeleteMapping("people/{ids}")
    public ResponseEntity<Void> deletePeople(@PathVariable List<ObjectId> ids) {
        service.deletePeopleByIds(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("people")
    public ResponseEntity<Void> updatePeople(@RequestBody List<Person> people) {
        service.updatePeople(people);
        return ResponseEntity.ok().build();
    }
	
}