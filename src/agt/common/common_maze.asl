+!register1(Room, Maze) <-
invokeAction("http://example.org/register", [Room])[artifact_id(Maze)].

+!register(Room, Url, HTTPArtifact) : true <-
cartago.invoke_obj("maze.Util", createPayloadFromInteger(Room), Payload);
.concat(Url, "/register", RegisterUrl);
!send_request_payload(RegisterUrl, Payload, HTTPArtifact).


+?get_current_location(Maze,Room) : true <-
invokeActionReturn("http://example.org/current", [], R)[artifact_id(Maze)];
cartago.invoke_obj(R, get, RoomString);
cartago.invoke_obj("util.FeedbackUtil", getStringAsInt(RoomString), Room);
.print("location retrieved").


+?get_current_location2(Url, HTTPArtifact, Room) : true <-
retrieveCurrentLocation(Url, Room)[artifact_id(HTTPArtifact)].


+!move1(Maze, M): true <-
invokeAction("http://example.org/move", [[M]])[artifact_id(Maze)].

+!move(M, Url, HTTPArtifact) : true <-
cartago.invoke_obj("maze.Util", createPayloadFromInteger(M), Payload);
.concat(Url, "/move", MoveUrl);
.print(MoveUrl);
!send_request_payload(MoveUrl, Payload, HTTPArtifact).

+?two_list_to_string(JasonList, StringList) : true<-
.nth(0, JasonList, A);
.nth(1, JasonList, B);
cartago.new_obj("java.util.ArrayList", [], JavaList);
cartago.invoke_obj(JavaList, add(A));
cartago.invoke_obj(JavaList, add(B));
cartago.invoke_obj(JavaList, toString, StringList).

+?create_string(Room, Payload) : true <-
cartago.new_obj("java.util.ArrayList", [], JavaList);
cartago.invoke_obj(JavaList, add(Room));
cartago.invoke_obj(JavaList, toString, Payload);
.print("payload");
.print(Payload).
