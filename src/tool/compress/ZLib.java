package tool.compress;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZLib {
	public static Optional<byte[]> compressOptional(byte[] data) {
		return Optional.ofNullable(compress(data));
	}

	public static byte[] compress(byte[] data) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Deflater compresser = new Deflater();
			compresser.reset();
			compresser.setInput(data);
			compresser.finish();

			byte[] buffer = new byte[1024];
			while (!compresser.finished()) {
				int len = compresser.deflate(buffer);
				baos.write(buffer, 0, len);
			}
			compresser.end();

			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

	public static Optional<byte[]> decompressOptional(byte[] data) {
		return Optional.ofNullable(decompress(data));
	}

	public static byte[] decompress(byte[] data) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Inflater decompresser = new Inflater();
			decompresser.reset();
			decompresser.setInput(data);

			byte[] buffer = new byte[1024];
			while (!decompresser.finished()) {
				int len = decompresser.inflate(buffer);
				baos.write(buffer, 0, len);
			}
			decompresser.end();

			return baos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}
}
