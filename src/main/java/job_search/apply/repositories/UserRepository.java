package job_search.apply.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import job_search.apply.models.Users;



public interface UserRepository extends MongoRepository<Users,ObjectId> {
    public Users findByUsername(String username);
}
