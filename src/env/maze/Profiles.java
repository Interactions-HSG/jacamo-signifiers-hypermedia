package maze;

import org.eclipse.rdf4j.model.Resource;
import org.hyperagents.ontologies.SignifierOntology;
import org.hyperagents.util.RDFS;
import org.hyperagents.util.ReifiedStatement;
import org.hyperagents.util.State;


public class Profiles {

    public static State getCurrentSituation3(){
        Resource stateId = RDFS.rdf.createBNode();
        State.Builder builder = new State.Builder(stateId);
        Resource statementId = RDFS.rdf.createBNode();
        ReifiedStatement s = new ReifiedStatement(statementId, RDFS.rdf.createIRI(SignifierOntology.thisAgent), RDFS.rdf.createIRI(MazeOntology.isIn), RDFS.rdf.createIRI(MazeOntology.room4));
        builder.addStatement(s);
        return builder.build();
    }

    public static State getPurpose3(){
        Resource stateId = RDFS.rdf.createBNode();
        State.Builder builder = new State.Builder(stateId);
        Resource statementId = RDFS.rdf.createBNode();
        ReifiedStatement s = new ReifiedStatement(statementId, RDFS.rdf.createIRI(SignifierOntology.thisAgent), RDFS.rdf.createIRI(MazeOntology.isIn), RDFS.rdf.createIRI(MazeOntology.room4));
        builder.addStatement(s);
        return builder.build();
    }

    public static State getPurpose4(){
        Resource stateId = RDFS.rdf.createBNode();
        State.Builder builder = new State.Builder(stateId);
        Resource statementId = RDFS.rdf.createBNode();
        Resource statement1Id = RDFS.rdf.createBNode();
        ReifiedStatement s1 = new ReifiedStatement(statement1Id,RDFS.rdf.createIRI(SignifierOntology.thisAgent), RDFS.rdf.createIRI(MazeOntology.hasIntermediaryGoal), RDFS.rdf.createIRI(MazeOntology.room4));
        builder.addStatement(s1);
        Resource statement2Id = RDFS.rdf.createBNode();
        ReifiedStatement s2 = new ReifiedStatement(statement2Id,RDFS.rdf.createIRI(SignifierOntology.thisAgent), RDFS.rdf.createIRI(MazeOntology.hasFinalGoal), RDFS.rdf.createIRI(MazeOntology.room9));
        builder.addStatement(s2);
        return builder.build();

    }

    public static State getPurposeArticle(){
        Resource stateId = RDFS.rdf.createBNode();
        State.Builder builder = new State.Builder(stateId);
        Resource statementId = RDFS.rdf.createBNode();
        ReifiedStatement s = new ReifiedStatement(statementId, RDFS.rdf.createIRI(SignifierOntology.thisAgent), RDFS.rdf.createIRI(MazeOntology.isIn), RDFS.rdf.createIRI(MazeOntology.room9));
        builder.addStatement(s);
        return builder.build();
    }
}
