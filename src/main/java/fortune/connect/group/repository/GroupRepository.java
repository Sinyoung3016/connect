package fortune.connect.group.repository;

import fortune.connect.group.domain.Group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupRepository {
  Group insertNewGroup(Group group);

  Optional<Group> findGroupById(UUID groupId);

  List<Group> findAllGroups();

  List<Group> findAllGroupsByNumOfMember(int num);
}
