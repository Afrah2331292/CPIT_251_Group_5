package ers_request_system251;

import java.io.BufferedReader;
import java.io.FileReader;



public class User {

	
	 protected String role;
	 protected String userId;
	 protected String password;
	 
	 
	 public User(String role, String userId, String password) {
	        this.role = role;
	        this.userId = userId;
	        this.password = password;
	    }
	 
	 
	 
	 
	    public String getRole() { 
	    	return this.role; }
	    
	    public String getUserId() { 
	    	return this.userId; }
	    
	    public String getPassword() {
	    	return this.password; }
	    
	    
	    
	    public static User login(String id, String pass) {
	        // Try to read the users file
	        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {

	            String line;
	            // Read file line by line
	            while ((line = br.readLine()) != null) {

	                String[] parts = line.split(",");
	                // Skip the line if it doesn't contain exactly 3 parts
	                if (parts.length != 3) continue;

	                String role = parts[0].trim();
	                String fileId = parts[1].trim();
	                String filePass = parts[2].trim();

	                // If ID or password do not match, skip this user
	                if (!fileId.equals(id) || !filePass.equals(pass))
	                    continue;

	                // Create the correct user type based on the role
	                switch (role) {
	                    case "student":
	                       

	                    case "institute":
	                    
	                    case "dean":
	                      	                }
	            }

	        } catch (Exception e) {
	            // If any error happens while reading the file
	            System.out.println("Error reading file: " + e.getMessage());
	        }

	        // No matching user found
	        return null;
	    }

	    
	    
	    
	    
	    
	    
	    
}

