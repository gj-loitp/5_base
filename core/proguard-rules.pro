# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Installation\Ung_Dung_Lap_Trinh\Java\IDE\AndroidStudio\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

##for eventbus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
##end for eventbus
#
##for realm
#-keep class io.realm.annotations.RealmModule
#-keep @io.realm.annotations.RealmModule class *
#-dontwarn javax.**
#-dontwarn io.realm.**
##for realm
#
##for loading view
#-keep class vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.** { *; }
#-keep class vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.indicators.** { *; }
##for loading view