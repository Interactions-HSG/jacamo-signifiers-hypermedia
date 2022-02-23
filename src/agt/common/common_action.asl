+!use_hypermedia_action(Action, ArtId) : true <-
.print("use hypermedia action");
cartago.invoke_obj("org.hyperagents.hypermedia.HypermediaAction", getAsHypermediaAction(Action), HypermediaAction);
.print("hypermedia action created");
//.print(HypermediaAction);
useHypermediaAction(HypermediaAction)[artifact_id(ArtId)]. //ArtId refers to an HTTPArtifact.