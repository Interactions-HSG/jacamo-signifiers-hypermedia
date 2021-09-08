package wot;

import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;
import ch.unisg.ics.interactions.wot.td.clients.TDHttpResponse;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.hyperagents.hypermedia.HypermediaPlan;
import org.hyperagents.util.ReifiedStatement;
import org.hyperagents.util.State;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HTTPArtifact extends Artifact {

    private static final String WEBID_PREFIX = "http://hyperagents.org/";


    @OPERATION
    public void sendRequest(String urlString, String method, Map<String, String> headers, Optional<String> payload){
        BasicClassicHttpRequest request = new BasicClassicHttpRequest(method, urlString);
        for (String key : headers.keySet()){
            String value = headers.get(key);
            request.addHeader(key, value);
        }
        if (payload.isPresent()){
            request.setEntity(new StringEntity(payload.get()));
        }
        HttpClient client = HttpClients.createDefault();
        try {
            HttpResponse response = client.execute(request);
            System.out.println("response received");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @OPERATION
    public void sendRequestPayload(String urlString, String method, Map<String, String> headers, String payload){
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
    public String sendRequestReturn(String urlString, String method, Map<String, String> headers){
        BasicClassicHttpRequest request = new BasicClassicHttpRequest(method, urlString);
        for (String key : headers.keySet()){
            String value = headers.get(key);
            request.addHeader(key, value);
        }
        String str = "";
        HttpClient client = HttpClients.createDefault();
        try {
            TDHttpResponse response = new TDHttpResponse( (ClassicHttpResponse) (client.execute(request)));
            Optional<String> payload = response.getPayload();
            if (payload.isPresent()){
                System.out.println("payload present");
                str = payload.get();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    @OPERATION
    public void writePurpose(String profileUrl, State purpose){
        for (ReifiedStatement s: purpose.getStatements()){
            String str = getRDFStatement(s);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type","application/json");
            headers.put("X-Agent-WebId",WEBID_PREFIX + getCurrentOpAgentId().getAgentName());
            sendRequestPayload(profileUrl+"/purpose", "POST", headers, toListString(str));
        }
    }

    @OPERATION
    public void writeSituation(String profileUrl, State purpose){
        for (ReifiedStatement s: purpose.getStatements()){
            String str = getRDFStatement(s);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type","application/json");
            headers.put("X-Agent-WebId",WEBID_PREFIX + getCurrentOpAgentId().getAgentName());
            sendRequestPayload(profileUrl+"/situation", "POST", headers, toListString(str));
        }
    }

    @OPERATION
    public void retrieveProfile(String profileUrl, OpFeedbackParam<Object> returnParam){
        Map<String, String> headers = getStandardHeaders(true);
        String str = sendRequestReturn(profileUrl+"/profile", "POST", headers);
        returnParam.set(str);
    }

    public Map<String, String> getStandardHeaders(boolean b){
        Map<String, String> headers = new HashMap<>();
        String agentWebId = WEBID_PREFIX + getCurrentOpAgentId().getAgentName();
        headers.put("X-Agent-WebId",agentWebId);
        //headers.put("Content-Type","application/json");
        headers.put("Accept-Encoding", "text/turtle");
        if (b) {
            headers.put("X-Reply", "true");
        } else {
            headers.put("X-Reply", "false");
        }
        return headers;
    }

    @OPERATION
    public void sendStandardRequestPayload(String url, String payload){
        Map<String, String> headers = getStandardHeaders(false);
        sendRequestPayload(url, "POST", headers, payload);
    }

    public void sendStandardRequestReturn(String url, OpFeedbackParam<Object> returnParam){
        Map<String, String> headers = getStandardHeaders(true);
        sendRequestReturn(url, "POST", headers);

    }

    @OPERATION
    public void retrieveSignifiers(String artifactUrl, OpFeedbackParam<Object> returnParam){
        String method = "POST";
        String url = artifactUrl+"/retrieve";
        Map<String, String> headers = getStandardHeaders(true);
        String returnString = sendRequestReturn(url, method, headers);
        returnParam.set(returnString);
    }

    @OPERATION
    public void retrieveContentUrl(String urlString, OpFeedbackParam<Object> returnParam) {
        String s = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("X-Agent-WebId",WEBID_PREFIX + getCurrentOpAgentId().getAgentName());
            if (connection.getResponseCode()==200){
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                Iterator<String> lines = in.lines().iterator();
                while (lines.hasNext()){
                    String str = lines.next();
                    s = s + str +"\n";
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        returnParam.set(s);
    }


    @OPERATION
    public void useHypermediaPlan(HypermediaPlan p){
        String url = p.getUrl();
        String method = p.getMethod();
        Map<String, String> headers = p.getHeaders();
        String agentWebId = WEBID_PREFIX + getCurrentOpAgentId().getAgentName();
        headers.put("X-Agent-WebId",agentWebId);
        Optional<String> payload = p.getPayload();
        sendRequest(url, method, headers, payload);
}

    @OPERATION
    public void retrieveCurrentLocation(String mazeUrl, OpFeedbackParam<Integer> returnParam){
        String method = "POST";
        Map<String, String> headers = new Hashtable<>();
        String agentWebId = WEBID_PREFIX + getCurrentOpAgentId().getAgentName();
        headers.put("X-Agent-WebId",agentWebId);
        String reply = sendRequestReturn(mazeUrl,method, headers);
        int location = Integer.parseInt(reply);
        returnParam.set(location);

    }

    @OPERATION
    public void createProfileArtifact(String urlString, String name, OpFeedbackParam<Object> returnParam){
        String type = "http://example.org/AgentProfileArtifact";
        String agentName = WEBID_PREFIX + getCurrentOpAgentId().getAgentName();
        List<String> parameters = new ArrayList<>();
        parameters.add(agentName);
        String str = "{ \"artifactClass\": \"" + type + "\", \"artifactName\" : \"" + name + "\",\"initParams\": " + getRepresentation(parameters) + "}";
        BasicClassicHttpRequest request = new BasicClassicHttpRequest("POST", urlString);
        request.addHeader("Content-Type","application/json");
        request.addHeader("X-Agent-WebId",WEBID_PREFIX + getCurrentOpAgentId().getAgentName());
        request.setEntity(new StringEntity(str));
        HttpClient client = HttpClients.createDefault();
        try {
            HttpResponse response =  client.execute(request);
            returnParam.set(urlString+name);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @OPERATION
    public void registerProfile(String profileUri, String signifierArtifactUri){
        String uri = signifierArtifactUri+"/profile";
        String payload = "["+profileUri+"]";
        Map<String, String> headers = getStandardHeaders(false);
        sendRequestPayload(uri, "POST", headers, payload);
    }

    @OPERATION
    public void move(String mazeUrl, int m){
        String uri = mazeUrl+"/move";
        String payload = "["+m+"]";
        Map<String, String> headers = getStandardHeaders(false);
        sendRequestPayload(uri, "POST", headers, payload);

    }

    public String getRepresentation(List<String> l){
        String str = "[";
        for (String s : l){
            str = str + "\""+s+"\"";
        }
        str = str +"]";
        return str;
    }


    private String getRDFStatement(ReifiedStatement s){
        String str = "rdf("+s.getSubject()+","+s.getPredicate()+","+s.getObject()+")";
        return str;
    }

    private String toListString(String str){
        String paramList="[\""+str+"\"]";
        return paramList;
    }
}
