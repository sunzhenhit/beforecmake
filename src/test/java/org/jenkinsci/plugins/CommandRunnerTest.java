package org.jenkinsci.plugins;

import static org.junit.Assert.*;

import java.io.IOException;

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
	public void exitFalse() {
		assertFalse(cr.exits("xxx"));
	}

	@Test
	public void exitTrue() {
		assertTrue(cr.exits(""));
	}
	
	@Test
	public void commandFalse() {
		try {
			assertFalse(cr.command("java",null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void commandTrue() {
		try {
			assertTrue(cr.command("ifconfig",null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
