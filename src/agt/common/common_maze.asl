+!register(Room, Maze) <-
invokeAction("http://example.org/register", [Room, 0])[artifact_id(Maze)].

+!register2(Room, Url, HTTPArtifact) : true <-
//cartago.invoke_obj("util.FeedbackUtil", getIntListAsString([Room,0]), Payload);
//!send_request_payload(Url, [Room, 0], HTTPArtifact).
?two_list_to_string([Room,0], Payload);
.print(Payload);
!send_request_payload(Url, Payload, HTTPArtifact).


+?get_current_location(Maze,Room) : true <-
invokeActionReturn("http://example.org/current", [], R)[artifact_id(Maze)];
cartago.invoke_obj(R, get, RoomString);
cartago.invoke_obj("util.FeedbackUtil", getStringAsInt(RoomString), Room);
.print("location retrieved").


+?get_current_location2(Url, HTTPArtifact, Room) : true <-
retrieveCurrentLocation(Url, Room)[artifact_id(HTTPArtifact)].


+!move(Maze, M): true <-
invokeAction("http://example.org/move", [M,0])[artifact_id(Maze)].

+!move2(Url, HTTPArtifact, M): true <-
//invokeAction("http://example.org/move", [M,0])[artifact_id(Maze)].
move(Url, M)[artifact_id(HTTPArtifact)].

+?two_list_to_string(JasonList, StringList) : true<-
.nth(0, JasonList, A);
.nth(1, JasonList, B);
cartago.new_obj("java.util.ArrayList", [], JavaList);
cartago.invoke_obj(JavaList, add(A));
cartago.invoke_obj(JavaList, add(B));
cartago.invoke_obj(JavaList, toString, StringList).
