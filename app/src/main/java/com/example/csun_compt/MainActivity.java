package com.example.csun_compt;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.speech.tts.TextToSpeech;
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
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    //camera function
    private Button cameraButton;
    private ImageView capturedImage;
    private static final int Image_Capture_Code = 1;


    Object[] images;
    //int endOfImages = images.length;
    int currImage = 0;
    public TextView descriptionTextView;

    Picasso p;
    Speaker speak;
    ModelDatabase db;
    RestClient rclient;
    Image64EncodeString image64EncodeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //camera pt 2
        cameraButton = (Button) findViewById(R.id.cameraButton);
        capturedImage = (ImageView) findViewById(R.id.imageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                descriptionTextView.setText("");
                startActivityForResult(cInt, Image_Capture_Code);
            }
        });

        Intent intent = this.getIntent();
        speak = new Speaker(MainActivity.this);
        image64EncodeString = new Image64EncodeString();
        try {
            //Default
            if(intent.getExtras().getString("ID").equals("")) {
                descriptionTextView = (TextView) findViewById(R.id.description_text_view);

                rclient = new RestClient(this);
                db = new ModelDatabase();

                images = db.getall();

                initializeImageSwitcher();
                setInitialImage();
                setImageRotateListener();
                setImageRotateBackwardListener();
                moreButtonListener();

                descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            }
        }catch(Exception e) {
            try {
                //ID comes back with description
                db.setdisc(intent.getExtras().getInt("ID"), intent.getExtras().getString("Description"));
                setImageRotateListener();
            } catch (Exception ae){
                //Defualt
                descriptionTextView = (TextView) findViewById(R.id.description_text_view);

                rclient = new RestClient(this);
                db = new ModelDatabase();

                images = db.getall();

                initializeImageSwitcher();
                setInitialImage();
                setImageRotateListener();
                setImageRotateBackwardListener();
                moreButtonListener();

                descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            }
        }

    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == Image_Capture_Code) {
                if (resultCode == RESULT_OK) {
                    Bitmap bp = (Bitmap) data.getExtras().get("data");
                    capturedImage.setImageBitmap(bp);
                    rclient.postRawBitMap(image64EncodeString.image2String64(bp), MainActivity.this);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                }
            }
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
        //final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        /*imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                return imageView;
            }
        });*/
        //imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        //imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    public void setNewText(String str){
        descriptionTextView.setText(str);
        speak.out(MainActivity.this, str);
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
                setCurrentImageV2();
            }
        });
    }

    private void setCurrentImageV2(){
        Object image_object = db.getimg(currImage);
        if (image_object instanceof java.lang.String){
            setCurrentImage((String) image_object);
        }else{
            Integer integer = (Integer) image_object;
            setCurrentImage(integer.intValue());
        }
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
                setCurrentImageV2();
            }
        });
    }

    private void setInitialImage() {
        setCurrentImageV2();
    }

    private void setCurrentImage(String url) {
        // save for trying to move XML to ImageView instead of ImageSwitcher
        //final ImageView imageSwitcher = (ImageView)findViewById(R.id.imageSwitcher);
        //imageSwitcher.setImageResource(images[currImage]);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        // Change image source in frontend
        //p = new Picasso();
        //Picasso.get().load(images[currImage]).fit().into(imageView);
        p.get().load(url).into(imageView);

        String curr_descr = db.getdis(currImage);

        if( curr_descr.equals("") ){
            rclient.post(url, MainActivity.this);
        }
        speak.out(MainActivity.this, curr_descr);
        descriptionTextView.setText(curr_descr);

    }

    private void setCurrentImage(int hardcode){
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        // Change image source in frontend
        //Picasso p = new Picasso();
        //Picasso.get().load(images[currImage]).fit().into(imageView);
//        imageSwitcher.setImageResource(images[currImage]);

        imageView.setImageResource(hardcode);
        String curr_descr = db.getdis(currImage);
        if( curr_descr.equals("") ){
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            rclient.postRawBitMap(image64EncodeString.image2String64(bitmap), MainActivity.this);
        }
        speak.out(MainActivity.this, curr_descr);
        descriptionTextView.setText(curr_descr);
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
                i.putExtra("ID", currImage);
                startActivity(i);

            }
        });
    }



}



