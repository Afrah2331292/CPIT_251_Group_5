//import ers_request_system251.Request;
import ers_request_system251.Student;
import ers_request_system251.SystemERS;
//import org.junit.Test;
//import static org.junit.Assert.*;

//This is Afrah's Unit Test Section 
//-----------------------------------------------------------

public class ApprovalStatus_UnitTest {

 // ----------------------------
 // Constants for test file paths
 // ----------------------------
 private static final String READ_TEST_FILE = "test_readFile.txt";
 private static final String WRITE_FILE = "test_writeFile.txt";
 private static final String TEST_REQUEST_FILE = "test_requests.txt";

 // --------------------------------
 // Setup method runs before each test
 // --------------------------------
 @Before
 void setUp() throws IOException {
     // Prepare a simple file for testing the readFile method
     FileWriter writer = new FileWriter(READ_TEST_FILE);
     writer.write("Line1\nLine2\nLine3");
     writer.close(); // flush is not needed here

     // Prepare a test file for updateApprovalStatus simulation
     FileManager.writeAll(TEST_REQUEST_FILE, List.of(
         "REQ001,John,Doe,Dept1,2025-11-27,Desc1,TypeA,Cat1,Pending,Pending",
         "REQ002,Jane,Smith,Dept2,2025-11-28,Desc2,TypeB,Cat2,Pending,Pending"
     ));
 }

 //-----------------------------------------------------------------
 // Test for readFile method
 //-----------------------------------------------------------------
 @Test
 void testReadFile() {
     // Call the readFile method
     List<String> lines = FileManager.readFile(READ_TEST_FILE);

     // Check the number of lines read
     assertEquals(3, lines.size(), "Should read 3 lines");

     // Check the content of each line
     assertEquals("Line1", lines.get(0));
     assertEquals("Line2", lines.get(1));
     assertEquals("Line3", lines.get(2));
 }

 //-----------------------------------------------------------------
 // Test for writeAll method
 //-----------------------------------------------------------------
 @Test
 void testWriteAll() throws IOException {
     // Prepare some lines to write
     List<String> linesToWrite = Arrays.asList("Hello", "World", "JUnit");

     // Call writeAll to write the lines into the file
     FileManager.writeAll(WRITE_FILE, linesToWrite);

     // Read the file back and verify the content
     List<String> readLines = FileManager.readFile(WRITE_FILE);
     assertEquals(linesToWrite, readLines, "File content should match written lines");
 }

 //-----------------------------------------------------------------
 // Test for direct status update (without using FileManager helper)
 //-----------------------------------------------------------------
 @Test
 void updateApprovalStatus() throws IOException {
     // Read all lines from the test request file
     List<String> lines = Files.readAllLines(Paths.get(TEST_REQUEST_FILE));
     List<String> updated = new ArrayList<>();

     for (String line : lines) {
         // If the line corresponds to REQ001, update approval and general status
         if (line.startsWith("REQ001,")) {
             String[] parts = line.split(",");
             parts[parts.length - 1] = "Approved"; // Update general status
             parts[parts.length - 2] = "Approved"; // Update approval status
             updated.add(String.join(",", parts));
         } else {
             // Keep other lines unchanged
             updated.add(line);
         }
     }

     // Write updated lines back to the file
     Files.write(Paths.get(TEST_REQUEST_FILE), updated);

     // Verify that the first request was updated and the second stayed unchanged
     List<String> newLines = Files.readAllLines(Paths.get(TEST_REQUEST_FILE));
     assertTrue(newLines.get(0).contains("Approved"), "REQ001 should be approved");
     assertTrue(newLines.get(1).contains("Pending"), "REQ002 should remain pending");
 }

 //-----------------------------------------------------------------
 // fileDelete method runs after each test
 //-----------------------------------------------------------------
 @After
 void fileDelete() throws IOException {
     // Delete all test files if they exist
     Files.deleteIfExists(Paths.get(READ_TEST_FILE));
     Files.deleteIfExists(Paths.get(WRITE_FILE));
     Files.deleteIfExists(Paths.get(TEST_REQUEST_FILE));
 }
}
