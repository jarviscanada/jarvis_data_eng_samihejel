package ca.jrvs.insurance_api.service;

import ca.jrvs.insurance_api.model.Person;
import ca.jrvs.insurance_api.repository.PersonRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repo;

    @Autowired
    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    // Save a single person
    public Person savePerson(Person person) {
        return repo.save(person);
    }

    // Save a list of people
    public List<Person> savePeople(List<Person> people) {
        return repo.saveAll(people);
    }

    // Find a person by id
    public Optional<Person> findPersonById(ObjectId id) {
        return repo.findById(id);
    }

    // Find a list of people by ids
    public List<Person> findPeopleByIds(List<ObjectId> ids) {
        return repo.findAllByIdIn(ids);
    }

    // Get all data on everyone
    public List<Person> findAllPeople() {
        return repo.findAll();
    }

    // Delete a person by id
    public void deletePersonById(ObjectId id) {
        repo.deleteById(id);
    }

    // Delete a list of people by ids
    public void deletePeopleByIds(List<ObjectId> ids) {
        repo.deleteAllByIdIn(ids);
    }

    // Delete everyone
    public void deleteAllPeople() {
        repo.deleteAll();
    }

    // Update a single person
    public Person updatePerson(Person person) {
        return repo.save(person);
    }

    // Update a list of people
    public List<Person> updatePeople(List<Person> people) {
        return repo.saveAll(people);
    }

    // Aggregations
    public long countPeople() {
        return repo.count();
    }

    public double getAverageAge() {
        return repo.averageAge();
    }

    public int getMaxNumberOfCars() {
        return repo.maxNumberOfCars();
    }
}