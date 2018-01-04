package org.jenkinsci.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import hudson.model.BuildListener;

/**
 * @author Zhen sun responsible for running different command lines
 *
 */
public class CommandRunner {

	/**
	 * Run command lines
	 * 
	 * @param commands
	 * @param listener
	 * @return command execution result
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 */
	public boolean command(String commands, BuildListener listener) throws IOException, InterruptedException {
		String s = null;
		Process p = null;
		boolean rs = true;
		if (listener != null)
			listener.getLogger().println(commands);
		p = Runtime.getRuntime().exec(commands);
		BufferedReader brError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String errline = null;
		while ((errline = brError.readLine()) != null) {
			if (listener != null)
				listener.getLogger().println(errline);
			System.out.println("Error: "+ errline);
			rs = false;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((s = br.readLine()) != null) {
			if (listener != null)
				listener.getLogger().println(s);
			System.out.println("INFO: " + s);
			rs = true;
		}
		p.waitFor();
		if (listener != null)
			listener.getLogger().println("exit: " + p.exitValue());
		p.destroy();
		System.out.println(rs);
		return rs;

	}

	/**
	 * Check given file exits
	 * 
	 * @param dir
	 * @return
	 */
	public boolean exits(String dir) {
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);

		if (!dirFile.exists() || !dirFile.isDirectory()) {

			return false;
		} else {
			return true;
		}

	}

}
