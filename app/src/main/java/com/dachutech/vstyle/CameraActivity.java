package com.dachutech.vstyle;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.melnykov.fab.FloatingActionButton;

import butterknife.InjectView;

public class CameraActivity extends AppCompatActivity {
    Camera camera;
    @InjectView(R.id.surfaceView)
    SurfaceView surfaceView;
    @InjectView(R.id.btn_take_photo)
    FloatingActionButton btn_take_photo;
    SurfaceHolder surfaceHolder;
    Camera.PictureCallback jpegCallback;
    Camera.ShutterCallback shutterCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

    }
        /*ButterKnife.inject(this);
        surfaceHolder = surfaceView.getHolder();
        //Install a surfaceHolder.Callback so we get notified when
        //the underlying surface is created and destroyed
        surfaceHolder.addCallback(this);
        //deprecated setting, but required on android versions prior to 3.0
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btn_take_photo.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                cameraImage();
            }
        });
        jpegCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                FileOutputStream outputStream = null;
                File file_image = getDirc();
                if (!file_image.exists() && !file_image.mkdirs()){
                    Toast.makeText(getApplicationContext(), "Cant create directory to save image", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                String date = simpleDateFormat.format(new Date());
                String photofile = "Cam_Demo" + date + ".jpg";
                String file_name = file_image.getAbsolutePath()+ "/" + photofile;
                File picfile = new File(file_name);
                try{
                    outputStream = new FileOutputStream(picfile);
                    outputStream.write(data);
                    outputStream.close();
                }catch (FileNotFoundException e) {
                }catch (IOException ex){
                }finally{

                }p0
                Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();
                refreshCamera();
                refreshGallery(picfile);


            }
        };
    }
    //refresh gallery
    public void refreshGallery(File file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    public void refreshCamera(){
        if (surfaceHolder.getSurface()==null){
            //preview surface does not exist
            return;

        }
        //stop preview before making change
        try{
            camera.stopPreview();
        }catch (Exception e){
        }
        //set preview size and make any resize, rotate or
        //reformatting changes here
        //start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.open();
            camera.startPreview();
        }catch (Exception e){

        }
    }
    private File getDirc(){
        File dics = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(dics, "Camera_Demo");
    }
    public void cameraImage(){
        //take the picture
        camera.takePicture(null,null,jpegCallback);


    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //open the camera
        try{
            camera = Camera.open();
        }catch(RuntimeException ex){
            Camera.Parameters parameters;
            parameters = camera.getParameters();
            //modify parameters
            parameters.setPreviewFrameRate(20);
            parameters.setPictureSize(352,288);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(90);
            try{
                //the surface has been created now tell the camera where to draw the preview
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();

            }catch (Exception e){

            }

        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //stop preview and release camera
        camera.release();
        camera=null;

    }*/
}
