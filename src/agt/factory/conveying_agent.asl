workshop_url("http://localhost:8080/environments/61/workspaces/102/artifacts/workshop").

!start.


+!start : workshop_url(Url) <- ?get_thing_artifact("workshop", Url, Workshop);
                                 makeArtifact("httpArtifact", "wot.HTTPArtifact", [], HTTPArtifact);
                                !run.

+!run <- !action;
         !run.

+!action : true <- ?retrieve_signifiers(Workshop, List);
                    .print("list received");
                    cartago.invoke_obj("util.FeedbackUtil", getListString(List), ListString);
                    .print(ListString);
                   ?get0(ListString, SignifierUrl);
                   .print(SignifierUrl);
                   ?retrieve_content(SignifierUrl, HTTPArtifact, SignifierContent);
                   .print(SignifierContent);
                   ?retrieve_signifier(SignifierContent, Signifier);
                   ?get_first_affordance(Signifier, Affordance);
                   ?get_first_plan(Affordance, Plan);
                   !use_hypermedia_plan(Plan, HTTPArtifact);
                   .print("action done").

{ include("../common/common.asl") }