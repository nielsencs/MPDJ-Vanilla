package ch.blinkenlights.android.vanilla;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AlbumLimiterPolicyTest {
	@Test
	public void keepsWholeAlbumWhenPreferenceDisabled() {
		Limiter artist = new Limiter(MediaUtils.TYPE_ARTIST, new String[] { "Main Artist" }, "artist_id=7");
		Limiter album = new Limiter(MediaUtils.TYPE_ALBUM, new String[] { "Main Artist", "Guest Album" }, "album_id=42");

		Limiter result = AlbumLimiterPolicy.maybeRestrictAlbumToCurrentArtist(album, artist, false);

		assertSame(album, result);
	}

	@Test
	public void combinesAlbumAndArtistWhenPreferenceEnabled() {
		Limiter artist = new Limiter(MediaUtils.TYPE_ARTIST, new String[] { "Main Artist" }, "artist_id=7");
		Limiter album = new Limiter(MediaUtils.TYPE_ALBUM, new String[] { "Main Artist", "Guest Album" }, "album_id=42");

		Limiter result = AlbumLimiterPolicy.maybeRestrictAlbumToCurrentArtist(album, artist, true);

		assertEquals(MediaUtils.TYPE_ALBUM, result.type);
		assertArrayEquals(new String[] { "Main Artist", "Guest Album" }, result.names);
		assertEquals("album_id=42 AND artist_id=7", result.data);
	}

	@Test
	public void ignoresNonArtistLimiterEvenWhenPreferenceEnabled() {
		Limiter genre = new Limiter(MediaUtils.TYPE_GENRE, new String[] { "Rock" }, "genre_id=5");
		Limiter album = new Limiter(MediaUtils.TYPE_ALBUM, new String[] { "Main Artist", "Guest Album" }, "album_id=42");

		Limiter result = AlbumLimiterPolicy.maybeRestrictAlbumToCurrentArtist(album, genre, true);

		assertSame(album, result);
	}
}
