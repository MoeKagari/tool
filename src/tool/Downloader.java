package tool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class Downloader {
	public static byte[] download(String urlStr) {
		return download(urlStr, -1);
	}

	public static byte[] download(String urlStr, String proxyHost, int proxyPort) throws Exception {
		Proxy proxy = null;
		if (proxyHost != null && proxyPort > 0) {
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
		}

		HttpURLConnection huc;
		if (proxy != null) {
			huc = (HttpURLConnection) new URL(urlStr).openConnection(proxy);
		} else {
			huc = (HttpURLConnection) new URL(urlStr).openConnection();
		}

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream is = huc.getInputStream()) {
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			return baos.toByteArray();
		} finally {
			huc.disconnect();
		}
	}

	public static byte[] download(String urlStr, int proxyPort) {
		try {
			return download(urlStr, "127.0.0.1", proxyPort);
		} catch (Exception e) {
			return null;
		}
	}
}
