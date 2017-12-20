package org.jenkinsci.plugins;

import static org.junit.Assert.*;

import org.jenkinsci.util.CommandRunner;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class CommandRunnerTest {
	
	private CommandRunner cr;

	@Before
	public void setUp() throws Exception {
		CommandRunner cr = new CommandRunner();
		this.cr=cr;

	}

	@Test
	public void test() {
		//Assert.assertTrue(condition);
		//assertTrue(cr.deleteDirectory("C:\\dev\\testdelete"));
		assertTrue(true);

	}

}
