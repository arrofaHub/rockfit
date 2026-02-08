# rockfit

Rockfit is an Android-first national sports performance and health platform.

## Local setup

1) Install Android Studio.
2) Open the project in Android Studio.
3) Install SDK components (SDK Manager):
   - Android 16 (API 36) SDK Platform
   - Android SDK Platform-Tools
   - Android SDK Build-Tools 36.0.0
   - Android Emulator
   - Android SDK Command-line Tools (latest)
4) Ensure the Android SDK is installed (compileSdk 36).
5) Set admin credentials (do NOT commit these):
   - In ~/.gradle/gradle.properties add:
     - ROCKFIT_ADMIN_EMAIL=farhad.hossain.ink@gmail.com
     - ROCKFIT_ADMIN_PASSWORD=admin
6) Sync Gradle and run the app.

## Notes
- Package/namespace: com.rockfit.ksa (Android app IDs must be dot-separated).
- Per-app language switching is enabled (English/Arabic).
- The included gradlew script is a minimal fallback. Use Android Studio or install Gradle to run builds.
