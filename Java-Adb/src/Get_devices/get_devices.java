package Get_devices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class get_devices {
	
	public static void main(String[] args) throws Exception {
		get_devices test1 = new get_devices();
		test1.get_all_devices();	
	}
	
	
	//region ��ʼ��
		/**��ȡ�豸��Ϣ(����״̬�����кš���Ʒ�����ͺš����ơ�ϵͳ�汾��)
		 * @return 
		 * @throws Exception
		 */
		public Map<String, String> get_all_devices() throws Exception {
			Map<String, String> map = null;
			ArrayList<String> list = null;
			Process process;
			BufferedReader reader;
			String line = null;
			String device_tpye;
			try {
				list = new ArrayList<String>();
				map = new HashMap<String, String>();
	            //����adb.exe���·������������˻���������ֱ������adb����
				String adb_path = "adb";
				//ִ��adb device�������鿴pc��ǰ�����ֻ���ģ�����豸�б�
				//ע�⣺һ��Ҫ�����ú�sdk���������������޷�ֱ��ִ��adb����
				process = Commons.excuteShell(adb_path + " devices -l");
				if(process != null){
					reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					while ((line = reader.readLine()) != null) {
						if (line.length() > 1) {
							list.add(line);
						}
					}
					if(!list.contains("* daemon started successfully *")){	
							if (list != null && list.size() > 1) {
								if(!list.contains("device")){
									for (int i = 1; i < list.size(); i++) {	
										for (int j = 0; j < list.get(i).split(" ").length; j++) {
											//��ȡ�ֻ��豸����״̬��Ŀǰ״̬�У�device(����)��offline��unauthorized
											device_tpye = list.get(i).split(" ")[j];
											//�жϵ�ǰ�豸״̬�Ƿ�����
											if(device_tpye.equals("device")){
												//��ȡ�豸���к�
												String device_sn = list.get(i).split(" ")[0];
					/*							//��ȡ�豸��Ʒ��
												String device_product = list.get(i).split(" ")[8];
												//��ȡ�豸�ͺ�
												String device_model = list.get(i).split(" ")[9];
												//��ȡ�豸����
												String device_name = list.get(i).split(" ")[10];*/
												System.out.println("��ǰ�豸����Ϊ:"+ device_sn);
												String shell = adb_path + " -s " + device_sn + " shell cat /system/build.prop /| grep 'ro.build.version.release'";
												process = Commons.excuteShell(shell);
												if(process != null){
													reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
													line = null;
													while ((line = reader.readLine()) != null) {
														if (line.indexOf("ro.build.version.release") != -1) {
															//��ȡ�豸�汾��
															String device_version = line.split("=")[1];
															System.out.println("��ǰϵͳ�汾��Ϊ:" + device_version);
															map.put(device_sn, device_version);
														}
													}
												}
											}
										}
									}
								}else{
									System.out.println("��ǰ�豸�б��У�û��device���������豸�����飡");
								}
							}else{
								System.out.println("��ǰ�豸�б�û�����ӵ��豸�����飡");
							}
					}else{
						get_all_devices();
					}
				}else{
					System.out.println("��ǰִ��adb�����쳣������adb������");
				}
			} catch (IOException e) {
				System.err.println("IOException" + e.getMessage());
				return null;
			}
			return map;
		}
		//endregion

		
		
		
}
