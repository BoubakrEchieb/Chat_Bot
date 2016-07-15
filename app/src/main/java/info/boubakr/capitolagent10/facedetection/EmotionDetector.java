package info.boubakr.capitolagent10.facedetection;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

import info.boubakr.capitolagent10.MainActivity;
import info.boubakr.capitolagent10.camera.MyCameraPreview;

/**
 * Created by aboubakr on 04/07/16.
 */
public class EmotionDetector  implements CameraDetector.CameraEventListener,Detector.FaceListener,Detector.ImageListener{

    private static final String LISENCE_PATH = "sdk_echieb.boubakr@gmail.com.license";
    private int rate = 5;
    CameraDetector cameraDetector;
    private MyCameraPreview cameraPreview;
    private Context context;
    private MainActivity main;
    private RelativeLayout mainLayout;

    public EmotionDetector(MyCameraPreview cameraPreview, Context context, MainActivity main, RelativeLayout mainLayout){
        this.cameraPreview = cameraPreview;
        this.context = context;
        this.main = main;
        this.mainLayout = mainLayout;
        this.cameraDetector = new CameraDetector(context, CameraDetector.CameraType.CAMERA_BACK,cameraPreview);
        configCameraDetector(cameraDetector);
    }

    private void configCameraDetector(CameraDetector cameraDetector) {
        cameraDetector.setLicensePath(LISENCE_PATH);
        cameraDetector.setMaxProcessRate(rate);
        cameraDetector.setSendUnprocessedFrames(true);
        cameraDetector.setFaceListener(this);
        cameraDetector.setImageListener(this);
        cameraDetector.setOnCameraEventListener(this);
        //
        cameraDetector.setDetectAllAppearance(true);
        cameraDetector.setDetectAllEmotions(true);
        cameraDetector.setDetectAllExpressions(true);
        cameraDetector.setDetectAllEmojis(true);
        //
    }

    public void startDetection(){
        //Lancer la detection et tester sur chaque émotion detecté.
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(cameraDetector.getDetectAnger()){

                }
                if(cameraDetector.getDetectAttention()){

                }
                if(cameraDetector.getDetectGender()){

                }
                if(cameraDetector.getDetectBrowFurrow()){

                }
                if(cameraDetector.getDetectGlasses()){

                }
                if(cameraDetector.getDetectSmile()){

                }
                if(cameraDetector.getDetectEyeClosure()){

                }
                if(cameraDetector.getDetectJoy()){

                }
            }
        });

    }
    @Override
    public void onCameraSizeSelected(int cameraWidth, int cameraHeight, Frame.ROTATE rotation) {
        int cameraPreviewWidth;
        int cameraPreviewHeight;
        //cameraWidth and cameraHeight report the unrotated dimensions of the camera frames, so switch the width and height if necessary

        if (rotation == Frame.ROTATE.BY_90_CCW || rotation == Frame.ROTATE.BY_90_CW) {
            cameraPreviewWidth = cameraHeight;
            cameraPreviewHeight = cameraWidth;
        } else {
            cameraPreviewWidth = cameraWidth;
            cameraPreviewHeight = cameraHeight;
        }

//retrieve the width and height of the ViewGroup object containing our SurfaceView (in an actual application, we would want to consider the possibility that the mainLayout object may not have been sized yet)

        int layoutWidth = mainLayout.getWidth();
        int layoutHeight = mainLayout.getHeight();

//compute the aspect Ratio of the ViewGroup object and the cameraPreview

        float layoutAspectRatio = (float)layoutWidth/layoutHeight;
        float cameraPreviewAspectRatio = (float)cameraWidth/cameraHeight;

        int newWidth;
        int newHeight;

        if (cameraPreviewAspectRatio > layoutAspectRatio) {
            newWidth = layoutWidth;
            newHeight =(int) (layoutWidth / cameraPreviewAspectRatio);
        } else {
            newWidth = (int) (layoutHeight * cameraPreviewAspectRatio);
            newHeight = layoutHeight;
        }

//size the SurfaceView

        ViewGroup.LayoutParams params = cameraPreview.getLayoutParams();
        params.height = newHeight;
        params.width = newWidth;
        cameraPreview.setLayoutParams(params);
    }

    @Override
    public void onFaceDetectionStarted() {
        while (cameraDetector.isRunning()){
            if(cameraDetector.getDetectAnger()){

            }
            if(cameraDetector.getDetectAttention()){

            }
            if(cameraDetector.getDetectGender()){

            }
            if(cameraDetector.getDetectBrowFurrow()){

            }
            if(cameraDetector.getDetectGlasses()){

            }
            if(cameraDetector.getDetectSmile()){

            }
            if(cameraDetector.getDetectEyeClosure()){

            }
            if(cameraDetector.getDetectJoy()){

            }
        }

    }

    @Override
    public void onFaceDetectionStopped() {

    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float timestamp) {
        if(faces == null){
            return;
        }
        if(faces.size() == 0){
            return;
        }
        //for each face found
        for(int i = 0 ; i < faces.size() ; i++){
            Face face = faces.get(i);
            int id = face.getId();

            //
            Face.GENDER genderValue = face.appearance.getGender();
            Face.GLASSES glassesValue = face.appearance.getGlasses();
            //
            //Some Emoji
            float smiley = face.emojis.getSmiley();
            float laughing = face.emojis.getLaughing();
            float wink = face.emojis.getWink();


            //Some Emotions
            float joy = face.emotions.getJoy();
            float anger = face.emotions.getAnger();
            float disgust = face.emotions.getDisgust();

            //Some Expressions
            float smile = face.expressions.getSmile();
            float brow_furrow = face.expressions.getBrowFurrow();
            float brow_raise = face.expressions.getBrowRaise();

            //Measurements
            float interocular_distance = face.measurements.getInterocularDistance();
            float yaw = face.measurements.orientation.getYaw();
            float roll = face.measurements.orientation.getRoll();
            float pitch = face.measurements.orientation.getPitch();

            //Face feature points coordinates
            PointF[] points = face.getFacePoints();

        }
    }

}
