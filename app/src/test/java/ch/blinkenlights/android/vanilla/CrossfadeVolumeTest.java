package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrossfadeVolumeTest {
	@Test
	public void volumeFallsLinearlyAcrossFadeOut() {
		assertEquals(1.0f, CrossfadeVolume.fadeOutFactor(0, 5000), 0.0001f);
		assertEquals(0.5f, CrossfadeVolume.fadeOutFactor(2500, 5000), 0.0001f);
		assertEquals(0.0f, CrossfadeVolume.fadeOutFactor(5000, 5000), 0.0001f);
	}

	@Test
	public void volumeRisesLinearlyAcrossFadeIn() {
		assertEquals(0.0f, CrossfadeVolume.fadeInFactor(0, 5000), 0.0001f);
		assertEquals(0.5f, CrossfadeVolume.fadeInFactor(2500, 5000), 0.0001f);
		assertEquals(1.0f, CrossfadeVolume.fadeInFactor(5000, 5000), 0.0001f);
	}

	@Test
	public void volumeFactorsClampOutsideFadeWindow() {
		assertEquals(1.0f, CrossfadeVolume.fadeOutFactor(-500, 5000), 0.0001f);
		assertEquals(0.0f, CrossfadeVolume.fadeOutFactor(6000, 5000), 0.0001f);
		assertEquals(0.0f, CrossfadeVolume.fadeInFactor(-500, 5000), 0.0001f);
		assertEquals(1.0f, CrossfadeVolume.fadeInFactor(6000, 5000), 0.0001f);
	}
}
