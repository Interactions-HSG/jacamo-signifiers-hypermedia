package util;

import ch.unisg.ics.interactions.wot.td.affordances.Form;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpRequest;
import ch.unisg.ics.interactions.wot.td.vocabularies.TD;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.hyperagents.affordance.Affordance;
import org.hyperagents.ontologies.SignifierOntology;
import org.hyperagents.plan.AffordancePlan;
import org.hyperagents.signifier.Signifier;
import org.hyperagents.plan.Plan;
import org.hyperagents.util.RDFS;

import javax.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FeedbackUtil {

    public static String getString(Optional<Object> opObj){
        Object obj = opObj.get();
        String str = obj.toString();
        return str;
    }

    public static List<String> getListString(Optional<Object> opObj){
        Object obj = opObj.get();
        String str = obj.toString();
        List<String> strList = Arrays.asList(str.split(","));
        int n = strList.size();
        List<String> newStrList = new ArrayList<>();
        for (int i = 0; i<n; i++){
            String s = strList.get(i);
            int l = s.length();
            if (n>1) {
                if (i == 0 && s.charAt(0) == '[') {
                    String s2 = s.substring(1);
                    newStrList.add(s2);
                } else if (i == n - 1 && s.charAt(l - 1) == ']') {
                    String s2 = s.substring(0, l - 1);
                    newStrList.add(s2);
                } else {
                    newStrList.add(s);
                }
            }
            else {
                String s2 = s;
                if (s.charAt(0) == '['){
                    s2 = s.substring(1);
                }
                String s3 = s2;
                if (s2.charAt(s2.length()-1)==']'){
                    s3 = s2.substring(0, s2.length()-1);

                }
                newStrList.add(s3);
            }
        }
        return newStrList;
    }

    public static Model retrieveModel(String content){
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE);
        Model model = new ModelBuilder().build();
        RDFHandler handler = new StatementCollector(model);
        parser.setRDFHandler(handler);
        InputStream stream = new ByteArrayInputStream(content.getBytes());
        try {
            parser.parse(stream);
        } catch(Exception e){
            e.printStackTrace();
        }
        return model;

    }

    public static Signifier retrieveSignifier(String signifierUrl, String content){
        Resource signifierId = RDFS.rdf.createIRI(signifierUrl);
        Model model = retrieveModel(content);
        Signifier signifier = Signifier.readSignifier(signifierId, model);
        return signifier;
    }

    public static Signifier getSignifierFromContent(String content){
        Signifier signifier = null;
        Model model = retrieveModel(content);
        Optional<Resource> opSignifierId = Models.subject(model.filter(null, RDF.TYPE, RDFS.rdf.createIRI(SignifierOntology.Signifier)));
        if (opSignifierId.isPresent()){
            Resource signifierId = opSignifierId.get();
            signifier = Signifier.readSignifier(signifierId, model);
        }
        return signifier;
    }

    public static Object[] getParametersFromPayload(String payload){
        Reader reader = new CharArrayReader(payload.toCharArray());
        JsonReader jsonReader = Json.createReader(reader);
        JsonArray array = jsonReader.readArray();
        List<Object> parameterList = new ArrayList<>();
        for (int i = 0; i<array.size(); i++){
            int a = array.getInt(i);
            parameterList.add(a);

        }
        return parameterList.toArray();
    }

    public static String getStringPlan(Plan p){
        Model model = p.getModel();
        RDFFormat format = RDFFormat.TURTLE;
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        Rio.write(model,output,format);
        return output.toString();

    }

    public static int getStringAsInt(String s){
        int n = Integer.parseInt(s);
        return n;
    }

    public static String getContent(Signifier signifier){
        return signifier.getTextTriples(RDFFormat.TURTLE);
    }

    public static String getIntListAsString(List<Integer> list){
        return list.toString();
    }

    public static void printModel(Model m){
        ByteArrayOutputStream output=new ByteArrayOutputStream();
        Rio.write(m,output,RDFFormat.TURTLE);
        System.out.println(output.toString());

    }

    public static List<Signifier> getAllSignifiers(List<String> contentList){
        List<Signifier> signifiers = new ArrayList<>();
        for (int i =0; i<contentList.size(); i++){
            Signifier s = getSignifierFromContent(contentList.get(i));
            signifiers.add(s);
        }
        return signifiers;
    }

    public static Affordance findAffordance(List<Signifier> signifiers, AffordancePlan affordancePlan){
        int n = signifiers.size();
        for (int i=0; i<n;i++){
            Signifier s = signifiers.get(i);
            List<Affordance> affordances = s.getAffordanceList();
            for (Affordance affordance : affordances){
                if (affordancePlan.satisfyPlan(affordance)){
                    return affordance;
                }
            }
        }
        return null;
    }

}
