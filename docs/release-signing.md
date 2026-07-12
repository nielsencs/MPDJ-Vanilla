# Release signing

Android release builds must be signed before they can be installed as release APKs or uploaded as Android App Bundles.

This repository does **not** store signing keys or passwords. Keep those local and private.

## Create an upload key

For Google Play, prefer Play App Signing and use this key as the upload key:

```sh
keytool -genkeypair \
  -v \
  -keystore mpdj-vanilla-upload.jks \
  -alias mpdj-vanilla-upload \
  -keyalg RSA \
  -keysize 4096 \
  -validity 10000
```

Back up the keystore and passwords somewhere safe. Losing the upload key is recoverable through Google Play, but it is still a nuisance; losing a self-managed release key for non-Play distribution is worse.

## Local signing.properties

Create an untracked `signing.properties` file in the repository root:

```properties
MPDJ_RELEASE_STORE_FILE=/absolute/path/to/mpdj-vanilla-upload.jks
MPDJ_RELEASE_STORE_PASSWORD=your-store-password
MPDJ_RELEASE_KEY_ALIAS=mpdj-vanilla-upload
MPDJ_RELEASE_KEY_PASSWORD=your-key-password
```

Then build:

```sh
ANDROID_HOME=/path/to/Android/Sdk ./gradlew assembleRelease
ANDROID_HOME=/path/to/Android/Sdk ./gradlew bundleRelease
```

If `signing.properties` or the equivalent environment variables are absent, Gradle still builds an unsigned release APK for local checking.

## Environment-variable alternative

Instead of `signing.properties`, set:

```sh
export MPDJ_RELEASE_STORE_FILE=/absolute/path/to/mpdj-vanilla-upload.jks
export MPDJ_RELEASE_STORE_PASSWORD=your-store-password
export MPDJ_RELEASE_KEY_ALIAS=mpdj-vanilla-upload
export MPDJ_RELEASE_KEY_PASSWORD=your-key-password
```
