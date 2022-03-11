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

+!print_class(Object) : true <-
cartago.invoke_obj(Object, getClass, ObjectClass);
cartago.invoke_obj(ObjectClass, toString, ClassString);
.print(ClassString).

+!print_list(List) : true <-
cartago.invoke_obj(List, size, N);
for (.range(I, 0,N-1)){
cartago.invoke_obj(List, get(I), E);
.print(I);
!print_class(E);
cartago.invoke_obj(E, getId, Id);
cartago.invoke_obj(Id, toString, IdString);
.print(IdString);
.print("description object");
!print_object(E);
}.
