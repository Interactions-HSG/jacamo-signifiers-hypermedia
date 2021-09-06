+!print_plan(Plan) : true <-
cartago.invoke_obj(Plan, getClass, PlanClass);
cartago.invoke_obj(PlanClass, toString, ClassString);
.print(ClassString);
cartago.invoke_obj(Plan, toString, PlanContent);
.print(PlanContent).