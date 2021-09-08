maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze4").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                           ?get_thing_artifact("maze", Url, Maze);
                           makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                           ?create_signifier_base("signifierBase", SignifierBase);
                           !register(1, Url, HTTPArtifact);
                           .print("registration done");
                           ?create_profile("http://localhost:8080/environments/61/workspaces/102/artifacts/", "profile", HTTPArtifact, ProfileUrl);
                           cartago.invoke_obj("maze.Profiles", getPurpose4, Purpose);
                           writePurpose(ProfileUrl, Purpose)[artifact_id(HTTPArtifact)];
                           !register_profile(ProfileUrl, Maze);
                           ?retrieve_signifiers(Maze, List);
                           .print("signifiers received");
                           cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                           ?get0(ListString, SignifierUrl1);
                           ?retrieve_content(SignifierUrl1, HTTPArtifact, SignifierContent1);
                           ?retrieve_signifier(SignifierContent1, S1);
                           ?get1(ListString, SignifierUrl2);
                           ?retrieve_content(SignifierUrl2, HTTPArtifact, SignifierContent2);
                           ?retrieve_signifier(SignifierContent2, S2);
                           cartago.new_obj("java.util.ArrayList",[], SignifierList);
                           cartago.invoke_obj(SignifierList, add(S1));
                           cartago.invoke_obj(SignifierList, add(S2));
                           ?isFirstSignifier(S1, B);
                           .print(B);
                           if (B){
                                    ?get0(SignifierList, Signifier1);
                                    ?get1(SignifierList, Signifier2);
                           }
                           else {
                                    ?get0(SignifierList, Signifier2);
                                    ?get1(SignifierList, Signifier1);
                           }
                           addSignifier("signifier2",Signifier2)[artifact_id(SignifierBase)];
                           ?get_first_affordance(Signifier1, A1);
                           ?get_first_plan(A1, P1);
                           !use_sequence_plan(P1, HTTPArtifact);
                           ?get_current_location(Maze, Room);
                           .print(Room);
                           !second_part;
                           .print("end").



+?isFirstSignifier(Signifier, B) : true <-
cartago.invoke_obj(Signifier, getAffordanceList, AffordanceList);
?get0(AffordanceList, FirstAffordance);
cartago.invoke_obj(FirstAffordance, getPrecondition, OpPrecondition);
cartago.invoke_obj(OpPrecondition, get, Precondition);
cartago.invoke_obj("maze.Util", hasPrecondition(Precondition,1), B).



+!second_part : true <-
.print("start second part");
retrieveSignifier("signifier2", Signifier);
?get_first_affordance(Signifier, A);
.print("affordance retrieved");
?get_first_plan(A, P);
.print("plan retrieved");
!use_hypermedia_plan(P, HTTPArtifact);
?get_current_location(Maze, Room);
.print(Room);
.print("end second part").


{ include("../common/common.asl") }