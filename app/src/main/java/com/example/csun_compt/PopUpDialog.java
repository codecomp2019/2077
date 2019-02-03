package com.example.csun_compt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public abstract class PopUpDialog extends AppCompatActivity implements View.OnClickListener {

    int idNum;
    private Button btn;
    EditText text_box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_dialog);
        btn = (Button) findViewById(R.id.submit_button);
        text_box = (EditText) findViewById(R.id.user_input_box);
        Intent intent = this.getIntent();
        //idNum = intent.getExtras().getInt("ID");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(PopUpDialog.this, MainActivity.class);
                //i.putExtra("ID", idNum);
               // i.putExtra("Desc", text_box.getText());
                startActivity(i);
            }
        });
    }

}
