+!send_request_payload(Url, Payload, HTTPArtifact) : true <-
sendStandardRequestPayload(Url, Payload)[artifact_id(HTTPArtifact)].

+?retrieve_signifiers(ArtId, Signifiers) : true <-
invokeActionReturn("http://example.org/retrieve",[], Signifiers)[artifact_id(ArtId)].

+?retrieve_signifiers2(Url, HTTPArtifact, Signifiers) : true <-
retrieveSignifiers(Url, Signifiers)[artifact_id(HTTPArtifact)].

+?retrieve_content(Url, HttpArtId, Content) : true <-
retrieveContentUrl(Url, Content)[artifact_id(HttpArtId)].

+?retrieve_all_contents(List, HTTPArtifact, ContentList) : true <-
cartago.new_obj("java.util.ArrayList", [], ContentList);
cartago.invoke_obj(List, size, Size);
for (.range(I, 0, Size-1)){
    cartago.invoke_obj(List, get(I), Url);
    ?retrieve_content(Url, HTTPArtifact, Content);
    cartago.invoke_obj(ContentList, add(Content));

}.


+?get_all_signifiers1(ContentList, SignifierList) : true <-
cartago.invoke_obj(ContentList, getClass, Class);
cartago.invoke_obj(Class, toString, ClassString);
.print(ClassString);
cartago.invoke_obj(ContentList, get(0), Content);
cartago.invoke_obj(Content, getClass, Class);
cartago.invoke_obj(Class, toString, ClassString);
.print(ClassString);
cartago.invoke_obj("util.FeedbackUtil", getAllSignifiers(ContentList), SignifierList).

+?get_all_signifiers(ContentList, SignifierList) : true <-
cartago.new_obj("java.util.ArrayList", [], SignifierList);
cartago.invoke_obj(ContentList, size, Size);
for (.range(I, 0, Size-1)){
    cartago.invoke_obj(ContentList, get(I), Content);
    ?retrieve_signifier(Content, Signifier);
    cartago.invoke_obj(SignifierList, add(Signifier));

}.


+?retrieve_all_signifiers(List, HTTPArtifact, SignifierList) : true <-
?retrieve_all_contents(List, HTTPArtifact, ContentList);
?get_all_signifiers(ContentList, SignifierList).

+?retrieve_signifiers_array(List, HTTPArtifact, SignifierArray) : true <-
?retrieve_all_contents(List, HTTPArtifact, ContentList);
?get_all_signifiers(ContentList, SignifierList);
cartago.invoke_obj(SignifierList, toArray, SignifierArray).

+?find_affordance(SignifierList, AffordancePlan, Affordance) : true <-
cartago.invoke_obj("util.FeedbackUtil", findAffordance(SignifierList, AffordancePlan), Affordance).

+?find_sequence_plan1(SignifierList, Plan): true <-
cartago.invoke_obj(SignifierList, size, Size);
for (.range(I, 0, Size-1)){
    cartago.invoke_obj(SignifierList, get(I), Signifier);
    !print_object(Signifier);
    cartago.invoke_obj("util.FeedbackUtil", hasSequencePlan(Signifier), B);
    if (B){
    .print("plan found");
    cartago.invoke_obj("util.FeedbackUtil", getSequencePlan(Signifier), Plan);
    }

}.

+?find_sequence_plan(SignifierArray, Plan): true <-
cartago.invoke_obj("util.FeedbackUtil", findSequencePlan(SignifierArray), Plan).

+?find_sequence_plan_signifiers(SignifierArray, SignifierList): true <-
cartago.invoke_obj("util.FeedbackUtil", findSequencePlanSignifiers(SignifierArray), SignifierList).

+?create_profile(Url, Name, HTTPArtifact, ProfileUrl) : true <-
createProfileArtifact(Url, Name, ProfileUrl)[artifact_id(HTTPArtifact)].


+!register_profile(ProfileUri, ArtId) : true <-
invokeAction("http://example.org/registerProfile", [ProfileUri, 0])[artifact_id(ArtId)].

+!register_profile2(ProfileUri, SignifierArtifactUri, HTTPArtifact) : true <-
registerProfile(ProfileUri, SignifierArtifactUri)[artifact_id(HTTPArtifact)].

+!add_signifier(Signifier, ArtId) : true <-
cartago.invoke_obj("util.FeedbackUtil", getContent(Signifier), Content);
invokeAction("http://example.org/add", [Content, 0])[artifact_id(ArtId)].