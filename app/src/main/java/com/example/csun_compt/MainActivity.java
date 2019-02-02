package com.example.csun_compt;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.os.Vibrator;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        View btnNext = findViewById(R.id.btnNext);
//        btnNext.setOnClickListener( new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                speak("Next meme");
//            }
//        });
    }

    private void speak(final String str){
        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    private void vibrate(){
        Vibrator myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
//        myVib.vibrate(50);
        // Star Wars Theme song
        myVib.vibrate(new long[]{0, 500, 110, 500, 110, 450, 110, 200, 110, 170, 40, 450, 110, 200, 110, 170, 40, 500}, -1);
        Log.e("Mug", "vibration succeed");
    }
}
