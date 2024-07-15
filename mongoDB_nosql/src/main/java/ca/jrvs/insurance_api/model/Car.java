package ca.jrvs.insurance_api.model;
import java.util.Objects;

public class Car {

	private String make;
	private String model;
	private int maxSpeed;
	
    // Constructors

    public Car(String make, String model, int maxSpeed) {
        this.make = make;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    // Getters and Setters


    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    

    // ToString, Hashcode, Equals

    @Override
    public String toString() {
        return "{" +
            " make='" + getMake() + "'" +
            ", model='" + getModel() + "'" +
            ", maxSpeed='" + getMaxSpeed() + "'" +
            "}";
    }
    
}
