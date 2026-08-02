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

public final class CrossfadeReadaheadPolicy {
	private CrossfadeReadaheadPolicy() {
	}

	public static Result apply(int crossfadeSeconds,
	                           boolean currentReadaheadEnabled,
	                           boolean managementActive,
	                           boolean savedReadaheadEnabled) {
		if (crossfadeSeconds > 0) {
			return new Result(true, true,
					managementActive ? savedReadaheadEnabled : currentReadaheadEnabled);
		}

		if (managementActive) {
			return new Result(savedReadaheadEnabled, false, savedReadaheadEnabled);
		}

		return new Result(currentReadaheadEnabled, false, savedReadaheadEnabled);
	}

	public static final class Result {
		public final boolean readaheadEnabled;
		public final boolean managementActive;
		public final boolean savedReadaheadEnabled;

		private Result(boolean readaheadEnabled,
		               boolean managementActive,
		               boolean savedReadaheadEnabled) {
			this.readaheadEnabled = readaheadEnabled;
			this.managementActive = managementActive;
			this.savedReadaheadEnabled = savedReadaheadEnabled;
		}
	}
}
