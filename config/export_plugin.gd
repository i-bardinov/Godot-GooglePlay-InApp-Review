@tool
extends EditorPlugin

var export_plugin: AndroidExportPlugin

func _enter_tree():
    export_plugin = AndroidExportPlugin.new()
    add_export_plugin(export_plugin)

func _exit_tree():
    remove_export_plugin(export_plugin)
    export_plugin = null

class AndroidExportPlugin extends EditorExportPlugin:
    var _plugin_name = "GodotGooglePlayInAppReview"

    func _get_name():
        return _plugin_name

    func _supports_platform(platform):
        if platform is EditorExportPlatformAndroid:
            return true
        return false

    func _get_android_libraries(platform, debug):
        return PackedStringArray(["res://addons/GodotGooglePlayInAppReview/GodotGooglePlayInAppReview.1.1.0.release.aar"])

    func _get_android_dependencies(platform, debug):
        return PackedStringArray(["com.google.android.play:review:2.0.2"])