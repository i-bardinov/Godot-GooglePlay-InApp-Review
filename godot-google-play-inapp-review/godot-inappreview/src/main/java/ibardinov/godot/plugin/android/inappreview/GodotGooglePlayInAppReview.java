package ibardinov.godot.plugin.android.inappreview;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;
import org.godotengine.godot.plugin.UsedByGodot;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.gms.tasks.Task;

import java.util.Set;

public class GodotGooglePlayInAppReview extends GodotPlugin {

    private ReviewManager manager = null;
    private ReviewInfo reviewInfo = null;
    private static final String TAG = "GodotGooglePlayInAppReview";

    public GodotGooglePlayInAppReview(Godot godot) {
        super(godot);
    }

    @NonNull
    @Override
    public String getPluginName() {
        return "GodotGooglePlayInAppReview";
    }

    @NonNull
    @Override
    public Set<SignalInfo> getPluginSignals() {
        Set<SignalInfo> signals = new ArraySet<>();

        signals.add(new SignalInfo("on_request_review_success"));
        signals.add(new SignalInfo("on_request_review_failed"));
        signals.add(new SignalInfo("on_launch_review_flow_success"));

        return signals;
    }

    /**
     * Request Review Info
     *
     */
    @UsedByGodot
    public void requestReviewInfo() {
        Activity currentActivity = getActivity();
        if (currentActivity == null) return;

        if (manager == null) {
            manager = ReviewManagerFactory.create(currentActivity);
        }

        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
                emitSignal("on_request_review_success");
            } else {
                Log.e(TAG, "Request failed", task.getException());
                emitSignal("on_request_review_failed");
            }
        });
    }

    /**
     * Launch review with GUI
     *
     */
    @UsedByGodot
    public void launchReviewFlow() {
        Activity currentActivity = getActivity();

        if (currentActivity == null || manager == null || reviewInfo == null) {
            Log.e(TAG, "Cannot launch review flow. Info is missing.");
            return;
        }

        Task<Void> flow = manager.launchReviewFlow(currentActivity, reviewInfo);
        flow.addOnCompleteListener(task -> {
            emitSignal("on_launch_review_flow_success");
            reviewInfo = null;
        });
    }
}