maze_url("http://localhost:8080/environments/env1/workspaces/wksp1/artifacts/maze").

signifiers_received(0).

!start.

+!start : maze_url(Url) <- .print("Start maze");
                           ?get_thing_artifact("maze", Url, Maze);
                           makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                           !register(1, Url, HTTPArtifact);
                           .print("registration done");
                           ?create_profile("http://localhost:8080/environments/env1/workspaces/wksp1/artifacts/", "profile", HTTPArtifact, ProfileUrl);
                           .print("Profile created");
                           .print(ProfileUrl);
                           cartago.invoke_obj("maze.Profiles", getPurposeArticle, Purpose);
                           .print("purpose created");
                           writePurpose(ProfileUrl, Purpose)[artifact_id(HTTPArtifact)];
                           .print("purpose written");
                           .print("max signifiers written");

                           ?get_current_location(Maze,Room);
                           .print(Room);
                           +current_location(Room);
                           while (current_location(X) & X<9){
                                ?retrieve_signifiers(Maze, List);
                                .print("signifiers received");
                                cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                                !add_number_signifiers(ListString);
                                ?get0(ListString, SignifierUrl);
                                ?retrieve_content(SignifierUrl, HTTPArtifact, SignifierContent);
                                .print("content received");
                                ?retrieve_signifier(SignifierContent, Signifier);
                                .print("signifier retrieved");
                                ?get_first_affordance(Signifier, Affordance);
                                .print("affordance retrieved");
                                ?get_first_action(Affordance, Action);
                                .print("action retrieved");
                                !use_hypermedia_action(Action, HTTPArtifact);
                                ?get_current_location(Maze,NewRoom);
                                .print(NewRoom);
                                -+current_location(NewRoom);
                           }
                           ?signifiers_received(C);
                           .print("number of signifiers received:");
                           .print(C);

                           .print("end").




{ include("../common/common.asl") }