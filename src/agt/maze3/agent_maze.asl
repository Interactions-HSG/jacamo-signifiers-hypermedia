maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze3").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                           ?get_thing_artifact("maze", Url, Maze);
                           makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                           !register(1, Url, HTTPArtifact);
                           .print("registration done");
                           ?create_profile("http://localhost:8080/environments/61/workspaces/102/artifacts/", "profile", HTTPArtifact, ProfileUrl);
                           cartago.invoke_obj("maze.Profiles", getPurpose3, Purpose);
                           writePurpose(ProfileUrl, Purpose)[artifact_id(HTTPArtifact)];
                           !register_profile(ProfileUrl, Maze);
                           ?retrieve_signifiers(Maze, List);
                           .print("signifiers received");
                           cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                           ?retrieve_signifiers_array(ListString, HTTPArtifact, SignifierArray);
                           .print("signifier list retrieved");
                           ?find_sequence_plan(SignifierArray, Plan);
                           !use_sequence_plan(Plan, HTTPArtifact, Maze);
                           ?get_current_location(Maze, Room);
                           .print(Room);
                           .print("end").




{ include("../common/common.asl") }