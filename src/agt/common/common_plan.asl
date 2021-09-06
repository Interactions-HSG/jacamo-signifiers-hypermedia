+?get_sequence_plan(Plan, SequencePlan) : true <-
cartago.invoke_obj("org.hyperagents.util.SequencePlan", getAsSequencePlan(Plan), SequencePlan).

+!use_hypermedia_plan(P, ArtId) : true <-
cartago.invoke_obj("org.hyperagents.hypermedia.HypermediaPlan", getAsHypermediaPlan(P), HypermediaPlan);
useHypermediaPlan(HypermediaPlan)[artifact_id(ArtId)]. //ArtId refers to an HTTPArtifact.


+!use_sequence_plan(Plan, ArtId,Maze) : true <-
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
                   !print_plan(P);
                   !use_hypermedia_plan(P, ArtId);
                   ?get_current_location(Maze,Room);
                    .print(Room);
                   -+counter(X+1);
}.