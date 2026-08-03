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

final class CrossfadeController {
	enum State { IDLE, ARMED, FADING, HANDOFF }

	private State mState = State.IDLE;
	private int mGeneration;
	private int mDurationMs;

	int arm(int durationMs) {
		mGeneration++;
		mDurationMs = Math.max(0, durationMs);
		mState = mDurationMs > 0 ? State.ARMED : State.IDLE;
		return mGeneration;
	}

	void cancel() {
		mGeneration++;
		mDurationMs = 0;
		mState = State.IDLE;
	}

	boolean shouldStart(int generation, int remainingMs) {
		return matches(generation, State.ARMED) && remainingMs <= mDurationMs;
	}

	boolean start(int generation) {
		if (!matches(generation, State.ARMED))
			return false;
		mState = State.FADING;
		return true;
	}

	boolean beginHandoff(int generation) {
		if (!matches(generation, State.FADING))
			return false;
		mState = State.HANDOFF;
		return true;
	}

	float fadeInFactor(int generation, int remainingMs) {
		if (!matches(generation, State.FADING))
			return 0.0f;
		return CrossfadeVolume.fadeInFactor(mDurationMs - remainingMs, mDurationMs);
	}

	float fadeOutFactor(int generation, int remainingMs) {
		if (!matches(generation, State.FADING))
			return 1.0f;
		return 1.0f - fadeInFactor(generation, remainingMs);
	}

	boolean isCurrent(int generation) {
		return generation == mGeneration && mState != State.IDLE;
	}

	int getGeneration() {
		return mGeneration;
	}

	boolean isFading() {
		return mState == State.FADING;
	}

	State getState() {
		return mState;
	}

	private boolean matches(int generation, State state) {
		return generation == mGeneration && mState == state;
	}
}
