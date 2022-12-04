package fortune.connect.connection.controller;

import fortune.connect.connection.controller.request.ConnectionRequest;
import fortune.connect.connection.controller.response.ConnectionListResponse;
import fortune.connect.connection.repository.entity.Connection;
import fortune.connect.connection.repository.entity.RequestConnection;
import fortune.connect.connection.service.ConnectionService;
import fortune.connect.group.controller.response.GroupResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class ConnectionController {

  private final ConnectionService service;

  @PostMapping("/request-connections")
  public ResponseEntity<Void> requestConnection(@RequestBody ConnectionRequest connectionRequest) {
    UUID groupIdFrom_ = UUID.fromString(connectionRequest.getGroupIdFrom());
    UUID groupIdTo_ = UUID.fromString(connectionRequest.getGroupIdTo());
    service.requestConnection(groupIdFrom_, groupIdTo_);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/request-connections/{groupId}")
  public ResponseEntity<ConnectionListResponse> getAllRequestConnectionById(@PathVariable("groupId") String groupIdTo) {
    UUID groupIdTo_ = UUID.fromString(groupIdTo);
    List<RequestConnection> connections = service.getAllRequestConnectionById(groupIdTo_);
    List<String> groupIds = connections.stream().map(c -> c.getGroupIdFrom().toString()).toList();
    ConnectionListResponse response = new ConnectionListResponse(
        groupIds
    );
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/connections")
  public ResponseEntity<GroupResponse> acceptConnectionRequest(@RequestBody ConnectionRequest connectionRequest) {
    UUID groupIdFrom_ = UUID.fromString(connectionRequest.getGroupIdFrom());
    UUID groupIdTo_ = UUID.fromString(connectionRequest.getGroupIdTo());
    service.acceptConnectionRequest(groupIdFrom_, groupIdTo_);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/connections/{groupId}")
  public ResponseEntity<ConnectionListResponse> getAllConnectionsByGroupId(@PathVariable("groupId") String groupId) {
    UUID groupId_ = UUID.fromString(groupId);
    List<Connection> connections = service.getAllConnectionBy(groupId_);
    List<String> groupIds = connections.stream().map(c ->
        {
          if (c.getGroupId1().equals(groupId_)) return c.getGroupId2().toString();
          else return c.getGroupId1().toString();
        }
    ).toList();
    ConnectionListResponse response = new ConnectionListResponse(
        groupIds
    );
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
