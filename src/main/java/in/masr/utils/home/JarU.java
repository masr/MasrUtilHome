package in.masr.utils.home;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class JarU {
	public static boolean isRunInJar(Class entryClass)
			throws ClassNotFoundException {
		// jar:file:/home/coolmore/.m2/repository/com/ebay/ddi/seller/Metadata/1.0-SNAPSHOT/Metadata-1.0-SNAPSHOT.jar!/com/ebay/ddi/seller/metadata/MetadataUtil.class
		URL classURL = entryClass.getResource(entryClass.getSimpleName()
				+ ".class");
		if (classURL == null) {
			throw new ClassNotFoundException("Cannot find " + classURL);
		}

		if (classURL.toString().startsWith("jar")) {
			return true;
		}
		return false;
	}

	public static File getJarFile(Class clazz) throws Exception {
		if (!isRunInJar(clazz)) {
			throw new IllegalStateException("It seems " + clazz
					+ " is not run in jar mode.");
		}
		String path = clazz.getProtectionDomain().getCodeSource().getLocation()
				.getFile();
		path = URLDecoder.decode(path, "UTF-8");
		File jarFile = new File(path);
		return jarFile;
	}

}
