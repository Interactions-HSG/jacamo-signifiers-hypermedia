maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze2").

!start.

+!start : maze_url(Url) <- .print("Start");
                  ?get_thing_artifact("maze", Url, Maze);
                  makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                  !register(6, Url, HTTPArtifact);
                  ?retrieve_signifiers(Maze, List);
                  .print("signifiers received");
                  cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                  ?retrieve_signifiers_array(ListString, HTTPArtifact, SignifierArray);
                  .print("signifier list retrieved");
                  ?find_sequence_plan(SignifierArray, Plan);
                  !print_object(Plan);
                  !use_sequence_plan(Plan, HTTPArtifact, Maze);
                  ?get_current_location(Maze,Room);
                  .print(Room);
                  .print("end").



{ include("../common/common.asl") }