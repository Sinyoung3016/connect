package fortune.connect.group.controller.request;

import fortune.connect.user.controller.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {
  private String ownerId;
  private String title;
  private String subtitle;
  private String interestTag;
  private int numOfMember;
  private List<UserRequest> groupMember;
}

