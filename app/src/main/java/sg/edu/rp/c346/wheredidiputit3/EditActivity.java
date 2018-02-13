package sg.edu.rp.c346.wheredidiputit3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.jar.Manifest;

public class EditActivity extends AppCompatActivity {

    EditText etEditItem, etEditPlace;
    Button btnEdit, btnCapture;
    Item item;
    ImageView img;
    DBHelper db = new DBHelper(EditActivity.this);
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etEditItem = (EditText)findViewById(R.id.editTextEditItem);
        etEditPlace = (EditText)findViewById(R.id.editTextEditPlace);
        btnEdit = (Button)findViewById(R.id.buttonEdit);
        btnCapture = (Button)findViewById(R.id.btnCapture);
        img = (ImageView)findViewById(R.id.image_view);
//        final int CAM_REQUEST = 1;
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newDir = new File(dir);
        newDir.mkdirs();

        Intent i = getIntent();
        item = (Item) i.getSerializableExtra("data");
        etEditItem.setText(item.getItemTitle());
        etEditPlace.setText(item.getItemPlace());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemTitle = etEditItem.getText().toString();
                String itemPlace = etEditPlace.getText().toString();
                item.setItemTitle(itemTitle);
                item.setItemPlace(itemPlace);
                db.updateItem(item);
                db.close();
                finish();
            }
        });


        if(ContextCompat.checkSelfPermission(EditActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(EditActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_CODE);
            }else{
                ActivityCompat.requestPermissions(EditActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO_CODE);
            }
        }
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                String file = dir+count+".jpg";
                File newFile = new File(file);
                try {
                    newFile.createNewFile();
                }
                catch (IOException e) {
                }
                Uri outputFileUri = Uri.fromFile(newFile);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
//                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File file = getFile();
//                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                startActivityForResult(camera_intent,CAM_REQUEST);
            }
        });
    }
//    private File getFile(){
//        File folder = new File("sdcard/WhereDidIPutIt3");
//        if(!folder.exists()){
//            folder.mkdir();
//        }
//
//        File image_file = new File(folder,"cam_image.jpg");
//        return image_file;
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        String path = "sdcard/WhereDidIPutIt3/cam_image.jpg";
//        img.setImageDrawable(Drawable.createFromPath(path));
//
//
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);

     if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
          Log.d("WhereDidIPutIt3Camera", "Pic saved");
        }
    }
}
