+!send_request_payload(Url, Payload, HTTPArtifact) : true <-
sendStandardRequestPayload(Url, Payload)[artifact_id(HTTPArtifact)].

+?retrieve_signifiers(ArtId, Signifiers) : true <-
invokeActionReturn("http://example.org/retrieve",[], Signifiers)[artifact_id(ArtId)].

+?retrieve_signifiers2(Url, HTTPArtifact, Signifiers) : true <-
retrieveSignifiers(Url, Signifiers)[artifact_id(HTTPArtifact)].

+?retrieve_content(Url, HttpArtId, Content) : true <-
retrieveContentUrl(Url, Content)[artifact_id(HttpArtId)].

+?retrieve_all_contents(List, HTTPArtifact, ContentList) : true <-
cartago.invoke_obj("java.util.ArrayList", [], ContentList);
cartago.invoke_obj(List, size, Size);
for (.range(I, 0, Size)){
    cartago?invoke_obj(List, get(I), Url);
    ?retrieveContent(Url, HTTPArtifact, Content);
    cartago.invoke_obj(ContentList, add(Content));

}


+?get_all_signifiers(ContentList, SignifierList) : true <-
cartago.invoke_obj("util.FeedbackUtil", getAllSignifiers(ContentList), SignifierList).

+?find_affordance(SignifierList, AffordancePlan, Affordance) : true <-
cartago.invoke_obj("util.FeedbackUtil", findAffordance(SignifierList, AffordancePlan), Affordance).

+?create_profile(Url, Name, HTTPArtifact, ProfileUrl) : true <-
createProfileArtifact(Url, Name, ProfileUrl)[artifact_id(HTTPArtifact)].


+!register_profile(ProfileUri, ArtId) : true <-
invokeAction("http://example.org/registerProfile", [ProfileUri, 0])[artifact_id(ArtId)].

+!register_profile2(ProfileUri, SignifierArtifactUri, HTTPArtifact) : true <-
registerProfile(ProfileUri, SignifierArtifactUri)[artifact_id(HTTPArtifact)].

+!add_signifier(Signifier, ArtId) : true <-
cartago.invoke_obj("util.FeedbackUtil", getContent(Signifier), Content);
invokeAction("http://example.org/add", [Content, 0])[artifact_id(ArtId)].