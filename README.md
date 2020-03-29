# NSFW(Nude Content) Detector

NSFW Content detector using
[Firebase MlKit (AutoML)](https://firebase.google.com/docs/ml-kit/automl-image-labeling)
. This module developed using Firebase on device AutoML and
[Firebase Vision](https://firebase.google.com/docs/ml-kit/android/label-images).

This module contains pre trained
[TensorFlow Lite(tflite)](https://www.tensorflow.org/lite) model that
compatible with AutoML.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)[![](https://jitpack.io/v/adawoud/BottomSheetTimeRangePicker.svg)](https://jitpack.io/#adawoud/BottomSheetTimeRangePicker)

## Installation

1. **Add the JitPack repository to your build file**  
   Add it in your project build.gradle at the end of repositories:

```
buildscript {
    ...
    dependencies {
        ...
        classpath 'com.google.gms:google-services:<latest_version>'
    }
}

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. **Add nsfw-detector and rest of the firebase dependencies**  
   Add it in your app build.gradle

```
...
android {
    ...
    aaptOptions {
        noCompress "tflite"
    }
}

dependencies {
    ...
    api "com.google.firebase:firebase-ml-vision:<latest_version>"
    implementation "com.google.firebase:firebase-ml-vision-automl:<latest_version>"
    implementation "com.google.firebase:firebase-ml-model-interpreter:<latest_version>"

    implementation 'com.github.nipunru:nsfw-detector:0.0.3'
}

apply plugin: 'com.google.gms.google-services'
```

3. **Run the gradle sync**

>Note: Since this is on device image method, It will take some time to
>download the tflite model.

4. **Enable Firebase** Please follow
   [this documentation](https://firebase.google.com/docs/android/setup)
   Make sure to add `google-service.json` file to your project.

## Usage

- **Initialize Firebase**  
  Add this before call the `NSFWDetector`

```
FirebaseApp.initializeApp(context)
```

- **Scan for NSFW**

```
val bitmap: Bitmap //Image that you want to scan
val confidenceThreshold: Float //(Optional) Default is 0.7

NSFWDetector.isNSFW(bitmap,confidenceThreshold) { isNSFW, confidence, image ->
    if (isNSFW){
        Toast.makeText(this, "NSFW with confidence: $confidence", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, "SFW with confidence: $confidence", Toast.LENGTH_SHORT).show()
    }
}
```

## License

[MIT License](LICENSE)

Copyright (c) 2020 Nipun Ruwanpathirana

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

