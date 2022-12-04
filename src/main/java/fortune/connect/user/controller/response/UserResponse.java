package fortune.connect.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
  private String userId;
  private String username;
  private String email;
  private String studentId;
  private String sex;
  private String department;
  private String introduction;
  private String userPicturePath;
}
