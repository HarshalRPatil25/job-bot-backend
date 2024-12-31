package job_search.apply.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import job_search.apply.models.BookmarkJobs;
import job_search.apply.models.Users;
import job_search.apply.repositories.BookmarkJobRepository;
import job_search.apply.repositories.UserRepository;

@Service
public class BookmarkJobService {

    @Autowired
    private BookmarkJobRepository bookmarkJobRepository;

    @Autowired 
    private UserRepository userRepository;

    // Method to add a bookmarked job
    @Transactional
    public List<BookmarkJobs> addBookmarkJob(String username, boolean bookmark, BookmarkJobs jobs) {
        Users currentUser = userRepository.findByUsername(username);
    
        if (currentUser == null || !bookmark) {
            return null; // Return null if user not found or bookmark flag is false
        }
    
        // Save the job entity first
        BookmarkJobs savedJob = bookmarkJobRepository.save(jobs);
    
        // Add the saved job to the user's bookmark jobs list
        List<BookmarkJobs> bookmarkJobsList = currentUser.getBookmarkJobsList();
        if (bookmarkJobsList == null) {
            bookmarkJobsList = new ArrayList<>();
        }
    
        bookmarkJobsList.add(savedJob);
        currentUser.setBookmarkJobsList(bookmarkJobsList);
    
        // Save the user entity to persist the relationship
        userRepository.save(currentUser);
    
        return bookmarkJobsList;
    }
    
    

    // Method to get the list of bookmarked jobs for a user
    public List<BookmarkJobs> getBookmarkJobsList(String username) {
        Users currentUser = userRepository.findByUsername(username);

        if (currentUser != null) {
            List<BookmarkJobs> bookmarkJobsList = currentUser.getBookmarkJobsList();
            return (bookmarkJobsList != null) ? bookmarkJobsList : new ArrayList<>();
        }

        return new ArrayList<>(); // Return an empty list if user is not found
    }
}
