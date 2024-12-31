package job_search.apply.models;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection="BookmarkJobs")
public class BookmarkJobs {

   @Id
    private ObjectId jobId;

    private String CompanyName;

    private String title;

    private String experience;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date date = new Date();

    private String platformLink;
}