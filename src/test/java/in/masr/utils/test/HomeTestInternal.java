package in.masr.utils.test;

import static org.junit.Assert.assertEquals;

import in.masr.utils.home.Home;
import in.masr.utils.home.MasrHomeInitException;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import antlr.build.ANTLR;

public class HomeTestInternal {

	private static String BASE = "/home/coolmore/workspace/eclipse/MasrHome";

	@Before
	public void before() {
		FileUtils.deleteQuietly(new File(BASE + "/in.masr.utils.home"));
		FileUtils.deleteQuietly(new File(BASE + "/in.masr.utils.home.another"));
		FileUtils.deleteQuietly(new File(BASE + "/target/in.masr.utils.home"));
		FileUtils.deleteQuietly(new File(BASE + "/test/in.masr.utils.home"));
	}

	@Test
	public void entryClass() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		Home home = new Home(HomeTestInternal.class);
		assertEquals(BASE, home.dir);
	}

	@Test
	public void name() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		Home home = new Home("in.masr.utils.home");
		assertEquals(BASE, home.dir);
	}

	@Test
	public void anotherClass() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		Home home = new Home(Home.class);
		assertEquals(BASE, home.dir);
	}

	@Test
	public void otherName() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home.another"));
		Home home = new Home(HomeTestInternal.class,
				"in.masr.utils.home.another");
		assertEquals(BASE, home.dir);
	}

	@Test
	public void entryClassAndName() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		Home home = new Home(HomeTestInternal.class, "in.masr.utils.home");
		assertEquals(BASE, home.dir);
	}

	@Test(expected = MasrHomeInitException.class)
	public void nameMissing() {
		new Home(HomeTestInternal.class);
	}

	@Test(expected = MasrHomeInitException.class)
	public void nameIsnotMatch() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		new Home(HomeTestInternal.class, "in.masr.utils.home.another");
	}

	@Test(expected = MasrHomeInitException.class)
	public void badClass() throws IOException {
		FileUtils.touch(new File(BASE + "/in.masr.utils.home"));
		new Home(String.class);
	}

	@Test
	public void anotherLocation() throws IOException {
		FileUtils.touch(new File(BASE + "/target/in.masr.utils.home"));
		Home home = new Home(HomeTestInternal.class);
		assertEquals(BASE + "/target", home.dir);
	}

	@Test
	public void outOfJar() throws IOException {
		FileUtils.touch(new File(BASE + "/test/in.masr.utils.home"));
		Home home = new Home(ANTLR.class);
		assertEquals(BASE + "/test", home.dir);
	}

	@After
	public void after() {
		before();
	}

}
