+?retrieve_signifier(SignifierContent, Signifier) : true <-
cartago.invoke_obj("util.FeedbackUtil", getSignifierFromContent(SignifierContent), Signifier).

+?get_first_affordance(Signifier, Affordance) : true <-
cartago.invoke_obj(Signifier, toString, SignifierString);
cartago.invoke_obj(Signifier, getAffordanceList, Affordances);
cartago.invoke_obj(Affordances, get(0), Affordance).