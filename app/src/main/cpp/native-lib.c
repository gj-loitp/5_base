#include <jni.h>

JNIEXPORT jstring JNICALL
Java_vn_loitp_app_activity_demo_ndk_NDKDemoActivity_getStringHello(
        JNIEnv *env,
        jobject thiz,
        jstring s
) {
    return (*env)->NewStringUTF(env, "This is a string from C++");
}

JNIEXPORT jint JNICALL
Java_vn_loitp_app_activity_demo_ndk_NDKDemoActivity_add(JNIEnv *env, jobject thiz, jint x, jint y) {
    return x + y;
}