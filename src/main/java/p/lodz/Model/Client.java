package p.lodz.Model;


import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import p.lodz.Model.Type.ClientType;

import java.util.UUID;


@Entity
@CqlName("clients")
public class Client {


    @PartitionKey
    @CqlName("id")
    private UUID id;


    @ClusteringColumn
    @CqlName("last_name")
    private String lastName;

    @CqlName("first_name")
    private String firstName;


    @CqlName("money_spent")
    private double moneySpent = 0;

    @CqlName("client_type")
    private String typ;

    @CqlName("address_client")
    private String address;



    public Client(String firstName, String lastName, String typ,Address address) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.typ = typ;
        this.address = address.toString();
    }
    public Client(){};

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addMoneySpent(double value){
        moneySpent += value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }



    public double getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", moneySpent=" + moneySpent +
                ", typ='" + typ + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }


}
