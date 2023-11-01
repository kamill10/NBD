package p.lodz.Model.Type;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("premium")
public class Premium extends ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_discount")
    private double clientDiscount = 0.1;

    @Column(name = "shorter_delivery_time")
    private int shorterDeliveryTime = 1;

    @Override
    public double getClientDiscount() {
        return clientDiscount;
    }

    @Override
    public int getShorterDeliveryTime() {
        return shorterDeliveryTime;
    }
}
