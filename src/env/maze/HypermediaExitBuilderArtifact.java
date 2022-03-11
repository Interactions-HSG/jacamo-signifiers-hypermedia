package maze;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.hyperagents.affordance.Affordance;
import org.hyperagents.hypermedia.HypermediaPlan;
import org.hyperagents.ontologies.SignifierOntology;
import org.hyperagents.plan.AffordancePlan;
import org.hyperagents.signifier.Signifier;
import org.hyperagents.plan.Plan;
import org.hyperagents.plan.SequencePlan;
import org.hyperagents.util.ReifiedStatement;
import org.hyperagents.util.State;

import java.util.ArrayList;
import java.util.List;

public class HypermediaExitBuilderArtifact extends Artifact {

    private static ValueFactory rdf = SimpleValueFactory.getInstance();
    private String url;
    private Affordance.Builder affordanceBuilder;
    private SequencePlan.Builder planBuilder;
    List<Plan> sequence;

    public void init(String url){
        this.url = url;
        Resource exitId = rdf.createBNode("exitAffordance");
        affordanceBuilder = new Affordance.Builder(exitId);
        Resource planId = rdf.createBNode("exitPlan");
        planBuilder = new SequencePlan.Builder(planId);
        sequence = new ArrayList<>();
    }

    @OPERATION
    public void addMovement(int room1, int movement, int room2){
        Affordance a = createMovement(room1, movement, room2);
        AffordancePlan affordancePlan = createMovementPlan(room2);
        sequence.add(affordancePlan);
        System.out.println("movement from "+room1+"to "+room2+" added");

    }

    @OPERATION
    public void build(OpFeedbackParam<Object> param){
        planBuilder.addSequence(sequence);
        SequencePlan plan = planBuilder.build();
        //affordanceBuilder.addPlan(plan);
        Affordance affordance = affordanceBuilder.build();
        param.set(affordance);
    }


    @OPERATION
    public void getAffordance(OpFeedbackParam<Object> param){
        planBuilder.addSequence(sequence);
        SequencePlan plan = planBuilder.build();
        //affordanceBuilder.addPlan(plan);
        Affordance affordance = affordanceBuilder.build();
        param.set(affordance);
    }

    @OPERATION
    public void getSignifier(OpFeedbackParam<Signifier> param){
        planBuilder.addSequence(sequence);
        SequencePlan plan = planBuilder.build();
        //affordanceBuilder.addPlan(plan);
        Affordance affordance = affordanceBuilder.build();
        Resource signifierId = rdf.createBNode("exit");
        Signifier signifier = new Signifier.Builder(signifierId)
                .addAffordance(affordance)
                .build();
        System.out.println("signifier created");
        System.out.println(signifier);
        param.set(signifier);
    }

    private Affordance createMovement(int room1, int movement, int room2){

        String s1 = "movement "+movement + " from room "+room1
                + " to room" + room2;
        String s2 = "movement "+movement + " from room "+room1
                + " to room" + room2 + "plan";
        Resource affordanceId = rdf.createBNode(s1);
        Resource movementPlanId = rdf.createBNode(s2);
        String payload = "["+movement+"]";
        HypermediaPlan movementPlan = new HypermediaPlan.Builder(movementPlanId, this.url+"/move", "POST")
                .setPayload(payload)
                .build();
        Affordance a = new Affordance.Builder(affordanceId)
                //.addPlan(movementPlan)
                .build();
        return a;
    }

    private AffordancePlan createMovementPlan(int room2){
        String name = "movementToRoom"+room2+"AffordancePlan";
        Resource affordancePlanId = rdf.createBNode(name);
        State objective = createObjectiveFromRoomNb(room2);
        AffordancePlan affordancePlan = new AffordancePlan(affordancePlanId, objective);
        return affordancePlan;
    }

    public static IRI getIRIFromRoomNb(int room) {
        IRI roomIRI = rdf.createIRI(MazeOntology.room9);
        if (room == 1) {
            roomIRI = rdf.createIRI(MazeOntology.room1);
        } else if (room == 2) {
            roomIRI = rdf.createIRI(MazeOntology.room2);
        } else if (room == 3) {
            roomIRI = rdf.createIRI(MazeOntology.room3);
        } else if (room == 4) {
            roomIRI = rdf.createIRI(MazeOntology.room4);
        } else if (room == 5) {
            roomIRI = rdf.createIRI(MazeOntology.room5);
        } else if (room == 6) {
            roomIRI = rdf.createIRI(MazeOntology.room6);
        } else if (room == 7) {
            roomIRI = rdf.createIRI(MazeOntology.room7);
        } else if (room == 8) {
            roomIRI = rdf.createIRI(MazeOntology.room8);
        }
        return roomIRI;
    }

    public static State createObjectiveFromRoomNb(int room) {
        IRI roomIRI = getIRIFromRoomNb(room);
        ReifiedStatement statement = new ReifiedStatement(rdf.createBNode("goToRoom" + room + "statement"), rdf.createIRI(SignifierOntology.thisAgent), rdf.createIRI(MazeOntology.isIn), roomIRI);
        Resource stateId = rdf.createBNode("goToRoom" + room);
        State objective = new State.Builder(stateId)
                .addStatement(statement)
                .build();
        return objective;
    }
}
