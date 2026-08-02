package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrossfadeTimingPolicyTest {
	@Test
	public void startsAtBeginningWhenFadeWindowIsReachedExactly() {
		assertEquals(0, CrossfadeTimingPolicy.elapsedAtStartMs(3000, 3000));
	}

	@Test
	public void compensatesWhenShortFadeStartsLate() {
		assertEquals(500, CrossfadeTimingPolicy.elapsedAtStartMs(3000, 2500));
		assertEquals(2500, CrossfadeTimingPolicy.elapsedAtStartMs(3000, 500));
	}

	@Test
	public void clampsCompensationToFadeWindow() {
		assertEquals(0, CrossfadeTimingPolicy.elapsedAtStartMs(3000, 4000));
		assertEquals(3000, CrossfadeTimingPolicy.elapsedAtStartMs(3000, -250));
		assertEquals(0, CrossfadeTimingPolicy.elapsedAtStartMs(0, 0));
	}
}
