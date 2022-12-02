package fortune.connect.user.service.dto;

import fortune.connect.user.domain.Email;
import fortune.connect.user.domain.Picture;
import fortune.connect.user.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
  private String username;
  private String password;
  private Email email;
  private String studentId;
  private Sex sex;
  private String department;
  private String introduction;
  private Picture picture;
}
