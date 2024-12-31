package job_search.apply.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import job_search.apply.models.AppliedJobs;

import job_search.apply.services.AppliedJobService;

@RequestMapping("/applied")
@RestController
public class AppliedJobList {

     @Autowired
     private AppliedJobService appliedJobService;


     @PostMapping("/add/{applied}")

public ResponseEntity<?> applyJob(@PathVariable boolean applied, @RequestBody AppliedJobs job) {
    try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated.");
        }

        String currentUsername = auth.getName();
        List<AppliedJobs> savedAppliedJobs =appliedJobService.applyForJob(currentUsername, applied, job);

        if (savedAppliedJobs != null) {
            return ResponseEntity.ok("Job successfully added to your applied jobs list: " + savedAppliedJobs);
        } else if (!applied) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Job application was not marked as applied.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found or an error occurred.");
        }

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An internal error occurred: " + e.getMessage());
    }
}



    
    
}
