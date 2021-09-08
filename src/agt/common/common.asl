
+?get_maze(Maze) : true <- lookupArtifact("maze", Maze).

+?get0(List, V) : true <- cartago.invoke_obj(List, get(0), V).

+?get1(List, V) : true <- cartago.invoke_obj(List, get(1), V).

+?get(List, Index, V) : true <- cartago.invoke_obj(List, get(Index), V).

+?get_size(List, Size) : true <- cartago.invoke_obj(List, size, Size).

+?get_movement(Room1, Room2, M) : true <-
cartago.invoke_obj("maze.Util", getGeneralDirection(Room1, Room2), M).

{ include("common_affordance.asl") }
{ include("common_artifacts.asl") }
{ include("common_beliefs.asl") }
{ include("common_hypermedia.asl") }
{ include("common_maze.asl") }
{ include("common_plan.asl") }
{ include("common_signifier.asl") }
{ include("common_state.asl") }
{ include("common_print.asl") }
{ include("$jacamoJar/templates/common-cartago.asl") }

