+?get_first_plan(Affordance, Plan) : true <-
cartago.invoke_obj(Affordance, getFirstPlan, Plan).

+?get_precondition(Affordance, Precondition) : true <-
cartago.invoke_obj(Affordance, getPrecondition, OpPrecondition);
cartago.invoke_obj(OpPrecondition, get, Precondition).
