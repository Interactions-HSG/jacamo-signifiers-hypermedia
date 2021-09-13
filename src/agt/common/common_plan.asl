+?get_sequence_plan(Plan, SequencePlan) : true <-
cartago.invoke_obj("org.hyperagents.plan.SequencePlan", getAsSequencePlan(Plan), SequencePlan).

+!use_hypermedia_plan(P, ArtId) : true <-
.print("use hypermedia plan");
cartago.invoke_obj("org.hyperagents.hypermedia.HypermediaPlan", getAsHypermediaPlan(P), HypermediaPlan);
.print("hypermedia plan created");
//.print(HypermediaPlan);
useHypermediaPlan(HypermediaPlan)[artifact_id(ArtId)]. //ArtId refers to an HTTPArtifact.


+!use_sequence_plan1(Plan, ArtId) : true <-
?get_sequence_plan(Plan, SequencePlan);
cartago.invoke_obj(SequencePlan, getSequence, Sequence);
cartago.invoke_obj(Sequence, size, N);
.print("sequence length");
.print(N);
-+counter(0);
while (counter(X) & X<N) {
                   .concat("Round : ",X, RoundDisplay);
                   .print(RoundDisplay);
                   cartago.invoke_obj(Sequence, get(X), A);
                   cartago.invoke_obj(A, getFirstPlan, P);
                   !use_hypermedia_plan(P, ArtId);
                   -+counter(X+1);
}.


+!use_sequence_plan(Plan, HTTPArtifact, Maze) : true <-
?get_sequence_plan(Plan, SequencePlan);
.print("print sequence plan");
!print_object(SequencePlan);
cartago.invoke_obj(SequencePlan, getSequence, Sequence);
.print("print sequence");
!print_list(Sequence);
cartago.invoke_obj(Sequence, size, N);
.print("sequence length");
.print(N);
-+counter(0);
while (counter(X) & X<N) {
                   .concat("Round : ",X, RoundDisplay);
                   .print(RoundDisplay);
                   cartago.invoke_obj(Sequence, get(X), P);
                   .print("print affordance plan");
                   !print_object(P);
                   !use_affordance_plan(P, HTTPArtifact, Maze);
                   -+counter(X+1);
}.

+!use_affordance_plan(P, HTTPArtifact, Maze) : true<-
cartago.invoke_obj("org.hyperagents.plan.AffordancePlan", getAsAffordancePlan(P), AffordancePlan);
cartago.invoke_obj(AffordancePlan, getObjective, Objective);
?retrieve_signifiers(Maze, List);
cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
?retrieve_all_signifiers(ListString, HTTPArtifact, SignifierList);
cartago.invoke_obj(SignifierList, toArray, SignifierArray);
?find_affordance(SignifierArray, AffordancePlan, Affordance);
.print("affordance retrieved");
cartago.invoke_obj(Affordance, getFirstPlan, Plan);
.print("print hypermedia plan");
!print_object(Plan);
.print("plan ended");
!print_class(Plan);
.print("plan class printed");
!print_object(P);
cartago.invoke_obj("org.hyperagents.hypermedia.HypermediaPlan", getAsHypermediaPlan(P), HypermediaPlan);
.print("hypermedia plan retrieved");
!print_class(HypermediaPlan);
useHypermediaPlan(HypermediaPlan)[artifact_id(HTTPArtifact)]; //ArtId refers to an HTTPArtifact.
.print("affordance plan used").
//!use_hypermedia_plan(Plan, HTTPArtifact).




+!use_sequence_or_hypermedia_plan(Plan, HTTPArtifact) : true <-
cartago.invoke_obj("maze.Util", isHypermediaPlan(Plan), B);
if (B){
    !use_hypermedia_plan(Plan);
}
else {
    !use_sequence_plan(Plan);
}.