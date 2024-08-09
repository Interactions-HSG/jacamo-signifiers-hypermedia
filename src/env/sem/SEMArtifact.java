package sem;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import jason.asSyntax.Atom;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.StringTermImpl;
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
    public void readSignifier(OpFeedbackParam<ListTerm> signifierParam){
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
                IRI signifierTypeIRI = rdf.createIRI("https://ci.mines-stetienne.fr/hmas/core#Signifier");
                Resource signifierId = null;
                for (Statement s : m.getStatements(null, RDF.TYPE, signifierTypeIRI)) {
                    signifierId = s.getSubject();
                }
                Resource actionId = null;
                for (Statement s2 : m.getStatements(signifierId, rdf.createIRI("https://ci.mines-stetienne.fr/hmas/interaction#signifies"), null)) {
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
    public void isFree(String a, OpFeedbackParam<String> b){
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
    public void top(String a, OpFeedbackParam<String> b){
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
}
