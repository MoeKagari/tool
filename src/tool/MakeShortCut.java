package tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/** 创建快捷方式 */
public class MakeShortCut {
	/**
	 * 创建快捷方式
	 * @param temp js文件的暂存位置
	 * @param source 源文件路径
	 * @param folder 快捷方式的目标文件夹
	 * @param name 快捷方式的文件名
	 * @return 是否创建成功
	 * @throws IOException js文件创建失败
	 * @throws InterruptedException js文件执行失败
	 */
	public static boolean creatShotcut(String temp, File source, String folder, String name) throws IOException, InterruptedException {
		try (PrintStream out = new PrintStream(new FileOutputStream(new File(temp)))) {
			out.println("WScript.Echo(\"Creating   shortcuts...\");");
			out.println("Shell   =   new   ActiveXObject(\"WScript.Shell\");");
			out.println("ProgramsPath   =   Shell.SpecialFolders(\"Programs\");");

			/** 创建菜单快捷方式 */
			out.println("fso   =   new   ActiveXObject(\"Scripting.FileSystemObject\");");
			out.println("if   (!fso.folderExists(ProgramsPath   +   \"\\\\" + folder + "\"))");
			out.println("fso.CreateFolder(ProgramsPath   +   \"\\\\" + folder + "\");");
			out.println("link   =   Shell.CreateShortcut(ProgramsPath   +   \"\\\\" + folder + "\\\\" + name + ".lnk\");");
			out.println("link.Arguments   =   \"\";");
			out.println("link.Description   =   \"" + name + "\";");
			out.println("link.HotKey   =   \"\";");
			out.println("link.IconLocation   =   \"" + source.getAbsolutePath().replace("\\", "\\\\") + ",0\";");
			out.println("link.TargetPath   =   \"" + source.getAbsolutePath().replace("\\", "\\\\") + "\";");
			out.println("link.WindowStyle   =   1;");
			out.println("link.WorkingDirectory   =   \"" + source.getAbsolutePath().replace("\\", "\\\\") + "\";");
			out.println("link.Save();");

			/** 创建桌面快捷方式 */
			out.println("DesktopPath   =   Shell.SpecialFolders(\"Desktop\");");
			out.println("link   =   Shell.CreateShortcut(DesktopPath   +   \"\\\\" + name + ".lnk\");");
			out.println("link.Arguments   =   \"\";");
			out.println("link.Description   =   \"" + name + "\";");
			out.println("link.HotKey   =   \"\";");
			out.println("link.IconLocation   =   \"" + source.getAbsolutePath().replace("\\", "\\\\") + ",0\";");
			out.println("link.TargetPath   =   \"" + source.getAbsolutePath().replace("\\", "\\\\") + "\";");
			out.println("link.WindowStyle   =   1;");
			out.println("link.WorkingDirectory   =   \"" + source.getAbsoluteFile().getParentFile().getAbsolutePath().replace("\\", "\\\\") + "\";");
			out.println("link.Save();");
			out.println("WScript.Echo(\"Shortcuts   created.\");");
		}

		Process process = Runtime.getRuntime().exec("cmd.exe   /c   cscript.exe   /nologo    " + temp);
		process.waitFor();
		new File(temp).delete();

		return process.exitValue() == 0;
	}
}
