maze_url("http://localhost:8080/environments/61/workspaces/102/artifacts/maze5").

!start.


+!start : maze_url(Url) <- .print("start");
                ?get_thing_artifact("maze", Url, Maze);
                makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                ?create_signifier_builder(SignifierBuilder);
                ?create_hypermedia_exit_builder(Url, ExitBuilder);
                !register(1, Url, HTTPArtifact);
                +current_location(1);
                while (current_location(V) & V < 9){
                ?get_current_location(Maze, X);
                ?get_movement(X, 9, M);
                !move(M, Url, HTTPArtifact);
                ?get_current_location(Maze, Y);
                .concat("current room : ",Y,String);
                .print(String);
                !add_movement(X, M, Y, ExitBuilder);
                -+current_location(Y);
                };
                getSignifier(Signifier)[artifact_id(ExitBuilder)];
                .print("print signifier");
                !print_object(Signifier);
                .print("signifier printed");
                !add_signifier(Signifier, Maze);
                .print("signifier sent");
                .wait(1000);
                .send(second_agent_maze, achieve, start);
                .print("end first agent").



{ include("../common/common.asl") }