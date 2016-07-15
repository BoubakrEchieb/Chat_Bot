package info.boubakr.capitolagent10;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import info.boubakr.capitolagent10.connection.AiApiConnexion;

public class MainActivity extends AppCompatActivity implements
                                                    View.OnClickListener,
                                                    TextToSpeech.OnInitListener{
    private AiApiConnexion connexion;
    private TextToSpeech textToSpeech;
    private ImageButton listenButton;
    private TextView whatAgentSayTextView;
    private TextView whatUserSayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        textToSpeech = new TextToSpeech(this,this);
        listenButton = (ImageButton) findViewById(R.id.button_listen);
        whatAgentSayTextView = (TextView) findViewById(R.id.agent_say_text_view);
        whatUserSayTextView = (TextView) findViewById(R.id.user_say_text_view);

        connexion = new AiApiConnexion(this,whatAgentSayTextView,whatUserSayTextView,textToSpeech);
        listenButton.setOnClickListener(this);
    }

    //micro clickListner
    @Override
    public void onClick(View v) {
        if(whatUserSayTextView.getVisibility() == View.VISIBLE){
            whatUserSayTextView.setVisibility(View.INVISIBLE);
        }
        if(whatAgentSayTextView.getVisibility() == View.VISIBLE){
            whatAgentSayTextView.setVisibility(View.INVISIBLE);
        }
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
