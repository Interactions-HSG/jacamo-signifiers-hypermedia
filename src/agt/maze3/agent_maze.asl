maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze3").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                            ?get_thing_artifact("maze", Url, Maze);
                            makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                            //!register(1, Url, HTTPArtifact);
                            !register(1, Maze);
                            .print("registration done");


                            ?create_profile("http://localhost:8080/environments/61/workspaces/102/artifacts/", "profile", HTTPArtifact, ProfileUrl);
                            .print("Profile Url received");
                             .print(ProfileUrl);
                             cartago.invoke_obj("maze.Profiles", getPurpose3, Purpose);
                             writePurpose(ProfileUrl, Purpose)[artifact_id(HTTPArtifact)];
                             !register_profile(ProfileUrl, Maze);


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
                              .print(SignifierContent);
                               ?retrieve_signifier(SignifierContent, Signifier);
                               .print("signifier retrieved");
                               ?get_first_affordance(Signifier, Affordance);
                               .print("affordance retrieved");
                                ?get_first_plan(Affordance, Plan);
                                .print("plan retrieved");
                                !use_sequence_plan(Plan, HTTPArtifact);
                                //?get_current_location(Url, HTTPArtifact, Room);
                                ?get_current_location(Maze, Room);
                                .print(Room);

                            .print("end").




{ include("../common/common.asl") }