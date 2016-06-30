package info.boubakr.capitolagent10;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Locale;
import java.util.Map;

import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import info.boubakr.capitolagent10.api.connection.AiApiConnexion;

public class MainActivity extends AppCompatActivity implements AIListener,View.OnClickListener,TextToSpeech.OnInitListener{

    private AiApiConnexion connexion;
    private TextToSpeech textToSpeech;
    private ImageButton listenButton;
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textToSpeech = new TextToSpeech(this,this);
        listenButton = (ImageButton) findViewById(R.id.button_listen);
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        connexion = new AiApiConnexion(this,resultTextView,textToSpeech);
        listenButton.setOnClickListener(this);
    }

    // AiListner Methods
    @Override
    public void onResult(AIResponse response) {// here process response
        Result result = response.getResult();
        //getParametrs
        String parametrString = "";
        if(result.getParameters()!=null && !result.getParameters().isEmpty()){
            for(final Map.Entry<String,JsonElement> entry : result.getParameters().entrySet()){
                parametrString += "(" + entry.getKey() + ", " + entry.getValue() + ")";
                resultTextView.setText("Query : " + result.getResolvedQuery()+
                "\nAction: " + result.getAction() +
                "\nParameters: " + parametrString);
            }
        }

    }//

    @Override
    public void onError(AIError error) {// here process error
        resultTextView.setText(error.toString());
    }//

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

    //micro clickListner
    @Override
    public void onClick(View v) {
        connexion.startListning();
    }

    //TextToSpeech
    @Override
    public void onInit(int status) {
        if(status != TextToSpeech.ERROR){
            textToSpeech.setLanguage(Locale.ENGLISH);
        }
    }
    //
}
