package Get_devices;

public class Commons {
	public static Process excuteShell(String s) {
		Process proc = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			proc = runtime.exec(s);
		} catch (Exception e) {
			System.out.print("ִ������:" + s + "��������");
			return null;
		}
		return proc;
	}
}
