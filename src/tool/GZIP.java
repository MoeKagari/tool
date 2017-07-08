package tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
	public static byte[] decompress(byte[] bytes) {
		try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes)); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			int len;
			byte buffer[] = new byte[1024];
			while ((len = gis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			return baos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	public static byte[] compress(byte[] bytes) {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ByteArrayOutputStream baos = new ByteArrayOutputStream(); GZIPOutputStream gos = new GZIPOutputStream(baos)) {
			int len;
			byte buffer[] = new byte[1024];
			while ((len = bais.read(buffer)) != -1) {
				gos.write(buffer, 0, len);
			}
			gos.finish();
			gos.flush();
			return baos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
}
