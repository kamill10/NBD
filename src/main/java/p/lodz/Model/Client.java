package p.lodz.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import p.lodz.Model.Type.ClientType;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    @Size(min = 3, max = 20)
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    @Size(min = 3, max = 20)
    private String lastName;

    @Embedded
    Address address;

    @ManyToOne
    @NotNull
    ClientType clientType;

    private boolean archived = false;

    @Min(value = 0, message = "Money spent cannot be less than 0")
    private double moneySpent = 0;

    public Client(String firstName, String lastName, Address address, ClientType clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.clientType = clientType;
    }

    public double getClientDiscount(){
        return clientType.getClientDiscount();
    }

    public int getClientShorterDeliveryTime(){
        return clientType.getShorterDeliveryTime();
    }

    public void addMoneySpent(double value){
        moneySpent += value;
    }
}
