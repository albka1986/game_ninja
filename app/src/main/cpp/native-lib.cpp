#include <jni.h>
#include <string>

using namespace std;


extern "C"
JNIEXPORT jstring JNICALL
Java_com_ponomarenko_shootingRange_StartActivity_callNative(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}