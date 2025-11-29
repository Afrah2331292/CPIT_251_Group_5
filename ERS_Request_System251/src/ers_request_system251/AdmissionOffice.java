package ers_request_system251;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *   Lena
 *   (AdmissionOffice class)
 *   AdmissionOffice class handles the Admission Office actions:
 *   1- Opening the admin page
 *   2- Showing all validated requests
 *   3- Approving requests
 */
public class AdmissionOffice extends User{   
    public AdmissionOffice(String role, String userId, String password) {
        super(role, userId, password);
        }
    
    public void openAdminPage(SystemERS system) {
        Scanner in = new Scanner(System.in);

        System.out.println("\n===== Admission Office Page =====");

        while (true) {

            //All requests that are already validated
            List<Request> validatedRequests = system.fetchVerifiedRequests();
            displayTable(system, validatedRequests);

            System.out.print("\nEnter Request ID to APPROVE (or 'exit'): ");
            String ReqID = in.nextLine().trim(); //Read the ID

            if (ReqID.equalsIgnoreCase("exit"))
                return;

            //Find request by the ID
            Request request = system.findRequestByID(ReqID);

            if (request == null) {
                System.out.println("? Request not found.");  //Wrong ID
                continue;
            }

            //Request must be validated first
            if (!request.getValidationStatus().equalsIgnoreCase("Validated")) {
                System.out.println("? Not validated. Cannot approve.");
                continue;
            }

            //If Request already approved
            if (request.getApprovalStatus().equalsIgnoreCase("Approved")) {
                System.out.println("? Already approved.");
                continue;
            }

            //Approve the request
            system.updateApprovalStatus(ReqID, "Approved");
            System.out.println("? Approved Successfully!");
        }
    }

    private void displayTable(SystemERS system, List<Request> list) {

        System.out.println("\n========================================================================================================================================================");
        System.out.println("| Request ID | Student ID   | Name            | Major               | CertCode | Score | Track | Validation | Approval | Status   | Date       |");
        System.out.println("========================================================================================================================================================");

        SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy"); //For date format

        for (Request req : list) {
            
            //Readable date
            String date = dateF.format(new Date(req.getRequestDate()));

            //Print each request
            System.out.printf(
                "| %-5s | %-7s | %-15s | %-18s | %-8s | %-5.1f | %-5s | %-10s | %-8s | %-8s | %-10s |\n",
                req.getRequestId(),
                req.getStudentId(),
                req.getStudentName(),
                req.getStudentMajor(),
                req.getCertificateCode(),
                req.getScore(),
                system.getTrack(req.getStudentMajor()),
                req.getValidationStatus(),
                req.getApprovalStatus(),
                req.getStatus(),
                date
            );
        }
        System.out.println("========================================================================================================================================================");
    }
}