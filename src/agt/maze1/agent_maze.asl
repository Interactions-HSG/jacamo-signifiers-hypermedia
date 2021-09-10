maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze1").

!start.

+!start : maze_url(Url) <- .print("Start");
                  ?get_thing_artifact("maze", Url, Maze);
                  makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                  !register(1, Url, HTTPArtifact);
                  ?retrieve_signifiers(Maze, List);
                  .print("signifiers received");
                  cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                  ?retrieve_all_signifiers(ListString, HTTPArtifact, SignifierList);
                  .print("signifier list retrieved");
                  //cartago.invoke_obj("util.FeedbackUtil", getSequencePlan(SignifierList), Plan);
                  //?find_sequence_plan(SignifierList, Plan);
                  cartago.invoke_obj("maze.MazeUtil", getPlan1, Plan);
                  .print(Plan);
                  !print_class(Plan);
                  .print("print plan");
                  !print_plan(Plan);
                  !use_sequence_plan(Plan, HTTPArtifact, Maze);
                  ?get_current_location(Maze,Room);
                  .print(Room);
                  .print("end").



{ include("../common/common.asl") }