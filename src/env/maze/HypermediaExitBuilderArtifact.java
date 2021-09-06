package maze;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.hyperagents.affordance.Affordance;
import org.hyperagents.hypermedia.HypermediaPlan;
import org.hyperagents.util.Plan;
import org.hyperagents.util.SequencePlan;

import java.util.ArrayList;
import java.util.List;

public class HypermediaExitBuilderArtifact extends Artifact {

    private ValueFactory rdf;
    private String url;
    private Affordance.Builder affordanceBuilder;
    private SequencePlan.Builder planBuilder;
    List<Affordance> sequence;

    public void init(String url){
        rdf = SimpleValueFactory.getInstance();
        this.url = url;
        Resource exitId = rdf.createBNode("exit");
        affordanceBuilder = new Affordance.Builder(exitId);
        Resource planId = rdf.createBNode("exitPlan");
        planBuilder = new SequencePlan.Builder(planId);
        sequence = new ArrayList<>();
    }

    @OPERATION
    public void addMovement(int room1, int movement, int room2){
        Affordance a = createMovement(room1, movement, room2);
        sequence.add(a);

    }

    @OPERATION
    public void build(OpFeedbackParam<Object> param){
        planBuilder.addSequence(sequence);
        Plan plan = planBuilder.build();
        affordanceBuilder.addPlan(plan);
        Affordance affordance = affordanceBuilder.build();
        param.set(affordance);
    }


    @OPERATION
    public void getAffordance(OpFeedbackParam<Object> param){
        planBuilder.addSequence(sequence);
        Plan plan = planBuilder.build();
        affordanceBuilder.addPlan(plan);
        Affordance affordance = affordanceBuilder.build();
        param.set(affordance);
    }

    private Affordance createMovement(int room1, int movement, int room2){

        String s1 = "movement "+movement + " from room "+room1
                + " to room" + room2;
        String s2 = "movement "+movement + " from room "+room1
                + " to room" + room2 + "plan";
        Resource affordanceId = rdf.createBNode(s1);
        Resource movementPlanId = rdf.createBNode(s2);
        String payload = "["+movement+",0]";
        Plan movementPlan = new HypermediaPlan.Builder(movementPlanId, this.url+"/move", "POST")
                .setPayload(payload)
                .build();
        Affordance a = new Affordance.Builder(affordanceId)
                .addPlan(movementPlan)
                .build();
        return a;
    }
}
