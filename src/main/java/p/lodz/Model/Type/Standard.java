package p.lodz.Model.Type;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Standard extends ClientType{


    private double clientDiscount = 0;

    private int shorterDeliveryTime = 0;

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
