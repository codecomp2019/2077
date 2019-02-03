package com.example.csun_compt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public abstract class PopUpDialog extends AppCompatActivity implements View.OnClickListener {

    int idNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_dialog);
        Intent intent = this.getIntent();
        idNum = intent.getExtras().getInt("ID");


    }

    private void submitButton() {
        final Button rotateButton = (Button) findViewById(R.id.submit_button);
        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Button btn =  (Button)findViewById(R.id.submit_button);
                Intent i = new Intent(PopUpDialog.this, MainActivity.class);
                i.putExtra("ID", idNum);
                i.putExtra("Desc", btn.getText() );
                startActivity(i);
            }
        });
    }
}
