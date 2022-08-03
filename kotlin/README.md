# android-face-liveness

![icon](./assets/icon.png)

Nodeflux Face Liveness demo application with SDK sdk-liveness-1.0.2.aar

## Requirement

- Minimal Android Studio Chipmunk (2021.2.1 Patch 1)
- Minimal Android Gradle Plugin Version: 7.2.1
- Minimal Gradle version : 7.4.2
- Minimal Java Version: 11
- Minimal Kotlin Version: 1.7.0
- Minimal compileSdkVersion: 32
- Minimal buildToolsVersion: 30.0.3

## Getting Started

**Please notes this method is just for example, you shouldn't save secrets in resources.**

Setup key first, you can get your key at [here](https://cloud.nodeflux.io/). Create secrets.xml under `values` folder and put your access_key and secret_key inside
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <item  name="access_key" type="string">{ACCESS_KEY}</item >
    <item  name="secret_key" type="string">{SECRET_KEY}</item >
</resources>
```

After that you're good to go and you're ready to build your application.

For more information visit [here](https://www.nodeflux.io/) or you can contact us at [here](https://www.nodeflux.io/Contact-Us)

**Configure Nodeflux Liveness SDK**

Before you start Nodeflux Liveness SDK Activity, if you want to change any sdk default settings, specify those settings with NodefluxLivenessSDKOptions Object. You can change the following settings:

| Settings                     | Data Type | Descriptions                                                      | Default Value |
| ---------------------------- |:---------:| -----------------------------------------------------------------:| ------------- |
| setAccessKey                 | String    | optional (required if you using basic implementation)             | nil           |
| setSecretKey                 | String    | optional (required if you using basic implementation)             | nil           |
| setSubmissionToken           | String    | optional (required if you using submission_token implementation)  | nil           |
| setThreshold                 | Double    | optional (number of threshold for acceptence score)               | 0.7           |
| setActiveLivenessFlag        | Boolean   | optional (boolean flag to activate or deactivate active liveness) | true          |
| setImageQualityFilter        | Boolean   | optional (boolean flag to activate or deactivate iqa )            | nil           |
| setImageQualityAssessment    | String    | optional (custom value for iqa parameter)                         | nil           |
| setTimeoutThreshold          | Int       | optional (millisecond value of active liveness duration)          | 15000         |
| setGestureToleranceThreshold | Int       | optional (millisecond value of each gesture tollerance)           | 3000          |


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