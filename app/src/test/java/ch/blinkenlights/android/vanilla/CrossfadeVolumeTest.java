package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrossfadeVolumeTest {
	@Test
	public void volumeFallsAcrossFadeOutWithEqualPower() {
		// cos(0 * PI / 2) = 1.0
		assertEquals(1.0f, CrossfadeVolume.fadeOutFactor(0, 5000), 0.0001f);
		// cos(0.5 * PI / 2) = cos(PI / 4) = sqrt(2)/2 ≈ 0.7071
		assertEquals(0.7071f, CrossfadeVolume.fadeOutFactor(2500, 5000), 0.0001f);
		// cos(1.0 * PI / 2) = 0.0
		assertEquals(0.0f, CrossfadeVolume.fadeOutFactor(5000, 5000), 0.0001f);
	}

	@Test
	public void volumeRisesAcrossFadeInWithEqualPower() {
		// sin(0 * PI / 2) = 0.0
		assertEquals(0.0f, CrossfadeVolume.fadeInFactor(0, 5000), 0.0001f);
		// sin(0.5 * PI / 2) = sin(PI / 4) = sqrt(2)/2 ≈ 0.7071
		assertEquals(0.7071f, CrossfadeVolume.fadeInFactor(2500, 5000), 0.0001f);
		// sin(1.0 * PI / 2) = 1.0
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
