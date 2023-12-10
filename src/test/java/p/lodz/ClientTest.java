package p.lodz;

import org.junit.jupiter.api.Test;
import p.lodz.Model.Address;
import p.lodz.Model.Client;
import p.lodz.Model.Type.ClientType;
import p.lodz.Model.Type.Premium;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    private final String testFirstName1 = "Kuba";
    private final String testLastName1 = "Jest";
    private final String testFirstName2 = "Konrad";
    private final String testLastName2 = "Byl";
    private ClientType clientType = new Premium();
    private Client testClient1 =  new Client(testFirstName1, testLastName1, new Address("aaa", "bbb", "ccc"),clientType);
    private Client getTestClient2 = new Client(testFirstName2,testLastName2,new Address("nie","wiem","3"),clientType);

    @Test
    public void clientConstructorTest() {
        assertEquals(testFirstName1, testClient1.getFirstName());
        assertEquals(testLastName1, testClient1.getLastName());
//        assertFalse(testClient1.getArchived());
    }

    @Test
    public void clientSettersTest() {
        assertEquals(testFirstName1, testClient1.getFirstName());
        assertEquals(testLastName1, testClient1.getLastName());

        testClient1.setFirstName(testFirstName2);
        testClient1.setLastName(testLastName2);
//        testClient1.setArchived(true);

        assertEquals(testFirstName2, testClient1.getFirstName());
        assertEquals(testLastName2, testClient1.getLastName());
//        assertTrue(testClient1.isArchived());
    }
}
