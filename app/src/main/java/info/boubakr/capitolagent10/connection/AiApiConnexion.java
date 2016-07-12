package info.boubakr.capitolagent10.connection;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import info.boubakr.capitolagent10.MainActivity;
import info.boubakr.capitolagent10.commands.CallCommand;

/**
 * Created by aboubakr on 28/06/16.
 */
public class AiApiConnexion implements AIListener {


    public static final String  TAG = AiApiConnexion.class.getName();
    private static final String ERROR_MESSAGE = "I did not undrestand what you want exactly!";
    private MainActivity context;
    private AIService aiService;
    private Gson gson;

    private String responseSpeech; // whay agent say
    private String source;
    private String resolvedQuery; ////what user say
    private String sessionId; //

    private TextView whatAgentSayTextView;
    private TextView whatUserSayTextView;

    private TextToSpeech textToSpeech;

    //Constructeur
    public AiApiConnexion(MainActivity context, TextView whatAgentSayTextView,TextView whatUserSayTextView,TextToSpeech textToSpeech){

        this.context = context;
        this.whatAgentSayTextView = whatAgentSayTextView;
        this.whatUserSayTextView = whatUserSayTextView;
        this.textToSpeech = textToSpeech;

        gson = GsonFactory.getGson();
        final AIConfiguration config = new AIConfiguration(Config.ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(context,config);
        aiService.setListener(this);

    }
    //cette methode lance le service d'écoute
    //écouter ce que l'utilisateur dit
    //puis envoyer le sound captu à l' API AI
    public void startListning(){
        aiService.startListening();
    }
    @Override
    public void onResult(final AIResponse response) {//processus de réponse

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"onResult...........");
                if(response.isError()){
                    responseSpeech = ERROR_MESSAGE;
                    textToSpeech.speak(responseSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    Log.i(TAG,"Received error response");
                    Log.i(TAG, "Error details: " + response.getStatus().getErrorDetails());
                    Log.i(TAG, "Error type: " + response.getStatus().getErrorType());
                }else{
                    Log.i(TAG,"Received success response !");
                    responseSpeech = response.getResult().getFulfillment().getSpeech();
                    resolvedQuery = response.getResult().getResolvedQuery();
                    source = response.getResult().getSource();
                    sessionId = response.getId();
                    //user
                    whatUserSayTextView.setText(resolvedQuery);
                    whatUserSayTextView.setVisibility(View.VISIBLE);
                   /* if(resolvedQuery.contains("call")){
                        String contactName = "";
                       // CallCommand.getInstence().makeCull(contactName);
                    }*/
                    //agent
                    whatAgentSayTextView.setText(responseSpeech);
                    whatAgentSayTextView.setVisibility(View.VISIBLE);



                    textToSpeech.speak(responseSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    refrechParams();
                }
                //
                if (response.getResult().getParameters() != null && !response.getResult().getParameters().isEmpty()) {
                    Log.i(TAG, "Parameters: ");
                    for (final Map.Entry<String, JsonElement> entry : response.getResult().getParameters().entrySet()){
                        Log.i(TAG, String.format("%s: %s", entry.getKey(), entry.getValue().toString()));
                    }
                }
                //

            }
        });

    }

    @Override
    public void onError(AIError error) { //process error if an error appear

        //agent
        whatAgentSayTextView.setText(ERROR_MESSAGE);
        whatAgentSayTextView.setVisibility(View.VISIBLE);
        textToSpeech.speak(ERROR_MESSAGE, TextToSpeech.QUEUE_FLUSH, null);
        refrechParams();
    }

    @Override
    public void onAudioLevel(float level) {

    } // callback for sound level visualization

    @Override
    public void onListeningStarted() {

    }// indicate start listening here

    @Override
    public void onListeningCanceled() {

    } // indicate stop listening here

    @Override
    public void onListeningFinished() {

    }// indicate stop listening here

    //GETTERS & SETTERS
    public String getResponseSpeech() {
        return responseSpeech;
    }

    public void setResponseSpeech(String responseSpeech) {
        this.responseSpeech = responseSpeech;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResolvedQuery() {
        return resolvedQuery;
    }

    public void setResolvedQuery(String resolvedQuery) {
        this.resolvedQuery = resolvedQuery;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    private void refrechParams(){
        responseSpeech = ""; // whay agent say
        resolvedQuery = ""; ////what user say
    }
}
