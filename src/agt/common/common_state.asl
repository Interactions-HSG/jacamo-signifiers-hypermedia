+?get_statements(State, Statements) : true <-
cartago.invoke_obj(State, getStatementList, Statements).

+?get_first_statement(State, Statement): true <-
?get_statements(State, Statements);
cartago.invoke_obj(Statements, get(0), Statement).


+?get_subject(Statement, Subject) : true <-
cartago.invoke_obj(Statement, getSubject, Subject).

+?get_predicate(Statement, Predicate) : true <-
cartago.invoke_obj(Statement, getPredicate, Predicate).

+?get_object(Statement, Object) : true <-
cartago.invoke_obj(Statement, getObject, Object).

+?get_string_value(Value, StringValue) : true <-
cartago.invoke_obj(Value, stringValue, StringValue).

+!print_value(Value): true <-
cartago.invoke_obj(Value, stringValue, StringValue);
.print(StringValue).

+?get_int_value(Value, IntValue) : true <-
cartago.invoke_obj("util.RDFProcessor", getIntFromValue(Value), IntValue).

+?get_length(String, L) : true <-
cartago.invoke_obj(String, length, L).

+?to_string(Object, String) : true <-
cartago.invoke_obj(Object, toString, String).

+?get_last_char_as_int(Value, IntValue) : true <-
?get_string_value(Value, StringValue);
?to_string(StringValue, String);
.print(String);
cartago.invoke_obj(String, charAt(0), C);
.print(C);
?get_length(StringValue, N);
.print(N);
cartago.invoke_obj(StringValue, substring(N-1), IntString);
cartago.invoke_obj("java.lang.Integer", parseInt(IntString), IntValue).

+?get_last_int(Value, IntValue) : true <-
cartago.invoke_obj("util.RDFProcessor", getLastInt(Value), IntValue).


