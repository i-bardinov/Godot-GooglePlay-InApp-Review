# Godot Google Play In-App Review Plugin

This is a Android plugin for [Godot Engine](https://github.com/godotengine/godot) 3.2.2 or higher.

This plugin supports:
- Request review
- Launch review

## Setup

- Configure, install  and enable the "Android Custom Template" for your project, just follow the [official documentation](https://docs.godotengine.org/en/latest/getting_started/workflow/export/android_custom_build.html);
- go to the ```release tab```, choose a version and download the respective package;
- extract the package and put```GodotGooglePlayInAppReview.gdap``` and ```GodotGooglePlayInAppReview.x.y.z.release.aar``` inside the ```res://android/plugins``` directory on your Godot project.
- on the Project -> Export... -> Android -> Options -> 
    - Custom Template: check the _Use Custom Build_
    - Plugins: check the _Godot # Godot Google Play In App Review_ (this plugin)

## API Reference

### Singletons
```python

# Example of using
if Engine.has_singleton("GodotGooglePlayInAppReview"):
	var in_app_review = Engine.get_singleton("GodotGooglePlayInAppReview")
	in_app_review.connect("on_request_review_success", self, "_on_request_review_success")
	in_app_review.connect("on_request_review_failed", self, "_on_request_review_failed")
	in_app_review.connect("on_launch_review_flow_success", self, "_on_launch_review_flow_success")

```
### Methods
```python

# Request Review Info
requestReviewInfo()

# Launch review with GUI
launchReviewFlow()

```
### Signals
```python
# Review Info was received
on_request_review_success

# Review Info was not received or another error was received
on_request_review_failed

# Review Flow was finished
on_launch_review_flow_success
```

## Compiling the Plugin (optional)

If you want to compile the plugin by yourself, it's very easy:
1. clone this repository;
2. checkout the desired version;
3. open ```godot-google-play-inapp-review``` directory in ```Android Studio```
4. don't forget to put ```godot-lib.release.aar``` to ```godot-lib.release``` directory

If everything goes fine, you'll find the ```.aar``` files at ```godot-google-play-inapp-review/godot-inappreview/build/outputs/aar/```.

## Troubleshooting

* First of all, please make sure you're able to compile the custom build for Android without plugin, this way we can isolate the cause of the issue.

* Using logcat for Android is the best way to troubleshoot most issues. You can filter Godot only messages with logcat using the command: 
```
adb logcat -s godot
```

## References

Google Developers:
* https://developer.android.com/guide/playcore/in-app-review

## License

MIT license
