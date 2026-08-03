package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CrossfadeControllerTest {
	@Test
	public void followsOneExplicitTransitionFromArmedToHandoff() {
		CrossfadeController controller = new CrossfadeController();

		int transition = controller.arm(3000);
		assertEquals(CrossfadeController.State.ARMED, controller.getState());
		assertFalse(controller.shouldStart(transition, 3001));
		assertTrue(controller.shouldStart(transition, 3000));

		assertTrue(controller.start(transition));
		assertEquals(CrossfadeController.State.FADING, controller.getState());
		assertEquals(0.0f, controller.fadeInFactor(transition, 3000), 0.0001f);
		// With equal-power crossfade, at midpoint (remaining 1500 of 3000):
		// fadeInFactor = sin(1500 / 3000 * PI / 2) = sin(PI / 4) = sqrt(2)/2 ≈ 0.7071
		assertEquals(0.7071f, controller.fadeInFactor(transition, 1500), 0.0001f);
		assertEquals(1.0f, controller.fadeInFactor(transition, 0), 0.0001f);

		assertTrue(controller.beginHandoff(transition));
		assertEquals(CrossfadeController.State.HANDOFF, controller.getState());
	}

	@Test
	public void rejectsMessagesFromCancelledTransition() {
		CrossfadeController controller = new CrossfadeController();
		int cancelled = controller.arm(3000);
		controller.cancel();
		int current = controller.arm(5000);

		assertFalse(controller.shouldStart(cancelled, 0));
		assertFalse(controller.start(cancelled));
		assertEquals(0.0f, controller.fadeInFactor(cancelled, 0), 0.0001f);
		assertTrue(controller.shouldStart(current, 5000));
	}

	@Test
	public void cancellationAlwaysReturnsToIdle() {
		CrossfadeController controller = new CrossfadeController();
		int transition = controller.arm(4000);
		controller.start(transition);

		controller.cancel();

		assertEquals(CrossfadeController.State.IDLE, controller.getState());
		assertFalse(controller.isFading());
	}

	@Test
	public void zeroDurationRemainsIdle() {
		CrossfadeController controller = new CrossfadeController();
		int transition = controller.arm(0);

		assertEquals(CrossfadeController.State.IDLE, controller.getState());
		assertFalse(controller.shouldStart(transition, 0));
		assertFalse(controller.start(transition));
	}

	@Test
	public void duplicateStartAndStaleHandoffAreRejected() {
		CrossfadeController controller = new CrossfadeController();
		int first = controller.arm(3000);
		assertTrue(controller.start(first));
		assertFalse(controller.start(first));

		int second = controller.arm(3000);
		assertFalse(controller.beginHandoff(first));
		assertTrue(controller.start(second));
		assertTrue(controller.beginHandoff(second));
		assertFalse(controller.beginHandoff(second));
	}
}
