package org.jenkinsci.plugins;

import java.io.IOException;
import java.util.List;

import org.jenkinsci.util.CommandRunner;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.CauseAction;
import hudson.model.Environment;
import hudson.model.FileParameterValue;
import hudson.model.FreeStyleProject;
import hudson.model.ParameterValue;
import hudson.model.ParametersAction;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

public class BeforeCmakeBuilder extends Builder {

	// sdk
	// private String SDKDownloadPath;
	private String SDKPath;
	private String SDKUnpackageScript;
	private String SDKUnpacakePath;
	// source code
	// String CodeDownloadPath;
	private String CodePath;
	private String CodeUnpackageScript;
	private String CodeUnpackagePath;
	private String command;

	public String getCommand() {
		return command;
	}

	@DataBoundSetter
	public void setCommand(String command) {
		this.command = command;
	}

	@DataBoundConstructor
	public BeforeCmakeBuilder(String installationName) {

	}

	// public String getSDKDownloadPath() {
	// return SDKDownloadPath;
	// }
	//
	// @DataBoundSetter
	// public void setSDKDownloadPath(String sDKDownloadPath) {
	// SDKDownloadPath = sDKDownloadPath;
	// }

	// public String getSDKPath() {
	// return SDKPath;
	// }
	//
	// @DataBoundSetter
	// public void setSDKPath(String sDKPath) {
	// SDKPath = sDKPath;
	// }

	public String getSDKUnpackageScript() {
		return SDKUnpackageScript;
	}

	@DataBoundSetter
	public void setSDKUnpackageScript(String sDKUnpackageScript) {
		SDKUnpackageScript = sDKUnpackageScript;
	}

	// public String getSDKUnpacakePath() {
	// return SDKUnpacakePath;
	// }
	//
	// @DataBoundSetter
	// public void setSDKUnpacakePath(String sDKUnpacakePath) {
	// SDKUnpacakePath = sDKUnpacakePath;
	// }

	// public String getCodeDownloadPath() {
	// return CodeDownloadPath;
	// }
	//
	// @DataBoundSetter
	// public void setCodeDownloadPath(String codeDownloadPath) {
	// CodeDownloadPath = codeDownloadPath;
	// }

	// public String getCodePath() {
	// return CodePath;
	// }
	//
	// @DataBoundSetter
	// public void setCodePath(String codePath) {
	// CodePath = codePath;
	// }

	// public String getCodeUnpackageScript() {
	// return CodeUnpackageScript;
	// }
	//
	// @DataBoundSetter
	// public void setCodeUnpackageScript(String codeUnpackageScript) {
	// CodeUnpackageScript = codeUnpackageScript;
	// }

	// public String getCodeUnpackagePath() {
	// return CodeUnpackagePath;
	// }
	//
	// @DataBoundSetter
	// public void setCodeUnpackagePath(String codeUnpackagePath) {
	// CodeUnpackagePath = codeUnpackagePath;
	// }

	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
			throws InterruptedException, IOException {
		FilePath WorkSpacePath = build.getWorkspace();
	
		String SDKfilename = "";
		String codefilename = "";
		// System.out.println(WorkSpacePath);
		boolean rs = false;

		List<ParametersAction> actionlist = build.getActions(ParametersAction.class);

		for (ParametersAction action : actionlist) {
			List<ParameterValue> parameterlist = action.getAllParameters();
			for (ParameterValue parameter : parameterlist) {
				// sdk name
				if ("sdk".equalsIgnoreCase(parameter.getDescription())) {
					SDKfilename = parameter.getName();
				}
				// code
				if ("code".equalsIgnoreCase(parameter.getDescription())) {
					codefilename = parameter.getName();
				}
			}
		}
		if ("".equals(SDKfilename)) {
			listener.getLogger().println("SDK is missing.");
			return false;
		}
		if ("".equals(codefilename)) {
			listener.getLogger().println("Code is missing.");
			return false;
		}

		String OS = System.getProperty("os.name").toLowerCase();
		if ("linux".equals(OS)) {
			// check absolute path or relative path;

			if (SDKfilename.startsWith("/")) {
				SDKPath = SDKfilename;
			} else {
				SDKPath = WorkSpacePath + "/" + SDKfilename;
			}
			// sdk
			SDKUnpacakePath = WorkSpacePath + "/" + "UnSDK";
			CommandRunner cr = new CommandRunner();
			if (cr.exits(SDKUnpacakePath)) {
				String delCmd = "rm -rf " + SDKUnpacakePath;
				rs = cr.command(delCmd, listener);
			}
			String mkCmd = "mkdir " + SDKUnpacakePath;
			rs = cr.command(mkCmd, listener);

			if (rs == false) {
				return rs;
			}
			String extCmd = SDKUnpackageScript + " " + SDKPath + " -C " + SDKUnpacakePath;
			rs = cr.command(extCmd, listener);

			if (rs == false) {
				return rs;
			}
			// code
			// check absolute path or relative path;
			if (codefilename.startsWith("/")) {
				CodePath = codefilename;
			} else {
				CodePath = WorkSpacePath + "/" + codefilename;
			}
			CodeUnpackagePath = WorkSpacePath + "/" + "UnCode";
			if (cr.exits(CodeUnpackagePath)) {
				String delCmd = "rm -rf " + CodeUnpackagePath;
				rs = cr.command(delCmd, listener);
			}
			mkCmd = "mkdir " + CodeUnpackagePath;
			rs = cr.command(mkCmd, listener);

			if (rs == false) {
				return rs;
			}
			CodeUnpackageScript = command;
			extCmd = CodeUnpackageScript + " " + CodePath + " -C " + CodeUnpackagePath;
			rs = cr.command(extCmd, listener);

		}
		return rs;
	}

	@Extension
	public static final class DescriptorImpI extends BuildStepDescriptor<Builder> {

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> jobType) {
			// TODO Auto-generated method stub
			if(jobType == FreeStyleProject.class) {
				return true;
			}
			
			
			return false;
		}

		@Override
		public String getDisplayName() {
			return "BeforeCmake";
		}

	}
}
