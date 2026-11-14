# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Preserve annotations used by serializers and dependency injection
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep Fragments referenced in your navigation XML graphs
-keep class * extends androidx.fragment.app.Fragment {
    public <init>();
}

# Keep Safe Args generated 'Args' and 'Directions' classes
-keep class **.*Args { *; }
-keep class **.*Args$* { *; }
-keep class **.*Directions { *; }
-keep class **.*Directions$* { *; }

# Keep methods required by Safe Args to parse Bundles and SavedStateHandles
-keepclasseswithmembers class * {
    public static *** fromBundle(...);
}
-keepclasseswithmembers class * {
    public static *** fromSavedStateHandle(...);
}

# Keep custom Parcelable and Serializable data classes used as Navigation Arguments
-keepnames class * extends android.os.Parcelable
-keepnames class * implements java.io.Serializable