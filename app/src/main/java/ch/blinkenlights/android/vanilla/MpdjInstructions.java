/*
 * Copyright (C) 2026 Carl Nielsen and contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.blinkenlights.android.vanilla;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MpdjInstructions {
	private static final String TAG = "MpdjInstructions";

	public static class Loop {
		public final int pointA; // Milliseconds to jump to
		public final int pointB; // Milliseconds triggering the jump
		public int remainingCount;

		public Loop(int pointA, int pointB, int count) {
			this.pointA = pointA;
			this.pointB = pointB;
			this.remainingCount = count;
		}
	}

	public final int startOffsetMs;
	public final int endOffsetMs;
	public final int fadeInMs;
	public final int fadeOutMs;
	public final List<Loop> loops = new ArrayList<>();

	public MpdjInstructions(int startOffsetMs, int endOffsetMs, int fadeInMs, int fadeOutMs) {
		this.startOffsetMs = startOffsetMs;
		this.endOffsetMs = endOffsetMs;
		this.fadeInMs = fadeInMs;
		this.fadeOutMs = fadeOutMs;
	}

	private static int parseInteger(String json, String key, int defaultValue) {
		String patternStr = "\"" + key + "\"\\s*:\\s*(-?\\d+)";
		Matcher m = Pattern.compile(patternStr).matcher(json);
		if (m.find()) {
			try {
				return Integer.parseInt(m.group(1));
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * Looks for an .mpdj metadata file associated with the given song path.
	 * If one exists, parses and returns the instructions; otherwise returns null.
	 */
	public static MpdjInstructions loadForPath(String path) {
		if (path == null) {
			return null;
		}

		File file = new File(path + ".mpdj");
		if (!file.exists()) {
			// Fallback: try replacing the extension with .mpdj
			int dotIndex = path.lastIndexOf('.');
			if (dotIndex > 0) {
				file = new File(path.substring(0, dotIndex) + ".mpdj");
			}
		}

		if (!file.exists()) {
			return null;
		}

		try {
			StringBuilder sb = new StringBuilder();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}

			String json = sb.toString();
			int startOffsetMs = parseInteger(json, "startOffsetMs", 0);
			int endOffsetMs = parseInteger(json, "endOffsetMs", -1);
			int fadeInMs = parseInteger(json, "fadeInMs", 0);
			int fadeOutMs = parseInteger(json, "fadeOutMs", 0);

			MpdjInstructions inst = new MpdjInstructions(startOffsetMs, endOffsetMs, fadeInMs, fadeOutMs);

			int loopsIndex = json.indexOf("\"loops\"");
			if (loopsIndex != -1) {
				String loopsPart = json.substring(loopsIndex);
				Matcher blockMatcher = Pattern.compile("\\{([^\\}]+)\\}").matcher(loopsPart);
				while (blockMatcher.find()) {
					String block = blockMatcher.group(1);
					int pointA = parseInteger(block, "pointA", -1);
					int pointB = parseInteger(block, "pointB", -1);
					int count = parseInteger(block, "count", 1);
					if (pointA >= 0 && pointB >= 0) {
						inst.loops.add(new Loop(pointA, pointB, count));
					}
				}
			}

			return inst;
		} catch (Exception e) {
			Log.e(TAG, "Failed to load/parse MPDJ instructions file: " + file.getAbsolutePath(), e);
			return null;
		}
	}
}
