/****************************************************************************************
 * Copyright (c) 2012 Kostas Spyropoulos <inigo.aldana@gmail.com>                       *
 *                                                                                      *
 * This program is free software; you can redistribute it and/or modify it under        *
 * the terms of the GNU General Public License as published by the Free Software        *
 * Foundation; either version 3 of the License, or (at your option) any later           *
 * version.                                                                             *
 *                                                                                      *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY      *
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A      *
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.             *
 *                                                                                      *
 * You should have received a copy of the GNU General Public License along with         *
 * this program.  If not, see <http://www.gnu.org/licenses/>.                           *
 ****************************************************************************************/
package com.ichi2.libanki.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ichi2.libanki.Collection;
import com.ichi2.libanki.Storage;

import android.content.Context;
import android.util.Log;

public class Shared {
	public static final String TAG = "AnkiDroidTest";
	
	public static File getUpgradeDeckPath(Context ctx) throws IOException {
		return getUpgradeDeckPath(ctx, "anki12.anki");
	}
	public static File getUpgradeDeckPath(Context ctx, String name) throws IOException {
		File srcdir = ctx.getExternalCacheDir();
		Log.i("AnkiDroidTest", "Target dir: " + srcdir.getAbsolutePath());
		File dst = File.createTempFile("tmp", ".anki2", srcdir);
		InputStream is = ctx.getResources().getAssets().open(name);
		byte[] buf = new byte[32768];
		OutputStream output = new BufferedOutputStream(new FileOutputStream(dst));
		int len;
		while ((len = is.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
		output.close();
		is.close();
		return dst;
	}
	
	public static File copyFileFromAssets(Context ctx, String name, File destDir) throws IOException {
		File dst = new File(destDir, name);
		InputStream is = ctx.getResources().getAssets().open(name);
		byte[] buf = new byte[32768];
		OutputStream output = new BufferedOutputStream(new FileOutputStream(dst));
		int len;
		while ((len = is.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
		output.close();
		is.close();
		return dst;
	}
	
	public static Collection getEmptyDeck(Context ctx) {
		return getEmptyDeck(ctx, false);
	}
	public static Collection getEmptyDeck(Context ctx, boolean server) {
		File dstdir = ctx.getExternalCacheDir();
		File dst;
		try {
			dst = File.createTempFile("empty", ".anki2", dstdir);
		} catch (IOException e) {
			Log.e(TAG, "Shared.getEmptyDeck: ", e);
			return null;
		}
		if (dst.exists()) {
			dst.delete();
		}
		return Storage.Collection(dst.getAbsolutePath(), server);
	}

    public static int[] toPrimitiveInt(Integer[] array) {
        int[] results = new int[array.length];
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                results[i] = array[i].intValue();
            }
        }
        return results;
    }
}
