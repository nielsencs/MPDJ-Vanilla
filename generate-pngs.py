#!/usr/bin/env python3
"""Generate Android PNG assets from SVG/SVGZ sources.

Inherited Vanilla Music drawable icons are generated from orig/drawable/*.svgz.
MPDJ Vanilla launcher icons are generated from artwork/icon/*.svg.
Existing PNGs are only regenerated when missing or older than their source.

Requires Inkscape, or set INKSCAPE=/path/to/inkscape.
"""
from __future__ import annotations

import os
import shutil
import subprocess
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent

DENSITIES = [
    ("mdpi", 96),
    ("hdpi", 144),
    ("xhdpi", 192),
    ("xxhdpi", 288),
]

LAUNCHER_DENSITIES = [
    ("mdpi", 96),
    ("hdpi", 144),
    ("xhdpi", 192),
    ("xxhdpi", 288),
    ("xxxhdpi", 384),
]


def find_inkscape() -> str:
    configured = os.environ.get("INKSCAPE", "").strip()
    if configured:
        return configured
    found = shutil.which("inkscape")
    if found:
        return found
    print("error: Inkscape not found. Install it or set INKSCAPE=/path/to/inkscape.", file=sys.stderr)
    raise SystemExit(1)


def is_newer(source: Path, target: Path) -> bool:
    return not target.exists() or source.stat().st_mtime > target.stat().st_mtime


def export_png(inkscape: str, source: Path, target: Path, dpi: int) -> None:
    if not is_newer(source, target):
        return

    target.parent.mkdir(parents=True, exist_ok=True)
    subprocess.run(
        [
            inkscape,
            str(source),
            "--export-area-page",
            f"--export-dpi={dpi}",
            "--export-type=png",
            f"--export-filename={target}",
        ],
        cwd=ROOT,
        check=True,
    )
    print()


def drawable_name(source: Path) -> str:
    if source.name.endswith(".svgz"):
        return source.name[:-5]
    return source.stem


def generate_drawable(inkscape: str, source: Path, density: str, dpi: int) -> None:
    name = drawable_name(source)
    export_png(inkscape, source, ROOT / "app" / "src" / "main" / "res" / f"drawable-{density}" / f"{name}.png", dpi)


def generate_named(inkscape: str, res_type: str, source: Path, name: str, density: str, dpi: int) -> None:
    export_png(inkscape, source, ROOT / "app" / "src" / "main" / "res" / f"{res_type}-{density}" / f"{name}.png", dpi)


def generate_launcher_density(inkscape: str, density: str, dpi: int) -> None:
    generate_named(inkscape, "mipmap", ROOT / "artwork" / "icon" / "mpdj-vanilla-icon_48.svg", "icon", density, dpi)
    generate_named(inkscape, "mipmap", ROOT / "artwork" / "icon" / "ic_normal_background.svg", "ic_normal_background", density, dpi)
    generate_named(inkscape, "mipmap", ROOT / "artwork" / "icon" / "ic_normal_foreground.svg", "ic_normal_foreground", density, dpi)


def main() -> int:
    inkscape = find_inkscape()

    for source in sorted((ROOT / "orig" / "drawable").glob("*.svgz")):
        for density, dpi in DENSITIES:
            generate_drawable(inkscape, source, density, dpi)

    for density, dpi in LAUNCHER_DENSITIES:
        generate_launcher_density(inkscape, density, dpi)

    return 0


if __name__ == "__main__":
    raise SystemExit(main())
