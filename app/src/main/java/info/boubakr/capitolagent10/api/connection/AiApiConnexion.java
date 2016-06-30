package info.boubakr.capitolagent10.api.connection;

import android.speech.tts.TextToSpeech;
import android.util.Log;
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

/**
 * Created by aboubakr on 28/06/16.
 */
public class AiApiConnexion implements AIListener {


    public static final String  TAG = AiApiConnexion.class.getName();
    private static final String ERROR_MESSAGE = "I did not undrestand what you want exactly!";
    private MainActivity context;
    private AIService aiService;
    private Gson gson;

    private String responseSpeech;
    private String source;
    private String resolvedQuery;
    private String sessionId;

    private TextView speech;
    private TextToSpeech textToSpeech;

    //Constructeur
    public AiApiConnexion(MainActivity context, TextView speech,TextToSpeech textToSpeech){

        this.context = context;
        this.speech = speech;
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
                    speech.setText(responseSpeech);
                    textToSpeech.speak(responseSpeech, TextToSpeech.QUEUE_FLUSH, null);
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
    public void onError(AIError error) { //process error
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onError");
                //aiTextView.setText(error.toString());
            }
        });
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
}
