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
                           ?retrieve_all_signifiers(ListString, HTTPArtifact, SignifierList);
                           ?get0(SignifierList, S1);
                           ?get1(SignifierList, S2);
                           cartago.new_obj("java.util.ArrayList",[], AList);
                           cartago.invoke_obj(AList, add(S1));
                           cartago.invoke_obj(AList, add(S2));
                           .print("before test");
                           ?isFirstSignifier(S1, B);
                           .print(B);
                           if (B){
                                    ?get0(AList, Signifier1);
                                    ?get1(AList, Signifier2);
                           }
                           else {
                                    ?get0(AList, Signifier2);
                                    ?get1(AList, Signifier1);
                           }
                           .print("signifier1");
                           !print_object(Signifier1);
                           .print("signifier2");
                           !print_object(Signifier2);
                           addSignifier("signifier2",Signifier2)[artifact_id(SignifierBase)];
                           ?get_first_affordance(Signifier1, A1);
                           ?get_first_plan(A1, P1);
                           .print("print plan 1");
                           !print_object(P1);
                           .print("before use sequence plan");
                           !use_sequence_plan(P1, HTTPArtifact, Maze);
                           ?get_current_location(Maze, Room);
                           .print(Room);
                           !second_part;
                           .print("end").



+?isFirstSignifier(Signifier, B) : true <-
cartago.invoke_obj(Signifier, getAffordanceList, AffordanceList);
.print("affordance list defined");
?get0(AffordanceList, FirstAffordance);
?get_precondition(FirstAffordance, Precondition);
cartago.invoke_obj("maze.Util", hasPrecondition(Precondition,1), B).



+!second_part : true <-
.print("start second part");
retrieveSignifier("signifier2", Signifier);
?get_first_affordance(Signifier, A);
.print("affordance retrieved");
?get_first_plan(A, P);
.print("plan retrieved");
!print_object(P);
!use_hypermedia_plan(P, HTTPArtifact);
?get_current_location(Maze, Room);
.print(Room);
.print("end second part").


{ include("../common/common.asl") }