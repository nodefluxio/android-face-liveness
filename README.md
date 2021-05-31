# android-face-liveness

![icon](/assets/icon.png)

Nodeflux Face Liveness demo application with SDK sdk-liveness-0.3.2.aar

## Requirement

- Minimal android API: 21
- Maximal android API: 30
- Docs use java
- IDE use Android Studio

## How to include SDK in project

### Create new project
1. Go to menu File -> New -> New Project
2. Choose template Empty Activity and click Next

![image](/assets/android_sdk_1.png)

3. Choose your application Name, Package name, Language, and most importantly Minimum SDK have to be 21 and above.
4. After done, click Finish.

![image](/assets/android_sdk_2.png)

### Prepare SDK lib
5. Make sure Android Studio project view as Project

![image](/assets/android_sdk_3.png)

6. Navigate to app/libs and copy your sdk there like below

![image](/assets/android_sdk_4.png)

7. Next open your application build.gradle and add below dependencies.

```
implementation files('libs/sdk-liveness-0.3.2.aar')
implementation 'androidx.camera:camera-view:1.0.0-alpha15'
implementation 'com.google.mlkit:face-detection:16.0.2'
implementation 'androidx.camera:camera-camera2:1.0.0-beta08'
implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.20'
implementation 'com.android.volley:volley:1.1.1'
```

8. Next open your application build.gradle and add below dependencies.

![image](/assets/android_sdk_5.png)

### Setup AndroidManifest.xml
9. Make sure include sdk activity

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
   package="com.example.myapplication">

   <application
       android:allowBackup="true"
       android:icon="@mipmap/ic_launcher"
       android:label="@string/app_name"
       android:roundIcon="@mipmap/ic_launcher_round"
       android:supportsRtl="true"
       android:theme="@style/Theme.SDKDemo">
       <activity android:name=".MainActivity">
           <intent-filter>
               <action android:name="android.intent.action.MAIN" />

               <category android:name="android.intent.category.LAUNCHER" />
           </intent-filter>
       </activity>
       <activity android:name="nodeflux.sdk.liveness.Liveness" /> ←- this line
   </application>

</manifest>
```

### Setup activity code
10. Go to MainActivity

![image](/assets/android_sdk_6.png)

11. Copy below code into your MainActivity
```
public class MainActivity extends AppCompatActivity {
   Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       if (!isCameraPermissionGranted()) {
           requestCameraPermission();
       } else {
           intent = new Intent(MainActivity.this, Liveness.class);
           intent.putExtra("ACCESS_KEY", "{ACCESS_KEY_HERE}");
           intent.putExtra("SECRET_KEY", "{SECRET_KEY_HERE}");
           intent.putExtra("THRESHOLD", "{THRESHOLD_HERE}"); <-- require double value 1.0. Default value is 0.7
           Liveness.setUpListener(new Liveness.LivenessCallback() {
               @Override
               public void onSuccess(boolean isLive, Bitmap bitmap, double score) {
                   Toast.makeText(MainActivity.this, String.valueOf(isLive), Toast.LENGTH_LONG).show();
               }

               @Override
               public void onError(String message) {
                   Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
               }
           });
       }
   }

   public void onButtonPressed(View v) {
       startActivity(intent);
   }

   public void requestCameraPermission() {
       ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
   }

   public boolean isCameraPermissionGranted() {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
               Log.v("permission", "Camera Permission is granted");
               return true;
           } else {
               Log.v("permission", "Camera Permission is revoked");
               return false;
           }
       } else {
           Log.v("permission", "Camera Permission is granted");
           return true;
       }
   }
}
```

### Setup activity layout
12. Go to activity_main

![image](/assets/android_sdk_7.png)

13. Copy below xml to your activity_main

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context=".MainActivity">

   <Button
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_marginBottom="84dp"
       android:onClick="onButtonPressed"
       android:text="Liveness"
       app:layout_constraintBottom_toBottomOf="parent"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintHorizontal_bias="0.498"
       app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Run the app
14. After everything is done, run the app. App will show like below.

15. Click the button and follow instructions

![image](/assets/android_sdk_8.png)


## Customization

Nodeflux SDK Liveness allows you to change face guidance area color, instruction text area color, and instruction text.

### Face guidance area color

![image](/assets/android_sdk_9.png)

Copy color below to res/values/colors.xml and edit color to your desire
```
<color name="face_area">#C8000000</color>
```

### Instruction text area color

![image](/assets/android_sdk_10.png)

Copy color below to res/values/colors.xml and edit color to your desire
```
<color name="instruction_area">#CB2B2B2B</color>
```

### Instruction text

![image](/assets/android_sdk_11.png)

Copy string below to res/values/strings.xml and edit string to your desire
```
<string name="left_orientation" translatable="false">Look over your left shoulder (30°)</string>
<string name="right_orientation" translatable="false">Look over your right shoulder (30°)</string>
<string name="finished" translatable="false">Finished</string>
<string name="repeat_from_start" translatable="false">Repeat from start in 1.5 seconds</string>
<string name="eye_blink" translatable="false">Blink both of your eyes</string>
```