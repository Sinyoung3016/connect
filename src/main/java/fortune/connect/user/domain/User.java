package fortune.connect.user.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private UUID userId;
  private UUID pictureId;
  private String username;
  private String password;
  private Email email;
  private String studentId;
  private Sex sex;
  private String department;
  private String introduction;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public User(UUID pictureId, String username, String password, Email email, String studentId, Sex sex, String department, String introduction) {
    this.pictureId = pictureId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.studentId = studentId;
    this.sex = sex;
    this.department = department;
    this.introduction = introduction;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
}