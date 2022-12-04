package fortune.connect.group.service.dto;

import fortune.connect.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupDto {
  private UUID ownerId;
  private String title;
  private String subtitle;
  private String interestTag;
  private int numOfMember;
  private List<User> groupMember;
}
