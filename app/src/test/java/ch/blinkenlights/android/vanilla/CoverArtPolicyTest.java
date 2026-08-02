package ch.blinkenlights.android.vanilla;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CoverArtPolicyTest {
	@Test
	public void songAlbumAndArtistRowsCanUseRealArtwork() {
		assertTrue(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_SONG));
		assertTrue(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_ALBUM));
		assertTrue(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_ARTIST));
	}

	@Test
	public void nonArtworkGroupRowsStillUsePlaceholders() {
		assertFalse(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_GENRE));
		assertFalse(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_PLAYLIST));
		assertFalse(CoverArtPolicy.canUseRepresentativeSongCover(MediaUtils.TYPE_INVALID));
	}
}
