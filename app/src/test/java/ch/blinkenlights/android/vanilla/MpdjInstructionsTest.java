package ch.blinkenlights.android.vanilla;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MpdjInstructionsTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void loadsMpdjInstructionsCorrectly() throws IOException {
		File audioFile = tempFolder.newFile("test_track.mp3");
		File metaFile = new File(audioFile.getAbsolutePath() + ".mpdj");

		String json = "{\n" +
				"  \"startOffsetMs\": 3730,\n" +
				"  \"endOffsetMs\": 322000,\n" +
				"  \"fadeInMs\": 11270,\n" +
				"  \"fadeOutMs\": 5000,\n" +
				"  \"loops\": [\n" +
				"    {\n" +
				"      \"pointA\": 264700,\n" +
				"      \"pointB\": 322000,\n" +
				"      \"count\": 2\n" +
				"    }\n" +
				"  ]\n" +
				"}";

		try (FileWriter writer = new FileWriter(metaFile)) {
			writer.write(json);
		}

		MpdjInstructions instructions = MpdjInstructions.loadForPath(audioFile.getAbsolutePath());
		assertNotNull(instructions);
		assertEquals(3730, instructions.startOffsetMs);
		assertEquals(322000, instructions.endOffsetMs);
		assertEquals(11270, instructions.fadeInMs);
		assertEquals(5000, instructions.fadeOutMs);
		assertEquals(1, instructions.loops.size());

		MpdjInstructions.Loop loop = instructions.loops.get(0);
		assertEquals(264700, loop.pointA);
		assertEquals(322000, loop.pointB);
		assertEquals(2, loop.remainingCount);
	}

	@Test
	public void returnsNullForMissingFile() {
		MpdjInstructions instructions = MpdjInstructions.loadForPath("non_existent_path.mp3");
		assertNull(instructions);
	}
}
