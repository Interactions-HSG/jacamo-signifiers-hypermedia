+?get_thing_artifact(Name, Url, ArtId) : true <-
makeArtifact(Name, "wot.ThingArtifact", [Url, false], ArtId).

+?create_http(name, HTTPArtifact) : true <-
makeArtifact(name, "wot.HTTPArtifact", [], HTTPArtifact).

+?create_signifier_base(Name,SignifierBase): true <-
makeArtifact(Name, "util.SignifierBase",[], SignifierBase).

+?create_hypermedia_exit_builder(Url, ExitBuilder): true <- makeArtifact("exitBuilder", "maze.HypermediaExitBuilderArtifact", [Url], ExitBuilder).

+?create_signifier_builder(SignifierBuilder) : true <- makeArtifact("signifierBuilder", "maze.SignifierBuilderArtifact", [], SignifierBuilder).

+!add_movement(X, M, Y, ExitBuilder) : true <-
addMovement(X, M, Y)[artifact_id(ExitBuilder)].