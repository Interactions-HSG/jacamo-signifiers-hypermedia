maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze5").


+!start : maze_url(Url) <- .print("Start");
                  ?get_thing_artifact("maze2", Url, Maze);
                  makeArtifact("httpArtifact2", "wot.HTTPArtifact", [], HTTPArtifact);
                  !register(1, Url, HTTPArtifact);
                  ?retrieve_signifiers(Maze, List);
                  .print("signifiers received");
                  cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                  ?retrieve_signifiers_array(ListString, HTTPArtifact, SignifierArray);
                  .print("signifier list retrieved");
                  ?find_sequence_plan(SignifierArray, Plan);
                  //cartago.invoke_obj("maze.MazeUtil", getPlan1, Plan);
                  !print_object(Plan);
                  !use_sequence_plan(Plan, HTTPArtifact, Maze);
                  ?get_current_location(Maze,Room);
                  .print(Room);
                  .print("end").



{ include("../common/common.asl") }