import ers_request_system251.Request;
import ers_request_system251.Student;
import ers_request_system251.SystemERS;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentIT {

    // HANA: Test case for submitting a request with a low score (should return null)
    @Test
    public void testSubmitRequest_LowScore() {
        SystemERS system = new SystemERS();
        Student student = new Student("Student", "2331341", "Hana2003");

        Request r = student.submitRequest(
                "Hana",
                "Computer Science",
                "ELIS",           // ELIS minimum score = 5
                "CERT-HANA-01",   // certificate code
                3.0f,             // score below minimum
                system
        );

        assertNull(r);
    }

    // HANA: Test case for submitting a request with a valid score (should create a Request object)
    @Test
    public void testSubmitRequest_ValidScore() {
        SystemERS system = new SystemERS();
        Student student = new Student("Student", "2331341", "Hana2003");

        Request r = student.submitRequest(
                "Hana",
                "Computer Science",
                "ELIS",           // minimum score = 5
                "CERT-HANA-02",
                5.5f,             // valid score
                system
        );

        assertNotNull(r);
        assertEquals("Hana", r.getStudentName());
        assertEquals("Computer Science", r.getStudentMajor());
        assertEquals(5.5f, r.getScore(), 0.0);
    }

    // HANA: Test case for submitting a request with a score equal to the minimum (should be accepted)
    @Test
    public void testSubmitRequest_ExactMinScore() {
        SystemERS system = new SystemERS();
        Student student = new Student("Student", "2331341", "Hana2003");

        Request r = student.submitRequest(
                "Sara",
                "English Language",
                "ELIE",            // minimum score = 5.5
                "CERT-HANA-03",
                5.5f,              // exact minimum
                system
        );

        assertNotNull(r);
        assertEquals("Sara", r.getStudentName());
        assertEquals("English Language", r.getStudentMajor());
        assertEquals(5.5f, r.getScore(), 0.0);
        assertEquals("ELIE", system.getTrack("English Language"));
    }
}
