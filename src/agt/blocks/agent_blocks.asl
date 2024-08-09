!start.

+!start: true <-
    .print("initialize");
    makeArtifact("semArtifact", "sem.SEMArtifact", [], SEMArtifact);
    +sem_artifact(SEMArtifact);
    !read_signifier.



+!read_signifier: sem_artifact(SEMArtifact) <-
 readSignifier(Signifier)[artifact_id(SEMArtifact)];
 !current_action(Signifier).


+!current_action(X): true <-
    .print("do");
    .print(X);
    .nth(0, X, U);
    .nth(1, X, V);
    .print("first block: ", U);
    .print("second block: ", V);
    !move(U, V).

+!basic_move(A,B): sem_artifact(SEMArtifact) <-
    .print("basic move from ", A, " to ", B);
    move(A,B)[artifact_id(SEMArtifact)].

+!free(X): sem_artifact(SEMArtifact) <-
    .print("X: ", X);
    isFree(X, B)[artifact_id(SEMArtifact)];
    .print("B: ", B);
    !conditional_free(X,B).

+!conditional_free(X,B): sem_artifact(SEMArtifact) & B=="true" <-
    .print("is free").

+!conditional_free(X,B): sem_artifact(SEMArtifact) & not (B=="true") <-
    top(X, Y)[artifact_id(SEMArtifact)];
    !move_to_table(Y).

+!move_to_table(A): sem_artifact(SEMArtifact) <-
    isFree(A, B)[artifact_id(SEMArtifact)];
    !move_to_table_free(A,B).

+!move_to_table_free(A, B): sem_artifact(SEMArtifact) & B=="true" <-
    !basic_move(A, "table").

+!move_to_table_free(A, B): sem_artifact(SEMArtifact) &  not (B=="true") <-
    top(A, X)[artifact_id(SEMArtifact)];
    !move_to_table(X);
    !move_to_table(A).

+!move(A,B): sem_artifact(SEMArtifact) <-
    !free(B);
    !free(A);
    !basic_move(A,B).


