+!send_request_payload(Url, Payload, HTTPArtifact) : true <-
sendStandardRequestPayload(Url, Payload)[artifact_id(HTTPArtifact)].

+?retrieve_signifiers(ArtId, Signifiers) : true <-
invokeActionReturn("http://example.org/retrieve",[], Signifiers)[artifact_id(ArtId)].

+?retrieve_signifiers2(Url, HTTPArtifact, Signifiers) : true <-
retrieveSignifiers(Url, Signifiers)[artifact_id(HTTPArtifact)].

+? retrieve_content(Url, HttpArtId, Content) : true <-
retrieveContentUrl(Url, Content)[artifact_id(HttpArtId)].

+?create_profile(Url, Name, HTTPArtifact, ProfileUrl) : true <-
createProfileArtifact(Url, Name, ProfileUrl)[artifact_id(HTTPArtifact)].


+!register_profile(ProfileUri, ArtId) : true <-
invokeAction("http://example.org/registerProfile", [ProfileUri, 0])[artifact_id(ArtId)].

+!register_profile2(ProfileUri, SignifierArtifactUri, HTTPArtifact) : true <-
registerProfile(ProfileUri, SignifierArtifactUri)[artifact_id(HTTPArtifact)].

+!add_signifier(Signifier, ArtId) : true <-
cartago.invoke_obj("util.FeedbackUtil", getContent(Signifier), Content);
invokeAction("http://example.org/add", [Content, 0])[artifact_id(ArtId)].