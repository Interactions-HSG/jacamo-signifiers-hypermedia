+!print_plan(Plan) : true <-
cartago.invoke_obj(Plan, getClass, PlanClass);
cartago.invoke_obj(PlanClass, toString, ClassString);
.print(ClassString);
cartago.invoke_obj(Plan, toString, PlanContent);
.print(PlanContent).

+!print_signifier(Signifier) : true <-
cartago.invoke_obj(Signifier, toString, SignifierString);
.print(SignifierString).

+!print_object(Object) : true <-
cartago.invoke_obj(Object, toString, String);
.print(String).