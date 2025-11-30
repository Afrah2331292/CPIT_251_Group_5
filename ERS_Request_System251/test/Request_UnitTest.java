import ers_request_system251.FileManager;
import ers_request_system251.Request;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/*
Lena:
Unit Test for Request class
Methods:
- setValidationStatus()
Steps:
1- First request from requests.txt
2- Convert line into a Request object
3- Apply method
*/

public class Request_UnitTest {
    
    private static final String REQUEST_FILE = "/Users/lena/Desktop/requests.txt";
    private Request sampleRequest;

    //Before each test (load request from the file)
    @Before
    public void setUp() {
        System.out.println("Request from file");

        //Read the lines in the request file
        List<String> lines = FileManager.readFile(REQUEST_FILE);
        assertTrue("Request file must not be empty", lines.size() > 0); //File is not empty

        //First request line in the file
        String line = lines.get(0);
        String[] p = line.split(",");


sampleRequest = new Request(
    p[0],            // requestId
    p[1],            // studentId
    p[2],            // name
    p[3],            // major
    p[4],            // certificate
    Float.parseFloat(p[5]), // score
    p[7],            // validation
    p[8]             // approval
);}

    @Test
    public void testSetValidationStatus() {
        System.out.println("setValidationStatus");

        //Apply update
        sampleRequest.setValidationStatus("Validated");

        assertEquals("Validated", sampleRequest.getValidationStatus());
        assertEquals("Validated", sampleRequest.getStatus());
    }
}


