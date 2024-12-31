package job_search.apply.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import job_search.apply.models.AppliedJobs;
import job_search.apply.models.Users;
import job_search.apply.repositories.JobRepository;
import job_search.apply.repositories.UserRepository;

@Service
public class AppliedJobService {

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private JobRepository jobRepository;

    // Method to apply for a job
    public List<AppliedJobs> applyForJob(String username, boolean applied, AppliedJobs job) {
        // Find the current user by username
        Users currentUser = userRepository.findByUsername(username);
        if (currentUser != null && applied) {
            // If user exists and job is to be applied
            List<AppliedJobs> appliedJobs = currentUser.getList();

            // Add the job to the user's applied jobs list
            if (appliedJobs == null) {
                appliedJobs = new ArrayList<>(); // Initialize if the list is empty
            }
            appliedJobs.add(job);  // Add the job to the list

            // Save the applied job and update the user's list
            job = jobRepository.save(job); // Save job to the database
            currentUser.setList(appliedJobs); // Save the updated applied jobs list to the user
            userRepository.save(currentUser); // Save updated user data

            return appliedJobs; // Return the updated list of applied jobs
        }

        return null; // Return null if user is not found or applied is false
    }

    // Method to get the list of applied jobs for a user
    public List<AppliedJobs> getAppliedJobs(String username) {
        Users currentUser = userRepository.findByUsername(username);
        if (currentUser != null) {
            return currentUser.getList(); // Return the list of applied jobs
        }
        return null; // Return an empty list if user not found
    }
}
