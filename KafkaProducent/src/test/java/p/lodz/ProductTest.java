package p.lodz;

import org.junit.jupiter.api.Test;
import p.lodz.Model.Product;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

        private final String testProductName1 = "Novel book 'Nad doliną' written by Adam Dostojnievky";
        private final String testProductName2 = "Novel book 'Pod młynem' written by Adam Dostojnievky";
        private double baseCost1 = 30.00;
        private double baseCost2 = 40.00;
        private double discount2 = 0.08;
        private int numberOfProducts1 = 1;
        private int numberOfProducts2 = 29;
        private final String description1 = "Number of pages: 214, Type of cover: hard";
        private final String description2 = "Number of pages: 2124, Type of cover: soft";

        private Product testProduct1 = new Product(testProductName1, baseCost1, numberOfProducts1, description1);

        @Test
        public void productConstructorTest() {
            assertEquals(testProductName1, testProduct1.getProductName());
            assertEquals(baseCost1, testProduct1.getBaseCost(), 0.01);
            assertEquals(0.00, testProduct1.getDiscount(), 0.01);
            assertEquals(numberOfProducts1, testProduct1.getNumberOfProducts());
            assertFalse(testProduct1.isArchived());
        }

        @Test
        public void productSettersTest() {

            assertEquals(testProductName1, testProduct1.getProductName());
            testProduct1.setProductName(testProductName2);
            testProduct1.setDiscount(discount2);
            testProduct1.setBaseCost(baseCost2);
            testProduct1.setNumberOfProducts(numberOfProducts2);
            testProduct1.setArchived(true);

            assertEquals(testProductName2, testProduct1.getProductName());
            assertEquals(baseCost2, testProduct1.getBaseCost(), 0.01);
            assertEquals(discount2, testProduct1.getDiscount(), 0.01);
            assertEquals(numberOfProducts2, testProduct1.getNumberOfProducts());
        }
    }

