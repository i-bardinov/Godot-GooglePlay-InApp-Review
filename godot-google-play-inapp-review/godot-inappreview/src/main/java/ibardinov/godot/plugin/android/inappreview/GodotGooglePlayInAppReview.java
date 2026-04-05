package ibardinov.godot.plugin.android.inappreview;

import android.app.Activity;

import org.godotengine.godot.Godot;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;

public class GodotGooglePlayInAppReview extends GodotPlugin {
    ReviewManager manager = null;
    ReviewInfo reviewInfo = null;

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
    public List<String> getPluginMethods() {
        return Arrays.asList(
                "requestReviewInfo", "launchReviewFlow"
        );
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
    public void requestReviewInfo() {
        if (manager == null) {
            manager = ReviewManagerFactory.create(getActivity());
        }
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                reviewInfo = task.getResult();
                emitSignal("on_request_review_success");
            } else {
                emitSignal("on_request_review_failed");
            }
        });
    }

    /**
     * Launch review with GUI
     *
     */
    public void launchReviewFlow() {
        Task<Void> flow = manager.launchReviewFlow(getActivity(), reviewInfo);
        flow.addOnCompleteListener(task -> {
            emitSignal("on_launch_review_flow_success");
        });
    }
}
