package p.lodz;

import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Product;
import p.lodz.Model.Purchase;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;
import p.lodz.Model.Type.PremiumDeluxe;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {
    private final String testFirstName1 = "Kuba";
    private final String testLastName1 = "Jest";
    private final String testProductName1 = "Novel book 'Nad doliną' written by Adam Dostojnievky";
    private final int productID1 = 123;
    private double baseCost1 = 30.00;
    private int numberOfProducts1 = 30;
    private final String description1 = "Number of pages: 214, Type of cover: hard";
    private final String testCity = "Łodz";
    private final String testStreet = "Politechniki";
    private final String testNumber = "19";
    private final int testID = 20;
    private Address address =  new Address(testCity, testStreet, testNumber);
    private ClientType type   = new Premium();
    private Client client =  new Client(testFirstName1, testLastName1, new Address("aaa", "bbb", "ccc"),type);
    ;
    private Product product = new Product(testProductName1, baseCost1, numberOfProducts1, description1);
    ;
    private Purchase purchase = new Purchase(client, new ArrayList<Product>() {
        {
            add(product);
        }
    });
    private Purchase purchase1 = new Purchase(client, new ArrayList<Product>() {
        {
            add(product);
        }
    });



    @Test
    public void finalCostTest() {
        double finalCost = product.getBaseCost() - product.getBaseCost() * product.getDiscount() - product.getBaseCost() * client.getClientDiscount();
        assertEquals(finalCost, purchase.getFinalCost(), 0.01);
        purchase.getProducts().get(0).setDiscount(0.5);
        assertEquals(finalCost, purchase.getFinalCost(), 0.01);
        purchase.getProducts().get(0).setBaseCost(2000000000.00);
        assertEquals(finalCost, purchase.getFinalCost(), 0.01);
    }

    @Test
    public void isDeliveryTimeFixed() {
        LocalDate deliveryTime = purchase.getPurchaseDate().plusDays(3 - client.getClientShorterDeliveryTime());
        assertEquals(deliveryTime, purchase.getDeliveryDate());
        ClientType premiumDeluxe = new PremiumDeluxe();
        purchase.getClient().setClientType(premiumDeluxe);
        assertEquals(deliveryTime, purchase.getDeliveryDate());
    }
}