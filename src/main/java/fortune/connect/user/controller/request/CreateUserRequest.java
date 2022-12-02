package fortune.connect.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
  private String username;
  private String password;
  private String email;
  private String studentId;
  private String sex;
  private String department;
  private String introduction;
  private String userPictureName;
}