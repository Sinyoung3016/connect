package fortune.connect.group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {
  private String userId;
  private String username;
  private String studentId;
  private String sex;
  private String department;
  private String introduction;
}
