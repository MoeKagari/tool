package tool.compress;

import java.util.Optional;

public class Base64 {
	public static Optional<byte[]> compress(byte[] data) {
		try {
			return Optional.of(java.util.Base64.getEncoder().encode(data));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static Optional<byte[]> decompress(byte[] data) {
		try {
			return Optional.of(java.util.Base64.getDecoder().decode(data));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
