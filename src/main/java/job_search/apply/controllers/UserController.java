package job_search.apply.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import job_search.apply.models.AppliedJobs;
import job_search.apply.models.BookmarkJobs;
import job_search.apply.models.Users;
import job_search.apply.services.AppliedJobService;
import job_search.apply.services.BookmarkJobService;

import job_search.apply.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AppliedJobService appliedJobService;

    @Autowired
    private UserService userService;


    @Autowired
    private BookmarkJobService bookmarjJobService;


    /**
     * Endpoint to apply for a job and add it to the user's applied jobs list.
     *
     * @param jobs Job details provided in the request body.
     * @return ResponseEntity with appropriate status and message.
     */
   


 



    /**
     * Endpoint to fetch the authenticated user's details.
     *
     * @return ResponseEntity with the user data or error message.
     */

     @GetMapping("/appliedjobs")
     public ResponseEntity<?> getAppliedJobsList() {
         Authentication authenticationDetails = SecurityContextHolder.getContext().getAuthentication();
         try {
             if (authenticationDetails.isAuthenticated()) {
                 String username = authenticationDetails.getName(); // Fetch username from security context
                 List<AppliedJobs> appliedJobsList = appliedJobService.getAppliedJobs(username);
                 if (appliedJobsList != null && !appliedJobsList.isEmpty()) {
                     return new ResponseEntity<>(appliedJobsList, HttpStatus.OK);
                 } else {
                     return new ResponseEntity<>("No applied jobs found", HttpStatus.NOT_FOUND);
                 }
             } else {
                 return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
             }
         } catch (Exception e) {
             return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }
     


     @GetMapping("/bookmarkjobs")
public ResponseEntity<?> getBookmarkJobsList() {
    Authentication authenticationDetails = SecurityContextHolder.getContext().getAuthentication();
    try {
        if (authenticationDetails.isAuthenticated()) {
            String username = authenticationDetails.getName(); // Fetch username securely
            List<BookmarkJobs> bookmarkJobsList = bookmarjJobService.getBookmarkJobsList(username);
            if (bookmarkJobsList != null && !bookmarkJobsList.isEmpty()) {
                return new ResponseEntity<>(bookmarkJobsList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No bookmarked jobs found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
        }
    } catch (Exception e) {
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




    
    @GetMapping
    public ResponseEntity<?> getUserDetails() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
            }

            String username = auth.getName();
            Users currentUser = userService.getUserData(username);

            if (currentUser != null) {
                return ResponseEntity.ok(currentUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/update-userProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Users updaUsers){
        try{
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            if(auth.isAuthenticated()){
                String username=auth.getName();
                Users currentUsers=userService.updateUserProfile(username, updaUsers);
                  if(currentUsers!=null){
                      return new ResponseEntity<>("Profile updated successfully",HttpStatus.OK);
                  }
            }
            return new ResponseEntity<>("Profile updation failed",HttpStatus.CONFLICT);

        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An internal server error occurred: " + e.getMessage());

        }
    }

    
}
