
+?get_maze(Maze) : true <- lookupArtifact("maze", Maze).

+?get0(List, V) : true <- cartago.invoke_obj(List, get(0), V).

+?get1(List, V) : true <- cartago.invoke_obj(List, get(1), V).

+?get(List, Index, V) : true <- cartago.invoke_obj(List, get(Index), V).

+?get_size(List, Size) : true <- cartago.invoke_obj(List, size, Size).

+?get_movement(Room1, Room2, M) : true <-
cartago.invoke_obj("maze.Util", getGeneralDirection(Room1, Room2), M).

+?create_empty_list(JasonList) : true <-
.concat([],[], JasonList);
.print(JasonList).

+?to_jason_list(List, JasonList) : true <-
?create_empty_list(JasonList);
?get_size(List, Size);
for (.range(I, 0, Size-1)){
    ?get(List, I, E);
    !print_class(E);
    .concat(JasonList, [E], JasonList);
}.

+?count(List, C): true <-
.print(List);
cartago.invoke_obj(List, size, C).

/*+?count(List, C):
cartago.invoke_obj("util.FeedbackUtil", count(List), C).*/

+!add_number_signifiers(List): true <-
?signifiers_received(X);
?count(List, C);
.print("number of signifiers in list:");
.print(C);
-+signifiers_received(X+C);
?signifiers_received(X2);
.print(X2);
.print("end add").

{ include("common_affordance.asl") }
{ include("common_artifacts.asl") }
{ include("common_beliefs.asl") }
{ include("common_hypermedia.asl") }
{ include("common_maze.asl") }
{ include("common_plan.asl") }
{ include("common_action.asl") }
{ include("common_signifier.asl") }
{ include("common_state.asl") }
{ include("common_print.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }

