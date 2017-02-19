package com.lepko.martin.arquiz.CameraAR;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lepko.martin.arquiz.R;
import com.lepko.martin.arquiz.Vuforia.VuforiaApplicationControl;
import com.lepko.martin.arquiz.Vuforia.VuforiaApplicationException;
import com.lepko.martin.arquiz.Vuforia.VuforiaApplicationSession;
import com.vuforia.CameraDevice;
import com.vuforia.DataSet;
import com.vuforia.ObjectTracker;
import com.vuforia.State;
import com.vuforia.Tracker;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

public class CameraActivity extends Activity implements VuforiaApplicationControl {

    private static final String TAG = "CameraActivity";
    DataSet dataSetUserDef = null;
    boolean mIsDroidDevice = false;
    private VuforiaApplicationSession vuforiaAppSession;
    private OpenGLView mGlView;
    private RelativeLayout mCameraLayout;
    private OpenGLRenderer mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        vuforiaAppSession = new VuforiaApplicationSession(this);
        vuforiaAppSession.initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mIsDroidDevice = android.os.Build.MODEL.toLowerCase().startsWith("droid");
    }

    @Override
    protected void onResume() {

        Log.d(TAG, "onResume");

        super.onResume();

        try {
            vuforiaAppSession.resumeAR();
        } catch (VuforiaApplicationException e) {
            Log.e(TAG, e.getString());
        }

        // Resume the GL view:
        if (mGlView != null) {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }
    }

    @Override
    protected void onPause() {

        Log.d(TAG, "onPause");
        super.onPause();

        if (mGlView != null) {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }

        try {
            vuforiaAppSession.pauseAR();
        } catch (VuforiaApplicationException e) {
            Log.e(TAG, e.getString());
        }
    }

    @Override
    protected void onDestroy() {

        Log.d(TAG, "onDestroy");
        super.onDestroy();

        try {
            vuforiaAppSession.stopAR();
        } catch (VuforiaApplicationException e) {
            Log.e(TAG, e.getString());
        }
    }

    @Override
    public boolean doInitTrackers() {

        Log.d(TAG, "doInitTrackers");

        boolean result = true;

        // Initialize the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker tracker = trackerManager.initTracker(ObjectTracker.getClassType());
        if (tracker == null) {
            Log.d(TAG, "Failed to initialize ObjectTracker.");
            result = false;
        } else {
            Log.d(TAG, "Successfully initialized ObjectTracker.");
        }

        return result;
    }

    @Override
    public boolean doLoadTrackersData() {
        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager.getTracker(ObjectTracker.getClassType());
        if (objectTracker == null) {
            Log.d(TAG, "Failed to load tracking data set because the ObjectTracker has not been initialized.");
            return false;
        }

        // Create the data set:
        dataSetUserDef = objectTracker.createDataSet();
        if (dataSetUserDef == null) {
            Log.d(TAG, "Failed to create a new tracking data.");
            return false;
        }

        if (!objectTracker.activateDataSet(dataSetUserDef)) {
            Log.d(TAG, "Failed to activate data set.");
            return false;
        }

        Log.d(TAG, "Successfully loaded and activated data set.");
        return true;
    }

    @Override
    public boolean doStartTrackers() {
        // Indicate if the trackers were started correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.start();

        return result;
    }

    @Override
    public boolean doStopTrackers() {
        // Indicate if the trackers were stopped correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.stop();

        return result;
    }

    @Override
    public boolean doUnloadTrackersData() {
        // Indicate if the trackers were unloaded correctly
        boolean result = true;

        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager.getTracker(ObjectTracker.getClassType());
        if (objectTracker == null) {
            result = false;
            Log.d(TAG, "Failed to destroy the tracking data set because the ObjectTracker has not been initialized.");
        } else {
            if (dataSetUserDef != null) {
                if (objectTracker.getActiveDataSet(0) != null && !objectTracker.deactivateDataSet(dataSetUserDef)) {
                    Log.d(TAG, "Failed to destroy the tracking data set because the data set could not be deactivated.");
                    result = false;
                }

                if (!objectTracker.destroyDataSet(dataSetUserDef)) {
                    Log.d(TAG, "Failed to destroy the tracking data set.");
                    result = false;
                }

                Log.d(TAG, "Successfully destroyed the data set.");
                dataSetUserDef = null;
            }
        }

        return result;
    }

    @Override
    public boolean doDeinitTrackers() {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;

        TrackerManager tManager = TrackerManager.getInstance();
        tManager.deinitTracker(ObjectTracker.getClassType());

        return result;
    }

    @Override
    public void onInitARDone(VuforiaApplicationException exception) {
        if (exception == null) {
            initCamera();

            mRenderer.mIsActive = true;

            addContentView(mGlView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            //Bring camera layout to front
            mCameraLayout.bringToFront();

            //set transparent color to camera layout
            mCameraLayout.setBackgroundColor(Color.TRANSPARENT);

            try {
                vuforiaAppSession.startAR(CameraDevice.CAMERA_DIRECTION.CAMERA_DIRECTION_DEFAULT);

            } catch (VuforiaApplicationException e) {
                Log.e(TAG, e.getString());
            }

        } else {
            Log.e(TAG, exception.getString());
        }
    }

    @Override
    public void onVuforiaUpdate(State state) {

    }

    private void initCamera() {
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();

        mGlView = new OpenGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new OpenGLRenderer(this, vuforiaAppSession);
        mGlView.setRenderer(mRenderer);

        addLayout(true);
    }

    private void addLayout(boolean init) {

        LayoutInflater inflater = LayoutInflater.from(this);
        mCameraLayout = (RelativeLayout) inflater.inflate(R.layout.activity_camera, null, false);

        if (init) {
            mCameraLayout.setBackgroundColor(Color.BLACK);
        }

        // Adds the inflated layout to the view
        addContentView(mCameraLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mCameraLayout.bringToFront();

    }
}
