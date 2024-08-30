package sem;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import jason.asSyntax.*;
import jason.asSyntax.Literal;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


public class SEMArtifact extends Artifact {

    @OPERATION
    public void readSignifier1(OpFeedbackParam<ListTerm> signifierParam){
        try {
            URL url = new URL("http://localhost:5000/signifiers/?name=bdi_agent");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == 200) {
                String response = readResponse(connection);
                ListTerm l = new ListTermImpl();
                Model m = parseTurtleString(response);
                ValueFactory rdf = SimpleValueFactory.getInstance();
                IRI signifierTypeIRI = rdf.createIRI("https://purl.org/hmas/Signifier");
                Resource signifierId = null;
                for (Statement s : m.getStatements(null, RDF.TYPE, signifierTypeIRI)) {
                    signifierId = s.getSubject();
                }
                Resource actionId = null;
                for (Statement s2 : m.getStatements(signifierId, rdf.createIRI("https://purl.org/hmas/signifies"), null)) {
                    Value v = s2.getObject();
                    if (v.isResource()) {
                        actionId = (Resource) v;
                    }
                }
                Resource listId = null;
                for (Statement s3 : m.getStatements(actionId, rdf.createIRI("http://example.org/hasParams"), null)) {
                    Value v = s3.getObject();
                    if (v.isResource()) {
                        listId = (Resource) v;
                    }
                }
                List<Value> lv = parseRDFList(m, listId);

                //List<String> li = getListFromId(m, listId);
                List<String> li = lv.stream().map(Object::toString).collect(Collectors.toList());
                for (String str: li){
                    String new_str = str.replace("\"", "");
                    l.add(new StringTermImpl(new_str));
                }
                signifierParam.set(l);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @OPERATION
    public void readSignifier(OpFeedbackParam<ListTerm> signifierParam) throws Exception {
        ListTerm lt = null;
        List<Literal> signifiers = getSignifiers();
        boolean b = true;
        for (Literal s: signifiers){
            if (s.getFunctor().equals("position") && b){ //TODO: check
                System.out.println("signifier: "+s);
                b = false;
                List<Term> terms = s.getTerms();
                lt = new ListTermImpl();
                lt.addAll(terms);
                this.defineObsProperty("position", terms);


            }
        }
        if (lt==null){
            throw new Exception();
        }
        signifierParam.set(lt);

    }



    public List<Literal> getSignifiers(){
        List<Literal> signifiers = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:5000/signifiers/?name=bdi_agent");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code == 200) {
                String response = readResponse(connection);
                ListTerm l = new ListTermImpl();
                Model m = parseTurtleString(response);
                ValueFactory rdf = SimpleValueFactory.getInstance();
                IRI signifierTypeIRI = rdf.createIRI("https://purl.org/hmas/Signifier");
                List<Resource> signifierIds = new ArrayList<>();
                for (Statement s : m.getStatements(null, RDF.TYPE, signifierTypeIRI)) {
                    signifierIds.add(s.getSubject());
                }
                for (Resource signifierId: signifierIds) {
                    Literal signifierLiteral = null;
                    Resource actionId = null;
                    for (Statement s2 : m.getStatements(signifierId, rdf.createIRI("https://purl.org/hmas/signifies"), null)) {
                        Value v = s2.getObject();
                        if (v.isResource()) {
                            actionId = (Resource) v;
                        }
                    }
                    for (Statement s4: m.getStatements(actionId, rdf.createIRI("http://example.org/hasPredicate"), null)){
                        Value v = s4.getObject();
                        if (v.isLiteral()){
                            signifierLiteral = new LiteralImpl(v.stringValue());
                        }
                    }
                    Resource listId = null;
                    for (Statement s3 : m.getStatements(actionId, rdf.createIRI("http://example.org/hasParams"), null)) {
                        Value v = s3.getObject();
                        if (v.isResource()) {
                            listId = (Resource) v;
                        }
                    }
                    List<Value> lv = parseRDFList(m, listId);

                    //List<String> li = getListFromId(m, listId);
                    List<String> li = lv.stream().map(Object::toString).collect(Collectors.toList());
                    for (String str : li) {
                        String new_str = str.replace("\"", "");
                        l.add(new StringTermImpl(new_str));
                        assert signifierLiteral != null;
                        signifierLiteral.addTerm(new StringTermImpl(new_str));
                    }
                    signifiers.add(signifierLiteral);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("all signifiers: "+signifiers);
    return signifiers;
    }

    public List<String> getListFromId(Model m, Resource id){ //TODO: update
        List<String> l = new ArrayList<>();
        l.add("A");
        l.add("B");
        return l;
    }


    public static List<Value> parseRDFList(Model model, Resource listIdentifier) {
        List<Value> items = new ArrayList<>();
        Resource current = listIdentifier;

        while (!current.equals(RDF.NIL)) {
            // Find the rdf:first value
            Value first = Models.object(model.filter(current, RDF.FIRST, null)).orElse(null);
            if (first != null) {
                items.add(first);
            }

            // Find the rdf:rest value
            Value rest = Models.object(model.filter(current, RDF.REST, null)).orElse(null);
            if (rest instanceof Resource) {
                current = (Resource) rest;
            } else {
                break;
            }
        }

        return items;
    }

    public Model parseTurtleString(String turtleString){
        RDFParser rdfParser = Rio.createParser(RDFFormat.TURTLE);
        Model model = new LinkedHashModel();
        rdfParser.setRDFHandler(new StatementCollector(model));
        InputStream in = new ByteArrayInputStream(turtleString.getBytes(StandardCharsets.UTF_8));
        try {
            rdfParser.parse(in);
        } catch (Exception e){
            e.printStackTrace();
        }
        return model;
    }

    public String readResponse(HttpURLConnection connection){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Return the response
            return response.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    @OPERATION
    public void move(String a, String b){
        try {
            System.out.println("move from: "+a+ " to "+b);
            //String new_a=a.replace("object_", "");
            //String new_b=b.replace("object_", "");
            URL url = new URL("http://localhost:5000/env");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            String jsonInputString = "{\"from_block\":\""+a+"\",\"to_block\":\""+b+"\"}";
            System.out.println("json input string: "+ jsonInputString);
            // Write the JSON input string to the output stream
            /*try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }*/
            Map<String, String> headers = new Hashtable<>();
            headers.put("Content-Type", "application/json");
            sendRequestPayload(url.toString(), "POST", headers, jsonInputString);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendRequestPayload(String urlString, String method, Map<String, String> headers, String payload){
        System.out.println("url string: " + urlString);
        BasicClassicHttpRequest request = new BasicClassicHttpRequest(method, urlString);
        for (String key : headers.keySet()){
            String value = headers.get(key);
            request.addHeader(key, value);
        }
        request.setEntity(new StringEntity(payload));
        HttpClient client = HttpClients.createDefault();
        try {
            client.execute(request);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @OPERATION
    public void isFree1(String a, OpFeedbackParam<String> b){
        try {
            URL url = new URL("http://localhost:5000/env/free/?block=" + a);
            System.out.println(url.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String str = readResponse(connection);
            System.out.println("is free: " +str);
            if (Objects.equals(str, "True")){
                b.set("true");
            } else{
                b.set("false");
            }
        } catch (Exception e){
            e.printStackTrace();
            b.set("false");
        }
        
    }

    @OPERATION
    public void isFree(String a, OpFeedbackParam<String> b){
        String bValue = "false";
        List<Literal> signifiers = getSignifiers();
        for (Literal signifier: signifiers){
            System.out.println("signifier to check: "+ signifier);
            String functor = signifier.getFunctor();
            if (functor.equals("isFree")){
                List<Term> terms = signifier.getTerms();
                if (terms.size()>=2){
                    Term blockTerm = terms.get(0);
                    Term booleanTerm = terms.get(1);
                    if (blockTerm.isString()){
                        String block = ((StringTerm) blockTerm).getString();
                        if (block.equals(a) && booleanTerm.isString()){
                            String booleanString = ((StringTerm) booleanTerm).getString();
                            if (booleanString.contains("true")){ //TODO: check
                                bValue = "true";
                            }
                        }
                    }

                }
            }
        }
        b.set(bValue);

    }

    @OPERATION
    public void top1(String a, OpFeedbackParam<String> b){
        //TODO: complete
        try {
            URL url = new URL("http://localhost:5000/env/top/?block=" + a);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String str = readResponse(connection);
            System.out.println("top: " +str);
            b.set(str);
        } catch (Exception e){
            e.printStackTrace();
            b.set("");
        }
    }

    @OPERATION
    public void top(String a, OpFeedbackParam<String> b){
        String top = "";
        List<Literal> signifiers = getSignifiers();
        for (Literal s: signifiers){
            if (s.getFunctor().equals("hasTop")){
                List<Term> terms = s.getTerms();
                Term t1 = terms.get(0);
                Term t2 = terms.get(1);
                if (t1.isString() && t2.isString()){
                    String b1 = ((StringTerm) t1).getString();
                    String b2 = ((StringTerm) t1).getString();
                    if (b1.equals(a)){
                        top = b2;
                    }
                }
            }
        }
        System.out.println("top: "+ top);
        b.set(top);
    }
}
