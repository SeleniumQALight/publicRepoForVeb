package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    @JsonProperty("_id")
    String id;
    String title;
    String body;
    String select1;
    String uniquePost;
    String createdDate;
    AuthorDto author;
    Boolean isVisitorOwner;

}
