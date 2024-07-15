package ca.jrvs.insurance_api.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("people")
public class Person {
	
    private ObjectId id;
    private String firstName;
    private String lastName;
    private int age;
    private Address addressEntity;
    private Date createdAt = new Date();
    private Boolean insurance;
    private List<Car> carEntities;
    
    // Constructors

    public Person(ObjectId id, String firstName, String lastName, int age, Address addressEntity, Date createdAt, Boolean insurance, List<Car> carEntities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.addressEntity = addressEntity;
        this.createdAt = createdAt;
        this.insurance = insurance;
        this.carEntities = carEntities;
    }

    // Getters and Setters

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddressEntity() {
        return this.addressEntity;
    }

    public void setAddressEntity(Address addressEntity) {
        this.addressEntity = addressEntity;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isInsurance() {
        return this.insurance;
    }

    public Boolean getInsurance() {
        return this.insurance;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public List<Car> getCarEntities() {
        return this.carEntities;
    }

    public void setCarEntities(List<Car> carEntities) {
        this.carEntities = carEntities;
    }

    // ToString, Hashcode, Equals

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", age='" + getAge() + "'" +
            ", addressEntity='" + getAddressEntity() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", insurance='" + isInsurance() + "'" +
            ", carEntities='" + getCarEntities() + "'" +
            "}";
    }


}