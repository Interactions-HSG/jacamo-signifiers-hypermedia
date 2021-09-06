package util;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import org.hyperagents.signifier.Signifier;

import java.util.Hashtable;
import java.util.Map;

public class SignifierBase extends Artifact {

    private Map<String, Signifier> signifiers;

    public void init(){
        this.signifiers = new Hashtable<>();
    }

    @OPERATION
    public void addSignifier(String name, Signifier signifier){
        this.signifiers.put(name, signifier);
    }

    @OPERATION
    public void retrieveSignifier(String name, OpFeedbackParam<Signifier> returnParam){
        Signifier signifier = this.signifiers.get(name);
        returnParam.set(signifier);
    }
}
