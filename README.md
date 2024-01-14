#This app show hot to used AppSerach Library in JetpackCompose and Kotlin

Gradle Dependencies

plugins {
   id 'com.android.application'
   id 'org.jetbrains.kotlin.android'
   id 'kotlin-kapt'
}

composeOptions {
   kotlinCompilerExtensionVersion '1.5.7'
}


implementation("androidx.appsearch:appsearch:1.1.0-alpha03")
kapt("androidx.appsearch:appsearch-compiler:1.1.0-alpha03")
implementation("androidx.appsearch:appsearch-local-storage:1.1.0-alpha03")


plugins {
   id 'com.android.application' version '8.0.2' apply false
   id 'com.android.library' version '8.0.2' apply false
   id 'org.jetbrains.kotlin.android' version '1.9.21' apply false
}

Screenshot

![img2](https://github.com/HusseinKamal/AppSearch/assets/29864161/7b82ee79-1880-466c-925e-06c13b3406f0)

![img1](https://github.com/HusseinKamal/AppSearch/assets/29864161/1d9f4a7a-1980-4561-9bbb-02333f70daec)

[search.webm](https://github.com/HusseinKamal/AppSearch/assets/29864161/2de290c4-62b0-436e-bc4d-202694e33b27)



