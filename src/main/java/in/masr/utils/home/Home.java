package in.masr.utils.home;

import java.io.File;
import java.net.URL;

public class Home {

	private static final String defaultFlag = "in.masr.utils.home";
	public String dir;

	public Home(Class entryClass) throws MasrHomeInitException {
		this(entryClass, defaultFlag);
	}

	public Home(String flagName) throws MasrHomeInitException {
		this(Home.class, flagName);
	}

	public Home(Class entryClazz, String flagFileName)
			throws MasrHomeInitException {
		dir = null;
		boolean isRunInJar = false;
		try {
			isRunInJar = JarU.isRunInJar(entryClazz);
		} catch (ClassNotFoundException e) {
			throw new MasrHomeInitException(e.getMessage(), e);
		}
		boolean ok = false;
		// flag file is out of classpath
		if (isRunInJar) {
			// find from jar file and look up
			ok = tryToFindOutofJar(entryClazz, flagFileName);
		} else {
			// find from class file and look up
			ok = tryToFindInFileystemFromCodeSource(entryClazz, flagFileName);
		}

		if (ok) {
			return;
		}

		throw new MasrHomeInitException("[" + entryClazz.getName()
				+ "] Cannot find " + flagFileName
				+ " in both folders and jar resources! ");
	}

	private boolean tryToFindInRootClassPath(Class entryClass,
			String flagFileName) {
		URL classURL = entryClass.getResource("/" + flagFileName);
		if (classURL == null) {
			return false;
		}
		dir = "";
		return true;
	}

	private boolean tryToFindInFileystemFromCodeSource(Class entryClass,
			String flagFileName) throws MasrHomeInitException {
		// probably run in eclipse or ide
		String entryFilePath = getClassFilePath(entryClass);
		String location = findHomePath(entryFilePath, flagFileName);
		if (location == null)
			return false;
		dir = location;
		return true;
	}

	private boolean tryToFindOutofJar(Class entryClass, String flagFileName)
			throws MasrHomeInitException {
		// run in jar mode
		String entryFilePath = null;
		try {
			entryFilePath = JarU.getJarFile(entryClass).getAbsolutePath();
		} catch (Exception ex) {
			throw new MasrHomeInitException(ex.getMessage(), ex);
		}
		String location = findHomePath(entryFilePath, flagFileName);
		if (location == null)
			return false;
		dir = location;
		return true;
	}

	private String getClassFilePath(Class entryClass)
			throws MasrHomeInitException {
		try {
			URL url = entryClass.getProtectionDomain().getCodeSource()
					.getLocation();
			String filePath = url.getPath();
			return filePath;
		} catch (Exception ex) {
			throw new MasrHomeInitException(ex.getMessage(), ex);
		}
	}

	private String findHomePath(String path, String flagFileName) {
		File file = new File(path);
		if (file.isFile()) {
			file = file.getParentFile();
		}
		while (file != null) {
			String flagFilePath = file.getAbsolutePath() + File.separator
					+ flagFileName;
			File flagFile = new File(flagFilePath);
			if (flagFile.exists()) {
				return file.getAbsolutePath();
			} else {
				file = file.getParentFile();
			}
		}
		return null;
	}

}