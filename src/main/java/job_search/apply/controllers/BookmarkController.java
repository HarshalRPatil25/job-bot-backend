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

import job_search.apply.models.BookmarkJobs;

import job_search.apply.services.BookmarkJobService;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    
    @Autowired
    private BookmarkJobService bookmarjJobService;
 
       @PostMapping("/add/{bookmarked}")
    public ResponseEntity<?> BookmarkJobs(@RequestBody BookmarkJobs  jobs,@PathVariable boolean bookmarked){
        Authentication authenticationDetails=SecurityContextHolder.getContext().getAuthentication();
        try{
            if(authenticationDetails.isAuthenticated() && (authenticationDetails!=null)){
                String username=authenticationDetails.getName();
                List<BookmarkJobs> markedJobs=bookmarjJobService.addBookmarkJob(username, bookmarked, jobs);
                if(markedJobs!=null){
                    return ResponseEntity.ok("Job successfully added to your bookmarked jobs list: " + markedJobs);
                }
                else{
                    return new ResponseEntity<>("Job application was not marked as applied.",HttpStatus.BAD_REQUEST);
                }
               
            }
            return new ResponseEntity<>("User not found or an error occurred.",HttpStatus.NOT_FOUND);
              

        }
        catch(Exception e){
            return new ResponseEntity<>("An error is occured \n",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
