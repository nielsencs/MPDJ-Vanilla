package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrossfadeReadaheadPolicyTest {
	@Test
	public void enablingCrossfadeStoresDisabledReadaheadAndForcesItOn() {
		CrossfadeReadaheadPolicy.Result result = CrossfadeReadaheadPolicy.apply(
				5, false, false, false);

		assertTrue(result.readaheadEnabled);
		assertTrue(result.managementActive);
		assertFalse(result.savedReadaheadEnabled);
	}

	@Test
	public void disablingCrossfadeRestoresPreviouslyDisabledReadahead() {
		CrossfadeReadaheadPolicy.Result result = CrossfadeReadaheadPolicy.apply(
				0, true, true, false);

		assertFalse(result.readaheadEnabled);
		assertFalse(result.managementActive);
		assertFalse(result.savedReadaheadEnabled);
	}

	@Test
	public void disablingCrossfadeRestoresPreviouslyEnabledReadahead() {
		CrossfadeReadaheadPolicy.Result result = CrossfadeReadaheadPolicy.apply(
				0, true, true, true);

		assertTrue(result.readaheadEnabled);
		assertFalse(result.managementActive);
		assertTrue(result.savedReadaheadEnabled);
	}

	@Test
	public void crossfadeDisabledWithoutManagementLeavesReadaheadAlone() {
		CrossfadeReadaheadPolicy.Result result = CrossfadeReadaheadPolicy.apply(
				0, true, false, false);

		assertTrue(result.readaheadEnabled);
		assertFalse(result.managementActive);
	}
}
