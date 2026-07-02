MPDJ Vanilla
============

MPDJ Vanilla is a maintained fork of [Vanilla Music](https://github.com/vanilla-music/vanilla), preserving its simple local-music-player spirit while keeping Android builds available, policy-compliant, and ad-free.

It is a GPLv3 licensed MP3/OGG/FLAC/PCM player for Android with the following features:

* local music playback without adverts or tracking
* multiple playlist support
* grouping by artist, album or genre
* plain filesystem browsing
* ReplayGain support
* headset/Bluetooth controls
* accelerometer/shake control
* cover art support
* Simple Last.fm Scrobbler compatible broadcasts

Lineage
-------

This project is a fork of Vanilla Music. Vanilla Music is no longer available from Google Play as of June 2024, and the original maintainer warned that Play Store releases using that name may be ad-infested third-party forks.

MPDJ Vanilla keeps the original GPLv3 licence, credits the original project, and uses a separate Android application ID for new builds.

To maintain compatibility with existing Vanilla Music cache, this app continues to use the `.vanilla` directory on your storage (e.g., `/sdcard/Music/.vanilla/`) for storing and retrieving cached album artwork.

Distribution
-------------

Planned public distribution order:

1. GitHub Releases
2. F-Droid, if the metadata/build process is accepted
3. Google Play, if the release and policy work stays manageable

Until this fork has its own F-Droid package, do not use the old Vanilla Music F-Droid badge/link for MPDJ Vanilla builds.

Privacy
-------

MPDJ Vanilla is intended to be an offline local music player. The app currently requests no Internet permission, so it cannot make network connections directly.

The app requests media/storage permissions so it can find and play audio files, including files stored on removable media where Android exposes them through normal media/storage access.

Building
--------

Requirements:

* JDK compatible with the Android Gradle Plugin used by this project
* Android SDK with the configured compile SDK installed

Build a debug APK:

```sh
ANDROID_HOME=/path/to/Android/Sdk ./gradlew assembleDebug
```

Build a release APK or Android App Bundle:

```sh
ANDROID_HOME=/path/to/Android/Sdk ./gradlew assembleRelease
ANDROID_HOME=/path/to/Android/Sdk ./gradlew bundleRelease
```

Release signing is documented in [`docs/release-signing.md`](docs/release-signing.md).

Contributing
------------

Please keep changes simple, reviewable, and honest about the project’s Vanilla Music lineage. New public-facing material should say MPDJ Vanilla unless it is explicitly discussing the original Vanilla Music project.
