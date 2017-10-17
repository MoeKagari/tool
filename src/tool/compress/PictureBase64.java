package tool.compress;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class PictureBase64 {

	/*                  encode                             */

	public static String encode(String filepath) throws IOException {
		try {
			return encode(new File(filepath));
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public static String encode(File file) throws IOException {
		try {
			return encode(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	public static String encode(InputStream is) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			return encode(baos.toByteArray());
		} catch (IOException e) {
			throw e;
		}
	}

	public static String encode(byte[] src) {
		return Base64.getEncoder().encodeToString(src);
	}

	/*                  decode                            	*/

	public static byte[] decode(byte[] src) {
		return Base64.getDecoder().decode(src);
	}

	public static byte[] decode(String src) {
		return Base64.getDecoder().decode(src);
	}

}
