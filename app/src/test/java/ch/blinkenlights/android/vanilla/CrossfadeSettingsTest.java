package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrossfadeSettingsTest {
	@Test
	public void zeroSecondsDisablesCrossfade() {
		CrossfadeSettings settings = CrossfadeSettings.fromSeconds(0);

		assertFalse(settings.isEnabled());
		assertEquals(0, settings.getSeconds());
		assertEquals(0, settings.getDurationMs());
	}

	@Test
	public void positiveSecondsEnableCrossfadeAndConvertToMilliseconds() {
		CrossfadeSettings settings = CrossfadeSettings.fromSeconds(5);

		assertTrue(settings.isEnabled());
		assertEquals(5, settings.getSeconds());
		assertEquals(5000, settings.getDurationMs());
	}

	@Test
	public void settingsClampToSafeRange() {
		assertEquals(0, CrossfadeSettings.fromSeconds(-3).getSeconds());
		assertEquals(CrossfadeSettings.MAX_SECONDS,
				CrossfadeSettings.fromSeconds(CrossfadeSettings.MAX_SECONDS + 20).getSeconds());
	}
}
