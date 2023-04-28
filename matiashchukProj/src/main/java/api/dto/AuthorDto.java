package api.endPoints;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
	String username;
	String avatar;
}
