package job_search.apply.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import job_search.apply.models.BookmarkJobs;

@Repository
public interface BookmarkJobRepository extends MongoRepository<BookmarkJobs,ObjectId> {
    
}
