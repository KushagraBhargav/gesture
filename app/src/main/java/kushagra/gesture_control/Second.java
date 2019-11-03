package kushagra.gesture_control;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;


public class Second extends Activity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureLibrary mLibrary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        if (!mLibrary.load()) {
            finish();
        }
        GestureOverlayView gestures = findViewById(R.id.gestures);
        gestures.addOnGesturePerformedListener(this);

    }

    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            String result = predictions.get(0).name;
            if ("dialer".equalsIgnoreCase(result)) {
                Intent launchIntent =
                        getPackageManager().getLaunchIntentForPackage("com.google.android.dialer");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            } else if ("camera".equalsIgnoreCase(result)) {
                Toast.makeText(this, "Opening camera", Toast.LENGTH_LONG).show();
                Intent launchIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            } else if ("calculator".equalsIgnoreCase(result)) {
                Toast.makeText(this, "Opening calculator",
                        Toast.LENGTH_LONG).show();
                String CALCULATOR_PACKAGE = "com.android.calculator2";
                String CALCULATOR_CLASS = "com.android.calculator2.Calculator";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(CALCULATOR_PACKAGE, CALCULATOR_CLASS));
                Second.this.startActivity(intent);
            } else if ("settings".equalsIgnoreCase(result)) {
                Toast.makeText(this, "Opening settings", Toast.LENGTH_LONG).show();
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            } else if ("calender".equalsIgnoreCase(result)) {
                Toast.makeText(this, "Opening calendar", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("title", "Some title");
                intent.putExtra("description", "Some description");
                startActivity(intent);
            }
        }
    }

}
