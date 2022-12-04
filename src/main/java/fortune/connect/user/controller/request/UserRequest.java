package fortune.connect.user.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String userId;
  private String pictureId;
  private String username;
  private String email;
  private String studentId;
  private String sex;
  private String department;
  private String introduction;
}
