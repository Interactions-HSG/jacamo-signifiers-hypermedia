+?get_sequence_plan(Plan, SequencePlan) : true <-
cartago.invoke_obj("org.hyperagents.plan.SequencePlan", getAsSequencePlan(Plan), SequencePlan).

+!use_hypermedia_plan(P, ArtId) : true <-
cartago.invoke_obj("org.hyperagents.hypermedia.HypermediaPlan", getAsHypermediaPlan(P), HypermediaPlan);
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
cartago.invoke_obj(SequencePlan, getSequence, Sequence);
cartago.invoke_obj(Sequence, size, N);
.print("sequence length");
.print(N);
-+counter(0);
while (counter(X) & X<N) {
                   .concat("Round : ",X, RoundDisplay);
                   .print(RoundDisplay);
                   cartago.invoke_obj(Sequence, get(X), P);
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
.print(Affordance);
cartago.invoke_obj(Affordance, getFirstPlan, Plan);
//cartago.invoke_obj("util.FeedbackUtil", getPlanFromDirectPlan(DirectPlan), Plan);
//.print("direct plan to plan");
!use_hypermedia_plan(Plan).




+!use_sequence_or_hypermedia_plan(Plan, HTTPArtifact) : true <-
cartago.invoke_obj("maze.Util", isHypermediaPlan(Plan), B);
if (B){
    !use_hypermedia_plan(Plan);
}
else {
    !use_sequence_plan(Plan);
}.