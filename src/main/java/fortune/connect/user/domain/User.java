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
  private String username;
  private String password;
  private Email email;
  private String studentId;
  private Sex sex;
  private Department department;
  private String introduction;
  private Picture picture;
  private LocalDateTime createdAt;
  private LocalDateTime updateAt;

  public User(UUID userId, String username, String password, Email email, String studentId, Sex sex, Department department, String introduction, LocalDateTime createdAt, LocalDateTime updateAt) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.email = email;
    this.studentId = studentId;
    this.sex = sex;
    this.department = department;
    this.introduction = introduction;
    this.createdAt = createdAt;
    this.updateAt = updateAt;
  }
}