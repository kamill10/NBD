package p.lodz.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class Address {
    private String city;
    private String street;
    private String number;

    public String getAddressInfo(){
        return city + " " + street + " " + number;
    }

    public Address(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }
}
