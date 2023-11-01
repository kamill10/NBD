package p.lodz.Model.Type;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "client_type")
public abstract class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "client_discount")
    private double clientDiscount;

    @Column(name = "shorter_delivery_time")
    private int shorterDeliveryTime;

    public abstract double getClientDiscount();
    public abstract int getShorterDeliveryTime();

    public Long getId() {
        return id;
    }
}
