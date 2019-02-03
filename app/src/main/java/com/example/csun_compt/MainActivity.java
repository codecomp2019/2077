package com.example.csun_compt;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Integer[] images;
    int endOfImages = images.length;
    int currImage = 0;
    private TextView descriptionTextView;

    ModelDatabase db;
    RestClient rclient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        descriptionTextView = (TextView) findViewById(R.id.description_text_view);

        rclient = new RestClient();
        db = new ModelDatabase();

        images = db.getall();

        initializeImageSwitcher();
        setInitialImage();
        setImageRotateListener();
        setImageRotateBackwardListener();
        moreButtonListener();

        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void onClick(View v){
    /*    switch(v.getId()){
            case R.id.nextButton:
               nextButton();
                break;
            case R.id.backButton:
              //  backButton();
                break;
            case R.id.moreButton:
             //   moreButton();
                break;
            default:
                break;
        }*/

    }

    private void initializeImageSwitcher() {
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                return imageView;
            }
        });
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    private void setImageRotateListener() {
        final Button rotatebutton = (Button) findViewById(R.id.nextButton);
        rotatebutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage++;
                if (currImage == 3) {
                    currImage = 0;
                }
                setCurrentImage();
            }
        });
    }

    private void setImageRotateBackwardListener() {
        final Button rotatebutton = (Button) findViewById(R.id.backButton);
        rotatebutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                currImage--;
                if (currImage == -1) {
                    currImage = 0;
                    displayToast("At beginning!");
                }
                setCurrentImage();
            }
        });
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage() {
        // save for trying to move XML to ImageView instead of ImageSwitcher
        //final ImageView imageSwitcher = (ImageView)findViewById(R.id.imageSwitcher);
        //imageSwitcher.setImageResource(images[currImage]);

        final ImageSwitcher imageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);

        // Change image source in frontend
        imageSwitcher.setImageResource(images[currImage]);

        String curr_descr = db.getdis(currImage);
        TextView tview = (TextView)findViewById(R.id.description_text_view);

        if( curr_descr.equals("") ){
            rclient.post(db.getimg(currImage), MainActivity.this);
            // ToDo function callback to save new descr in db
        }
        else {
            tview.setText(curr_descr);
        }

    }


    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    private void moreButtonListener() {
        final Button rotateButton = (Button) findViewById(R.id.moreButton);
        rotateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, PopUpDialog.class);
                startActivity(i);
            }
        });
    }



}

  /*  public void nextButton(){
        if(count < images.length -1){
            count++;
            imageSwitcher.setImageResource(images[count]);
        }
    }

    public void backButton(){
        if(0 < count){
            count--;
            imageSwitcher.setImageResource(image[count]);
        }
    }*/



