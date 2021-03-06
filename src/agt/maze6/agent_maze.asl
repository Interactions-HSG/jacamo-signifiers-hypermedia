maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze6").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                           ?get_thing_artifact("maze", Url, Maze);
                           makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                           !register(1, Url, HTTPArtifact);
                           .print("registration done");
                           ?get_current_location(Maze,Room);
                           .print(Room);
                           +current_location(Room);
                           while (current_location(X) & X<9){
                                ?retrieve_signifiers(Maze, List);
                                .print("signifiers received");
                                cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                                ?get0(ListString, SignifierUrl);
                                ?retrieve_content(SignifierUrl, HTTPArtifact, SignifierContent);
                                .print("content received");
                                ?retrieve_signifier(SignifierContent, Signifier);
                                .print("signifier retrieved");
                                ?get_first_affordance(Signifier, Affordance);
                                .print("affordance retrieved");
                                ?get_first_plan(Affordance, Plan);
                                .print("plan retrieved");
                                !use_hypermedia_plan(Plan, HTTPArtifact);
                                ?get_current_location(Maze,NewRoom);
                                .print(NewRoom);
                                -+current_location(NewRoom);
                           }

                           .print("end").




{ include("../common/common.asl") }