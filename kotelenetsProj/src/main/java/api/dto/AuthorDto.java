package api.endPoints;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    String username;
    String avatar;
}
