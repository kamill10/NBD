package p.lodz.Model.Type;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PremiumDeluxe extends ClientType{


    private double clientDiscount = 0.2;


    private int shorterDeliveryTime = 2;

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
