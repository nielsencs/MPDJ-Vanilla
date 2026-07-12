#!/bin/sh

# This script can be used to generate PNGs from the source SVG artwork.
#
# Inherited Vanilla Music drawable icons are generated from orig/drawable/*.svgz.
# MPDJ Vanilla launcher icons are generated from artwork/icon/*.svg.
# If the PNGs already exist and are newer than the SVG, they will not be recreated.
#
# Requires:
# - inkscape

set -eu

INKSCAPE=${INKSCAPE:-}
if [ -z "$INKSCAPE" ]; then
	if command -v inkscape >/dev/null 2>&1; then
		INKSCAPE=inkscape
	else
		echo "error: Inkscape not found. Install it or set INKSCAPE=/path/to/inkscape." >&2
		exit 1
	fi
fi

export_png() {
	path=$1
	png=$2
	dpi=$3

	if [ "$path" -nt "$png" ] || [ ! -e "$png" ]; then
		mkdir -p "$(dirname "$png")"
		"$INKSCAPE" "$path" \
			--export-area-page \
			--export-dpi="$dpi" \
			--export-type=png \
			--export-filename="$png"
		echo
	fi
}

gen() {
	type=$1
	path=$2
	res=$3
	dpi=$4

	name=$(basename "$path" .svgz)
	export_png "$path" "app/src/main/res/$type-$res/$name.png" "$dpi"
}

gen_named() {
	type=$1
	path=$2
	name=$3
	res=$4
	dpi=$5

	export_png "$path" "app/src/main/res/$type-$res/$name.png" "$dpi"
}

gen_launcher_density() {
	res=$1
	dpi=$2

	gen_named mipmap artwork/icon/mpdj-vanilla-icon_48.svg icon "$res" "$dpi"
	gen_named mipmap artwork/icon/ic_normal_background.svg ic_normal_background "$res" "$dpi"
	gen_named mipmap artwork/icon/ic_normal_foreground.svg ic_normal_foreground "$res" "$dpi"
}

for i in orig/drawable/*.svgz; do
	gen drawable "$i" mdpi 96
	gen drawable "$i" hdpi 144
	gen drawable "$i" xhdpi 192
	gen drawable "$i" xxhdpi 288
done

gen_launcher_density mdpi 96
gen_launcher_density hdpi 144
gen_launcher_density xhdpi 192
gen_launcher_density xxhdpi 288
gen_launcher_density xxxhdpi 384
