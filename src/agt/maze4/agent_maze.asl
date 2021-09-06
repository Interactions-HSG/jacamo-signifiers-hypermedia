maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze4").

!start.

+!start : maze_url(Url) <- .print("Start maze");
                            ?get_thing_artifact("maze", Url, Maze);
                            makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                            ?create_signifier_base("signifierBase", SignifierBase);
                            !register(1, Maze);
                            .print("registration done");


                            ?create_profile("http://localhost:8080/environments/61/workspaces/102/artifacts/", "profile", HTTPArtifact, ProfileUrl);
                            .print("Profile Url received");
                             .print(ProfileUrl);
                             cartago.invoke_obj("maze.Profiles", getPurpose4, Purpose);
                             writePurpose(ProfileUrl, Purpose)[artifact_id(HTTPArtifact)];
                             !register_profile(ProfileUrl, Maze);


                            ?retrieve_signifiers(Maze, List);
                            .print("list received");
                             .print(List);
                             cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                             .print(ListString);
                              ?get0(ListString, SignifierUrl1);
                               .print(SignifierUrl1);
                                ?retrieve_content(SignifierUrl1, HTTPArtifact, SignifierContent1);
                                 .print(SignifierContent1);
                                 ?retrieve_signifier(SignifierContent1, S1);
                                ?get1(ListString, SignifierUrl2);
                                print(SignifierUrl2);
                                ?retrieve_content(SignifierUrl2, HTTPArtifact, SignifierContent2);
                                 .print(SignifierContent2);
                                 ?retrieve_signifier(SignifierContent1, S2);
                                  cartago.new_obj("java.util.ArrayList",[], SignifierList);
                                 cartago.invoke_obj(SignifierList, add(S1));
                                 cartago.invoke_obj(SignifierList, add(S1));
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
                                         .print("print affordance");
                                         //!print_affordance(A1);
                                         .print("affordance printed");
                                        ?get_first_plan(A1, P1);
                                         .print("print plan");
                                          !print_plan(P1);
                                          .print("plan printed");
                                           !use_sequence_plan(P1, HTTPArtifact);
                                            ?get_current_location(Maze, Room);
                                             .print(Room);
                                            !second_part;
                                            .print("end").

+?isFirstSignifier(Signifier, B) : true <-
.print("start first signifier");
?get_first_affordance(Signifier, A);
?get_precondition(A, P);
.print("has precondition");
?get_first_statement(P, Statement);
.print("first statement");
.print(Statement);
?get_object(Statement, Value);
.print("here");
!print_value(Value);
?get_last_int(Value, IntValue);
.print(IntValue);
if (IntValue == 1){
//!set_value(B, true);
.nth(0, [true, false], B);
}
else {
//!set_value(B, false);
.nth(1, [true, false], B);
}.



+!second_part : true <-
.print("start second part");
retrieveSignifier("signifier2", Signifier);
?get_first_affordance(Signifier, A);
.print("affordance retrieved");
?get_first_plan(A, P);
.print("plan retrieved");
!print_plan(P);
//useAsSequencePlan(P)[artifact_id(SignifierUtil)];
!use_sequence_plan(P, HTTPArtifact);
//?get_signifier(Manager, Signifier);
.print("end second part").


{ include("../common/common.asl") }