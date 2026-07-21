/*
 * Copyright (C) 2026 Carl Nielsen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ch.blinkenlights.android.vanilla;

/**
 * Decides how an album opened from a currently-limited artist list should be
 * scoped. A whole-album view is better for mostly-single-artist albums with a
 * few guest tracks; an artist-scoped view is useful for various-artists albums.
 */
public final class AlbumLimiterPolicy {
	private AlbumLimiterPolicy() {
		// Utility class.
	}

	public static Limiter maybeRestrictAlbumToCurrentArtist(Limiter albumLimiter, Limiter currentLimiter, boolean restrictToCurrentArtist)
	{
		if (!restrictToCurrentArtist || albumLimiter == null || currentLimiter == null || currentLimiter.type != MediaUtils.TYPE_ARTIST) {
			return albumLimiter;
		}

		int albumNameStart = 0;
		if (currentLimiter.names.length > 0 && albumLimiter.names.length > 0) {
			String currentLast = currentLimiter.names[currentLimiter.names.length - 1];
			String albumFirst = albumLimiter.names[0];
			if (currentLast == null ? albumFirst == null : currentLast.equals(albumFirst)) {
				albumNameStart = 1;
			}
		}

		String[] names = new String[currentLimiter.names.length + albumLimiter.names.length - albumNameStart];
		System.arraycopy(currentLimiter.names, 0, names, 0, currentLimiter.names.length);
		System.arraycopy(albumLimiter.names, albumNameStart, names, currentLimiter.names.length, albumLimiter.names.length - albumNameStart);
		return new Limiter(MediaUtils.TYPE_ALBUM, names, albumLimiter.data + " AND " + currentLimiter.data);
	}
}
