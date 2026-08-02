package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrossfadeWarmupPolicyTest {
	@Test
	public void warmsPreparedPlayerShortlyBeforeFadeWindow() {
		assertTrue(CrossfadeWarmupPolicy.shouldWarmup(3000, 3750, 1000, false));
		assertTrue(CrossfadeWarmupPolicy.shouldWarmup(9000, 9500, 1000, false));
	}

	@Test
	public void doesNotWarmTooEarlyOrAfterFadeStarts() {
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(3000, 4500, 1000, false));
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(3000, 3000, 1000, false));
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(3000, 2500, 1000, false));
	}

	@Test
	public void doesNotWarmWhenAlreadyPlayingOrInvalid() {
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(3000, 3750, 1000, true));
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(0, 750, 1000, false));
		assertFalse(CrossfadeWarmupPolicy.shouldWarmup(3000, 3750, 0, false));
	}
}
