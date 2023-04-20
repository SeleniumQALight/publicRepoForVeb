package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //or @Data instead of getter/setter
@AllArgsConstructor
@NoArgsConstructor //because default constructor is dissapeared
@Builder //only fields that I needed, another is null
public class PostDto {
    @JsonProperty("_id") //if different from name of value
    String id;
    String title;
    String body;
    String select1;
    String uniquePost;
    String createdDate;
    AuthorDto author;
    Boolean isVisitorOwner;


}
