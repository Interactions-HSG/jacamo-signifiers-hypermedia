maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze1").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                  ?get_thing_artifact("maze", Url, Maze);
                  makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                  //register;
                  !register(1, Maze);
                  //retrieveVisibleSignifiers(List);
                  ?retrieve_signifiers(Maze, List);
                  .print("list received");
                  .print(List);
                  cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                  .print(ListString);
                  ?get0(ListString, SignifierUrl);
                  .print(SignifierUrl);
                  ?retrieve_content(SignifierUrl, HTTPArtifact, SignifierContent);
                  .print(SignifierContent);
                 ?retrieve_signifier(SignifierContent, Signifier);
                  //?get_first_affordance_from_structure(Signifier, Affordance).
                  ?get_first_affordance(Signifier, Affordance);
                  ?get_first_plan(Affordance, Plan);
                  !use_sequence_plan(Plan, HTTPArtifact);
                  ?get_current_location(Maze,Room);
                  .print(Room);
                  .print("end").

+!start2 : maze_url(Url) <- .print("Start maze");
                            ?get_thing_artifact("maze", Url, Maze);
                            makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                            //!register(1, Url, HTTPArtifact);
                            !register(1, Maze);
                            .print("registration done");
                            //?retrieve_signifiers(Url, HTTPArtifact, List);
                            ?retrieve_signifiers(Maze, List);
                            .print("list received");
                             .print(List);
                             cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                             .print(ListString);
                              ?get0(ListString, SignifierUrl);
                              .print(SignifierUrl);

                              ?retrieve_content(SignifierUrl, HTTPArtifact, SignifierContent);
                              .print("content received");
                              //.print(SignifierContent);
                               ?retrieve_signifier(SignifierContent, Signifier);
                               .print("signifier retrieved");
                               ?get_first_affordance(Signifier, Affordance);
                               .print("affordance retrieved");
                                ?get_first_plan(Affordance, Plan);
                                .print("plan retrieved");
                                .print("print plan");
                                !print_plan(Plan);
                                .print("plan printed");
                                !use_sequence_plan(Plan, HTTPArtifact,Maze);
                                //?get_current_location(Url, HTTPArtifact, Room);
                                ?get_current_location(Maze,Room);
                                .print(Room);

                            .print("end").




{ include("../common/common.asl") }