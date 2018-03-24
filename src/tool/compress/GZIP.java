package tool.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
	public static Optional<byte[]> compressOptional(byte[] data) {
		return Optional.ofNullable(compress(data));
	}

	public static byte[] compress(byte[] data) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); GZIPOutputStream gos = new GZIPOutputStream(baos)) {

			gos.write(data);
			gos.finish();
			gos.flush();

			return baos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	public static Optional<byte[]> decompressOptional(byte[] data) {
		return Optional.ofNullable(decompress(data));
	}

	public static byte[] decompress(byte[] data) {
		try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(data)); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			int len;
			byte[] buffer = new byte[1024];
			while ((len = gis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			return baos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
