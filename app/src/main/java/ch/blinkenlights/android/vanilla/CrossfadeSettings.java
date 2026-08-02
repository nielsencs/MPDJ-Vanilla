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

public final class CrossfadeSettings {
	public static final int MAX_SECONDS = 20;

	private final int mSeconds;

	private CrossfadeSettings(int seconds) {
		mSeconds = seconds;
	}

	public static CrossfadeSettings fromSeconds(int seconds) {
		if (seconds < 0) {
			seconds = 0;
		} else if (seconds > MAX_SECONDS) {
			seconds = MAX_SECONDS;
		}
		return new CrossfadeSettings(seconds);
	}

	public boolean isEnabled() {
		return mSeconds > 0;
	}

	public int getSeconds() {
		return mSeconds;
	}

	public int getDurationMs() {
		return mSeconds * 1000;
	}
}
