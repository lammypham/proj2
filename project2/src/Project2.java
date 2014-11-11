import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Project2 {
	SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	SimpleDateFormat _sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Map<Integer, List<App>> _appsMap = new HashMap<Integer, List<App>>();
	private Map<Integer, User> _usersMap = new HashMap<Integer, User>();
	private Map<Integer, Jobs> _jobsMap = new HashMap<Integer, Jobs>();
	private Map<Integer, List<App>> _apps2Map = new HashMap<Integer, List<App>>();
	private Map<Integer, User> _users2Map = new HashMap<Integer,User>();
	
	public void execute(String inputDir, String outputFile) throws Exception
	{
		File inDir = new File(inputDir);
		System.out.println("Processing User");
		processUser(inDir);
		System.out.println("Processing User2");
		processUser2(inDir);
		System.out.println("Processing Jobs");
		processJobs(inDir);
		System.out.println("Processing Apps");
		processApps(inDir);
		System.out.println("Processing User History");
		processUserHistory(inDir);
		System.out.println("Finish");
		
		//currentEmp: yes manage:yes
		Map<Integer,List<User>> group1 = new HashMap<Integer,List<User>>();
		//currentEmp: yes manage:no
		Map<Integer,User> group2 = new HashMap<Integer,User>();
		//currentEmp: no manage:yes
		Map<Integer,User> group3 = new HashMap<Integer,User>();
		//currentEmp: no manage:no
		Map<Integer,User> group4 = new HashMap<Integer,User>();
		
		
		// using k means to find the distance of 3 nominal attributes

		// find the distance between the nominal attributes for u1
		// use the weighted distance to find the record of u2 and find smallest value
		for(Integer uid : _usersMap.keySet())
		{
			User u = _usersMap.get(uid);
			List<User> list1 = new ArrayList();
			
			if (u.getCurrentEmp().equalsIgnoreCase("yes"))
			{
				if (u.getManage().equalsIgnoreCase("yes"))
				{
					//if match add to list, add record
					list1.add(u);
					group1.put(uid, list1);
				}
				else
				{
					group2.put(uid,u);
				}
			}
			else{
				if (u.getManage().equalsIgnoreCase("yes"))
				{
					group3.put(uid,u);
				}
				else
				{
					group4.put(uid, u);
				}
			}
		}
		
		for (Integer uid : group1.keySet())
		{
			System.out.println(uid + ": " + group1.get(uid).size());
		}

		
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
			user.setZipcode(ar[4]);
			user.setDegree(ar[5]);
			user.setMajor(ar[6]);
			user.setGradDate(ar[7].isEmpty() ? null : _sdf2.parse(ar[7]));
			user.setWorkHistCount(Integer.valueOf(ar[8])); 
			user.setTotalYrsExp(ar[9].isEmpty() ? 0 :Integer.valueOf(ar[9]));
			user.setCurrentEmp(ar[10]);
			user.setManage(ar[11]);
			user.setManageCount(Integer.valueOf(ar[12]));
			
			
			_usersMap.put(user.getId(), user);
		}
		
		br.close();
	}
	
	private void processUser2(File inputDir) throws Exception
	{
		File users2File = new File(inputDir, "user2.tsv");
		BufferedReader br = new BufferedReader(new FileReader(users2File));
		String line;
		while((line = br.readLine()) != null)
		{
			User user = _usersMap.get(Integer.valueOf(line));
			if (user != null)
			{
				_users2Map.put(user.getId(), user);
			}
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
			jobs.setZipcode(ar[7]);
			jobs.setStart(_sdf2.parse(ar[8]));
			jobs.setEnd(_sdf2.parse(ar[9]));
			
			_jobsMap.put(jobs.getId(), jobs);
		}
	}
	
	private void processUserHistory(File inputDir) throws Exception
	{
		File uhFile = new File(inputDir, "user_history.tsv");
		BufferedReader br = new BufferedReader(new FileReader(uhFile));
		String line = br.readLine();
		while((line = br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			User u = _usersMap.get(Integer.valueOf(ar[0]));
			
			if (u != null && ar.length > 2 && !ar[2].isEmpty())
			{
				u.addJobHistory(ar[2]);
			}
		}
		br.close();
	}
	

	
	private void processApps(File inputDir) throws Exception
	{
		Date t2 = _sdf2.parse("2014-04-09 00:00:00");
		//System.out.println(t2.toString());
		File appsFile = new File(inputDir, "apps.tsv");
		BufferedReader br = new BufferedReader(new FileReader(appsFile));
		String line = br.readLine();
		while((line = br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			App app = new App(Integer.valueOf(ar[0]), _sdf2.parse(ar[1]), Integer.valueOf(ar[2]));
			if(app.getDate().after(t2))
			{
				List<App> list = _apps2Map.get(app.getUserId());
				if(list == null)
				{
					list = new ArrayList<App>();
					_apps2Map.put(app.getUserId(), list);
				}
				list.add(app);
			}
				
			else{

				List<App> list = _appsMap.get(app.getUserId());
				if(list == null)
				{
					list = new ArrayList<App>();
					_appsMap.put(app.getUserId(), list);
				}
				list.add(app);
					
			}

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
