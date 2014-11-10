import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Project2 {
	SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private Map<Integer, List<App>> _appsMap = new HashMap<Integer, List<App>>();
	private Map<Integer, User> _usersMap = new HashMap<Integer, User>();
	private Map<Integer, Jobs> _jobsMap = new HashMap<Integer, Jobs>();
	
	public void execute(String inputDir, String outputFile) throws Exception
	{
		File inDir = new File(inputDir);
		
	}
	
	private void processUser(File inputDir) throws Exception
	{
		File usersFile = new File(inputDir, "users.tsv");
		BufferedReader br = new BufferedReader(new FileReader(usersFile));
		String line = br.readLine();
		while((line = br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			User user = new User(Integer.valueOf(ar[0]));
			user.setCity(ar[1]);
			user.setState (ar[2]);
			user.setCountry(ar[3]);
			user.setZipcode(Integer.valueOf(ar[4]));
			user.setDegree(ar[5]);
			user.setMajor(ar[6]);
			user.setGradDate(_sdf.parse(ar[7]));
			user.setWorkHistCount(Integer.valueOf(ar[8])); 
			user.setTotalYrsExp(Integer.valueOf(ar[9]));
			user.setCurrentEmp(ar[10]);
			user.setManage(ar[11]);
			user.setManageCount(Integer.valueOf(ar[12]));
			
			_usersMap.put(user.getId(), user);
		}
		
		br.close();
	}
	
	private void processJobs(File inputDir) throws Exception
	{
		File jobsFile = new File(inputDir, "jobs.tsv");
		BufferedReader br = new BufferedReader(new FileReader(jobsFile));
		String line = br.readLine();
		while((line=br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			Jobs jobs = new Jobs(Integer.valueOf(ar[0]));
			jobs.setTitle(ar[1]);
			jobs.setDesc(ar[2]);
			jobs.setReqs(ar[3]);
			jobs.setCity(ar[4]);
			jobs.setState(ar[5]);
			jobs.setCountry(ar[6]);
			jobs.setZipcode(Integer.valueOf(ar[7]));
			jobs.setStart(_sdf.parse(ar[8]));
			jobs.setEnd(_sdf.parse(ar[9]));
			
			_jobsMap.put(jobs.getId(), jobs);
		}
	}
	
	private void processApps(File inputDir) throws Exception
	{
		File appsFile = new File(inputDir, "apps.tsv");
		BufferedReader br = new BufferedReader(new FileReader(appsFile));
		String line = br.readLine();
		while((line = br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			App app = new App(Integer.valueOf(ar[0]), _sdf.parse(ar[1]), Integer.valueOf(ar[2]));
			List<App> list = _appsMap.get(app.getUserId());
			if(list == null)
			{
				list = new ArrayList<App>();
				_appsMap.put(app.getUserId(), list);
			}
			list.add(app);
		}
		br.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		if(args.length < 2)
		{
			System.out.println("usage: Project2 input-dir output-file");
			System.exit(1);
		}
		
		new Project2().execute(args[0], args[1]);
	}

}
