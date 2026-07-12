# MPDJ Vanilla icon source artwork

The editable launcher icon sources live here as plain SVG files:

- `mpdj-vanilla-icon.svg` — large editable master artwork
- `mpdj-vanilla-icon_48.svg` — 48px legacy launcher source, matching Vanilla Music's `orig/mipmap/icon.svgz` proportions
- `ic_normal_background.svg` — adaptive icon background source
- `ic_normal_foreground.svg` — adaptive icon foreground source

Generated Android icon assets live under `app/src/main/res/`:

- legacy launcher PNGs: `mipmap-*/icon.png`
- adaptive icon PNGs: `mipmap-*/ic_normal_background.png` and `mipmap-*/ic_normal_foreground.png`
- adaptive icon wrapper: `mipmap-anydpi-v26/icon.xml`

Run from the repository root:

```bash
./generate-pngs.sh
```

The script also regenerates inherited non-launcher drawable icons from `orig/drawable/*.svgz`, but MPDJ Vanilla launcher icons now come from the SVG files in this directory rather than from `orig/mipmap/*.svgz`.
