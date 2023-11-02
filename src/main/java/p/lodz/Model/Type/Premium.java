package p.lodz.Model.Type;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Premium extends ClientType {


    private double clientDiscount = 0.1;

    private int shorterDeliveryTime = 1;

//    @Override
//    public double getClientDiscount() {
//        return clientDiscount;
//    }
//
//    @Override
//    public int getShorterDeliveryTime() {
//        return shorterDeliveryTime;
//    }
}
