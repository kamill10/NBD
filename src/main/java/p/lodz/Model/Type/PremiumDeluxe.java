package p.lodz.Model.Type;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("premium_deluxe")
public class PremiumDeluxe extends ClientType{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_discount")
    private double clientDiscount = 0.2;

    @Column(name = "shorter_delivery_time")
    private int shorterDeliveryTime = 2;

    @Override
    public double getClientDiscount() {
        return clientDiscount;
    }

    @Override
    public int getShorterDeliveryTime() {
        return shorterDeliveryTime;
    }
}
