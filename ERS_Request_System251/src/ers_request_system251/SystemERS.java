package ers_request_system251;

import java.io.*;
import java.util.*;

/**
 * Hana:
 * This class represents the main system controller for the IELTS Exemption Request System (ERS).
 * It handles loading majors, tracks, minimum scores, and reading/writing student requests.
 * This class is used by Student, Staff, and Admin components.
 */
public class SystemERS {

    // ====== DATA STRUCTURES ======
    // Stores all majors (1 → History, 2 → Islamic Studies, ...)
    private Map<Integer, String> majors = new LinkedHashMap<>();

    // Stores the track code for each major (e.g., "Computer Science" → "ELIS")
    private Map<String, String> majorTrack = new HashMap<>();

    // Stores the minimum IELTS score required for each track
    private Map<String, Float> minimumScores = new HashMap<>();

    // Path to the requests file
    private final String REQUEST_FILE = "data" + File.separator + "requests.txt";

    /**
     * Hana:
     * Constructor for SystemERS.
     * When the system starts, it loads majors, tracks, and minimum scores into memory.
     */
    public SystemERS() {
        loadMajors();
        loadTracks();
        loadMinimumScores();
    }

    /**
     * Hana:
     * Loads a list of predefined majors into the system.
     * This list is used in the Student page when selecting a major.
     */
    private void loadMajors() {
        majors.put(1, "History");
        majors.put(2, "Islamic Studies");
        majors.put(3, "Psychology");
        majors.put(4, "English Language");
        majors.put(5, "Nursing");
        majors.put(6, "Computer Science");
        majors.put(7, "Media & Communication");
        majors.put(8, "Architecture");
        majors.put(9, "Mathematics");
        majors.put(10, "Physics");
    }

    /**
     * Hana:
     * Prints all available majors to the console.
     * This is mainly used in the Student interface during request creation.
     */
    public void printMajors() {
        for (Map.Entry<Integer, String> e : majors.entrySet()) {
            System.out.println(e.getKey() + ") " + e.getValue());
        }
    }

    /**
     * Hana:
     * Retrieves the major name given the major number.
     * Example: getMajorName(6) → "Computer Science"
     *
     * @param n major number selected by the student
     * @return major name
     */
    public String getMajorName(int n) { 
        return majors.get(n); 
    }

    /**
     * Hana:
     * Loads the IELTS track group for each major.
     * Each track represents a category with a specific minimum IELTS requirement.
     */
    private void loadTracks() {
        majorTrack.put("History", "ELIA");
        majorTrack.put("Islamic Studies", "ELIA");
        majorTrack.put("Psychology", "ELIA");
        majorTrack.put("English Language", "ELIE");
        majorTrack.put("Nursing", "ELIH");
        majorTrack.put("Computer Science", "ELIS");
        majorTrack.put("Media & Communication", "ELIC");
        majorTrack.put("Architecture", "ELIS");
        majorTrack.put("Mathematics", "ELIS");
        majorTrack.put("Physics", "ELIS");
    }

    /**
     * Hana:
     * Returns the IELTS track code for a specific major.
     *
     * @param major the major selected by the student
     * @return track code (ELIS, ELIA, ELIE...)
     */
    public String getTrack(String major) { 
        return majorTrack.get(major); 
    }

    /**
     * Hana:
     * Loads the minimum IELTS score required for each track.
     * This is used to validate whether the student's certificate score is acceptable.
     */
    private void loadMinimumScores() {
        minimumScores.put("ELIA", 4f);
        minimumScores.put("ELIS", 5f);
        minimumScores.put("ELIE", 5.5f);
        minimumScores.put("ELIH", 5.5f);
        minimumScores.put("ELIC", 4f);
    }

    /**
     * Hana:
     * Validates whether the student's score meets the minimum score for their track.
     *
     * @param t IELTS track code
     * @param s student's certificate score
     * @return true if score ≥ required minimum
     */
    public boolean verifyScore(String t, float s) {
        return s >= minimumScores.get(t);
    }

    /**
     * Hana:
     * Retrieves the minimum required score for a given IELTS track.
     *
     * @param t IELTS track code
     * @return minimum required score
     */
    public float getMinScore(String t) { 
        return minimumScores.get(t); 
    }

    /**
     * Hana:
     * Saves a new request to the requests.txt file.
     * Each request is stored as one line in CSV format.
     *
     * @param r Request object
     * @return same Request object
     */
    public Request saveNewRequest(Request r) {
        FileManager.appendLine(REQUEST_FILE, r.toFileFormat());
        return r;
    }

    /**
     * Hana:
     * Reads all request entries from the file and converts them into Request objects.
     * This method is used by staff and admin to display requests.
     *
     * @return list of all Request objects from the file
     */
    public List<Request> fetchAllRequests() {
        List<String> lines = FileManager.readFile(REQUEST_FILE);
        List<Request> list = new ArrayList<>();

        for (String line : lines) {
            String[] p = line.split(",");

            if (p.length < 10) continue;

            String reqId = p[0];
            String studentId = p[1];
            String name = p[2];
            String major = p[3];
            String cert = p[4];
            float score = Float.parseFloat(p[5]);
            String v = p[7];
            String a = p[8];
            String g = p[9];

            list.add(new Request(reqId, studentId, name, major, cert, score, v, a, g));
        }

        return list;
    }

    /**
     * Hana:
     * Searches for a specific request by its Request ID.
     * Used when staff/admin need to open or update a request.
     *
     * @param reqID ID of the request
     * @return Request object if found, otherwise null
     */
    public Request findRequestByID(String reqID) {
        for (Request r : fetchAllRequests())
            if (r.getRequestId().equals(reqID))
                return r;

        return null;
    }
}
