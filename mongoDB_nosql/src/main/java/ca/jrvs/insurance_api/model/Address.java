package ca.jrvs.insurance_api.model;
import java.util.Objects;

public class Address {
	
    private int streetNumber;
    private String streetName;
    private String city;
    private String country;
    private String postalCode;

    // Constructors

    public Address(int streetNumber, String streetName, String city, String country, String postalCode) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    // Getters and Setters

    public int getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // ToString, Hashcode, Equals

    @Override
    public String toString() {
        return "{" +
            " streetNumber='" + getStreetNumber() + "'" +
            ", streetName='" + getStreetName() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            "}";
    }
    
}
