package ca.jrvs.insurance_api.repository;

import ca.jrvs.insurance_api.model.Person;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends MongoRepository<Person, ObjectId> {
    // Find a person by id
    Optional<Person> findById(ObjectId id);

    // Find a list of people by ids
    List<Person> findAllByIdIn(List<ObjectId> ids);

    // Delete a person by id
    void deleteById(String id);

    // Delete a list of people by ids
    void deleteAllByIdIn(List<ObjectId> ids);

    // Aggregations
    long count();

    double averageAge();

    int maxNumberOfCars();
}