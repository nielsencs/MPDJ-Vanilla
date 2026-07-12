# Vanilla Music upstream issue triage

This note records known upstream Vanilla Music issues considered before MPDJ Vanilla's first public release.

## Review scope

- Review date: 2026-07-09.
- Upstream tracker reviewed: `vanilla-music/vanilla` open issues.
- Open non-PR issues reviewed: 255.
- MPDJ Vanilla branch reviewed: `mpdj-public-release-readiness` at `eaa47087`.
- No device or emulator reproduction was run during this review. “Likely fixed” means the MPDJ Vanilla branch appears to contain a targeted or overlapping fix, and should be verified before strong public claims.

## Already fixed or likely fixed in MPDJ Vanilla

These issue families appear to be fixed, substantially improved, or directly addressed by current MPDJ Vanilla work:

- Android foreground-service / phone-call interruption crash paths.
- Database dump failure under scoped storage.
- Same-title albums by different artists or album artists being merged together.
- Compilation and album-artist display/filtering problems.
- Stale notification / media-session metadata showing the previous track.
- Stale or wrong album artwork caused by cache/key problems.
- Some Android 13/14 notification and background-service compatibility problems.
- Public identity and release metadata: MPDJ Vanilla app name, package identity, icon work, README, fastlane metadata, and release-signing notes.

Treat these as “likely fixed” until they have been checked on a real device or fixture library.

## Needs manual verification before stronger claims

These are worth checking before a public release, especially because they affect first-run, playback, library correctness, or file compatibility:

1. Fresh install and permissions on Android 13–15:
   - media permission flow;
   - notification permission;
   - library scan after first launch;
   - files outside `/Music` where Android exposes them normally.
2. Notification and media-session behaviour:
   - lockscreen / quick settings current-song display;
   - play/pause/next/previous;
   - repeated play-pause taps;
   - headset or Bluetooth controls if available.
3. Album and artist fixtures:
   - same album title by different artists;
   - album-artist-only albums;
   - compilation albums;
   - embedded and folder artwork changes.
4. Playlist and file-extension path:
   - adding `.mp4` audio and `.oga` files to queue and internal playlists;
   - playlist import/export only if MPDJ Vanilla wants to promise that workflow.
5. ReplayGain and tag parsing:
   - M4A/ALAC ReplayGain;
   - Opus ReplayGain;
   - FLAC/MP3 comparison fixture.
6. Opus behaviour:
   - embedded artwork;
   - opening from another file manager;
   - untagged-title fallback.
7. RTL/locale smoke test:
   - Persian or Arabic locale startup/navigation;
   - one search/sort/library check after locale change.
8. Playback-transition checks:
   - gapless or near-gapless track pair;
   - automatic next-track transition;
   - rapid next/previous with large artwork.

## Intentionally deferred unless seen locally

These should not block the first MPDJ Vanilla release unless the project wants to promise the behaviour or the bug reproduces in normal MPDJ Vanilla use:

- Broad SAF / external SD-card support beyond the simple local-player path.
- Delete-from-device semantics under modern scoped storage.
- Large playlist-management redesigns.
- `.cue` support and other format-expansion requests.
- Android Auto, car mode, assistant/alarm integration, widget expansions, and other broad feature requests.
- Old single-device reports without a current repro or sample.
- Plugin-centric flows unless MPDJ Vanilla decides to support those plugins as part of its public promise.

## Top pre-release smoke tests

Before tagging or publishing a first public build, run this minimal smoke suite:

- Build debug and release artefacts.
- Fresh install on a current Android device.
- Grant permissions and scan a small local music library.
- Play, pause, skip, seek, and resume from notification/lockscreen.
- Check current-song metadata and artwork after several track changes.
- Verify album grouping with a tiny fixture set covering compilations and duplicate album titles.
- Add a few files to queue and playlist, including one `.oga` or audio-only `.mp4` if available.
- Try an RTL locale once if a device/emulator is available.
- Confirm README, app label, icon, fastlane title/description, package identity, and signing docs all say MPDJ Vanilla rather than implying this is the original Vanilla Music app.

## Release posture

MPDJ Vanilla should make a clean break in identity while preserving honest lineage:

- Credit Vanilla Music and retain GPLv3 obligations.
- Do not use old Vanilla Music store badges or distribution links for MPDJ builds.
- Say clearly that MPDJ Vanilla is a maintained fork, not the original app.
- Avoid claiming inherited upstream issues are fixed until they are verified in MPDJ Vanilla.
