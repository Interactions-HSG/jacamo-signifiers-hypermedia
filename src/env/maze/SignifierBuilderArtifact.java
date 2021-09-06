package maze;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.hyperagents.affordance.Affordance;
import org.hyperagents.signifier.Signifier;

public class SignifierBuilderArtifact extends Artifact {
    private ValueFactory rdf;

    private Signifier.Builder builder;


    public void init(){
        rdf = SimpleValueFactory.getInstance();
        Resource signifierId = rdf.createBNode();
        builder = new Signifier.Builder(signifierId);
    }

    @OPERATION
    public void restart(){
        Resource signifierId = rdf.createBNode();
        builder = new Signifier.Builder(signifierId);

    }

    @OPERATION
    public void restart(Resource signifierId){
        builder = new Signifier.Builder(signifierId);
    }

    @OPERATION
    public void addModel(Model m){
        builder.add(m);
    }

    @OPERATION
    public void addAffordance(Affordance a){
        builder.addAffordance(a);
    }

    @OPERATION
    public void getSignifier(OpFeedbackParam<Object> param){
        Signifier signifier = builder.build();
        param.set(signifier);
        restart();
    }
}
