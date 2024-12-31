package job_search.apply.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import job_search.apply.models.AppliedJobs;

public interface JobRepository extends MongoRepository<AppliedJobs,ObjectId> {
    
}
