//package AI Grammar App;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;



/** Created by Sahil Sahota
 * 
 */
public class GrammarApp implements APIKey {
    
private String userInput;



    public static void main(String[] args) {
        //System.out.println(getContentFromJSON(runPrompt("how long do whales live?")));

    }

    
    /** Creates openAI API connects API key to url connection object and returns a prompt in a JSON data structure
     * 
     * @param Requires a prompt "please fix my grammar: + userinpuit"
     * @return Returns a String that has correct grammer
     */
    public static String runPrompt(String s) {
        //System.out.println("test 1");
        //create open ai api
        String url = "https://api.openai.com/v1/chat/completions"; //url to chapgpt api
        String model = "gpt-3.5-turbo"; 
        //System.out.println("test 2");
        try {
            //System.out.println("test 3");
            
            URI uri = new URI(url);
            URL obj = uri.toURL(); //create a url obj
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection(); //create a url connection obj and initialize it to obj connection
            connection.setRequestMethod("POST"); 
            connection.setRequestProperty("Authorization", "Bearer " + APIKEY);
            connection.setRequestProperty("Content-Type", "application/json");
            //System.out.println("test 4");

            //request body
            String body = "{\"model\" : \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + s + "\"}]}";
            //System.out.println("test 4.1");
            connection.setDoOutput(true);
            System.out.println("test 4.11");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            //System.out.println("test 4.2");
            writer.write(body);
            //System.out.println("test 4.3");
            writer.flush();
            writer.close();
            //System.out.println("test 5");

            //get response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input; 
            StringBuffer response = new StringBuffer();
            //System.out.println("test 6");
            while((input = in.readLine()) != null) {
                response.append(input);
            }
            in.close();
            //return (response.toString().split("\"content\":\"")[1].split("\"")[0]).substring(4);
            return response.toString();

        
        } catch(Exception e) {
            throw new RuntimeException(e);
        }


    }
    /** Takes String returned from runPrompt and gets the actual oepnAI response from it
     *  by turning the response into a JSON data structure.
     * 
     * @param Requres the JSON data structure in string form
     * @return the extraxted openAI response from the JSON
     */
    public static String getContentFromJSON(String response) {
        JSONObject obj = new JSONObject(response); //turn response into jason obj
        JSONArray choicesArray = obj.getJSONArray("choices"); //create json array at choices key
        JSONObject choicesObj = choicesArray.getJSONObject(0); //create an object from index 0 of chocies array
        JSONObject messageObj = choicesObj.getJSONObject("message"); //gets "messages", which is an object in choicesObj
        return messageObj.getString("content"); 


    }

    /** runs the runPromptMethod and the getContentFromJSON method in order to fully extract the answer
     * based on the prompt and userinput. Try catch incase the API cannot connect to openAI.
     * 
     * @param Requires the original input from user
     * @return Returns the final answer from OpenAI
     */
    public String fixGrammar() {
        boolean doesNotWork = true;
        while (doesNotWork==true){
            try {
                return getContentFromJSON(runPrompt("without quotation markfix my grammar: " + userInput));
                
            } catch (Exception e) {
                doesNotWork=true;

            }
        }
        return "error";
        }
            
    

    public void setUserInput(String s) {
        this.userInput = s;
    }

    public String getUserInput() {
        return this.userInput;
    }

}
