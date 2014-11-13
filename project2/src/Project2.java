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
	private Map<Integer, User> _users1Map = new HashMap<Integer, User>();
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
		System.out.println("Processing User1");
		processUser1();
		System.out.println("Processing Jobs");
		processJobs(inDir);
		System.out.println("Processing Apps");
		processApps(inDir);
		System.out.println("Processing User History");
		processUserHistory(inDir);
		System.out.println("Finish");
		
		//currentEmp: yes manage:yes
		Map<Integer,User> group1 = new HashMap<Integer,User>();
		//currentEmp: yes manage:no
		Map<Integer,User> group2 = new HashMap<Integer,User>();
		//currentEmp: no manage:yes
		Map<Integer,User> group3 = new HashMap<Integer,User>();
		//currentEmp: no manage:no
		Map<Integer,User> group4 = new HashMap<Integer,User>();
		
		//currentEmp: yes workhistcount: <5
		Map<Integer,User> group5 = new HashMap<Integer,User>();
		//currentEmp: yes workhistcount:>5
		Map<Integer,User> group6 = new HashMap<Integer,User>();
		//currentEmp: no workhistcount:<5
		Map<Integer,User> group7 = new HashMap<Integer,User>();
		//currentEmp: no workhistcount:>5
		Map<Integer,User> group8 = new HashMap<Integer,User>();
		
		//currentEmp: yes totalyrsexp: <10
		Map<Integer,User> group9 = new HashMap<Integer,User>();
		//currentEmp: yes totalyrsexp:>10
		Map<Integer,User> group10 = new HashMap<Integer,User>();
		//currentEmp: no totalyrsexp:<10
		Map<Integer,User> group11 = new HashMap<Integer,User>();
		//currentEmp: no totalyrsexp:>10
		Map<Integer,User> group12 = new HashMap<Integer,User>();
		
		
		// using k means to find the distance of 3 nominal attributes

		// find the distance between the nominal attributes for u1
		// use the weighted distance to find the record of u2 and find smallest value
		for(Integer uid : _users1Map.keySet())
		{
			User u = _users1Map.get(uid);

			if (u.getCurrentEmp().equalsIgnoreCase("yes"))
			{
				if (u.getManage().equalsIgnoreCase("yes"))
				{
					//if match add to list, add record
					
					group1.put(uid, u);
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
		
		for (Integer uid: _users1Map.keySet())
		{
			User u = _users1Map.get(uid);
			if (u.getCurrentEmp().equalsIgnoreCase("yes"))
			{
				if(u.getWorkHistCount() < 5 )
				{
					group5.put(uid, u);
				}
				else
				{
					group6.put(uid,u);
				}
			}
			else
			{
				if(u.getWorkHistCount() < 5)
				{
					group7.put(uid,u);
				}
				else
				{
					group8.put(uid,u);
				}
			}
		}
		for (Integer uid : _users1Map.keySet())
		{
			User u = _users1Map.get(uid);
			if (u.getCurrentEmp().equalsIgnoreCase("yes"))
			{
				if(u.getTotalYrsExp() < 10)
				{
					group9.put(uid,u);
				}
				else
				{
					group10.put(uid,u);
				}
			}
			else
			{
				if(u.getTotalYrsExp() < 10)
				{
					group11.put(uid,u);
				}
				else
				{
					group12.put(uid,u);
				}
			}
		}
		
		
		
		//System.out.println("Group9: " +group9.size());
		//System.out.println("Group10: " +group10.size());
		//System.out.println("Group11: " +group11.size());
		//System.out.println("Group12: " +group12.size());
		Integer total = group9.size() + group10.size() + group11.size()+group12.size();
		
		//System.out.println("total: "+ total);
		//System.out.println("users: " + _usersMap.size());
		
		
		
		Float d1 = processKDistance(group1.size(), group2.size(), group3.size(), group4.size());
		Float d2 = processKDistance(group5.size(), group6.size(), group7.size(), group8.size());
		Float d3 = processKDistance(group9.size(), group10.size(), group11.size(), group12.size());
		//System.out.println("d1: " + d1);
		//System.out.println("d2: " + d2);
		//System.out.println("d3: " + d3);
		
		//compare u2 records to u1 records and calculate weighted distance
		float temp1 = 0;
		float temp2 = 0;
		float temp3 = 0;
		float tempTotal  = 0;
		Map<Integer,Float> userKNN = new HashMap<Integer, Float>();
		List<PredictionResult> selectedUsers = new ArrayList<PredictionResult>();
		
		for (Integer uid2 : _users2Map.keySet())
		{
			userKNN.clear();
			for (Integer uid1: _users1Map.keySet())
			{
				User u2 = _users2Map.get(uid2);
				User u1 = _users1Map.get(uid1);

				if(u1.getManage() != null && u2.getManage() != null)
				{
					if(u1.getManage().equalsIgnoreCase(u2.getManage()))
					{
						temp1 = 0;
					}
					else
					{
						temp1 = d1*d1;
					}

				}
				else
				{
					temp1 = d1*d1;
				}
							
				if ((u1.getWorkHistCount() < 5 && u2.getWorkHistCount() < 5) || (u1.getWorkHistCount() > 5 && u2.getWorkHistCount() > 5))
				{
					temp2 = 0;
				}
				else
				{
					temp2 = d2*d2;
				}
				if ((u1.getTotalYrsExp() < 10 && u2.getTotalYrsExp() <10) || (u2.getTotalYrsExp() > 10 && u2.getTotalYrsExp() > 10))
				{
					temp3 = 0;
				}
				else
				{
					temp3 = d3*d3;
				}
				tempTotal = temp1 + temp2 + temp3;				
				if(!(u1.getMajor().equalsIgnoreCase(u2.getMajor())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added major - " + tempTotal);

				}
				
				if(!(u1.getDegree().equalsIgnoreCase(u2.getDegree())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added degree - " + tempTotal);

				}
				if(!(u1.getCity().equals(u2.getCity())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added city - " + tempTotal);

				}
				if(!(u1.getZipcode().equalsIgnoreCase(u2.getZipcode())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added zipcode - " + tempTotal);
				}
				if(!(u1.getState().equalsIgnoreCase(u2.getState())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added state - " + tempTotal);

				}
				if(!(u1.getCountry().equalsIgnoreCase(u2.getCountry())))
				{
					tempTotal = (float) (tempTotal +.125);
					//System.out.println("added country - " + tempTotal);

				}

				//System.out.println("temp1: " + temp1 + " temp2: " + temp2 + " temp3: " + temp3);
				//System.out.println("total: " + tempTotal);
				userKNN.put(uid1, tempTotal);
				
				//rest values
				tempTotal = 0;
				temp1 = 0; temp2 = 0; temp3 = 0;
			}
			//search for closest u1 value that has a t2 application
			Integer minUid = null;
			float minTotal = (float)999999;
			for(Integer i : userKNN.keySet())
			{
				float f = userKNN.get(i);
				if(f < minTotal)
				{
					User u = _users1Map.get(i);
					List<App> t2 = _apps2Map.get(i);
					if(t2 != null)
					{
						minUid = i;
						minTotal = f;
					}
				}
			}
			if(minUid != null)
			{
				PredictionResult p = new PredictionResult(uid2, minUid, minTotal);
				selectedUsers.add(p);
			}
		}
		Map<Integer, List<PredictionResult>> predictionMap = new HashMap<Integer, List<PredictionResult>>();
		for(PredictionResult p : selectedUsers)
		{
			List<PredictionResult> lst = predictionMap.get(p.getU2());
			if(lst == null)
			{
				lst = new ArrayList<PredictionResult>();
				predictionMap.put(p.getU2(), lst);
			}
			lst.add(p);
		}
		/*
		
		String s = "Major in Accounting with minor in Engineering";
		String[] ar = s.split(" ");

		*/
		String[] exclude = {"the", "to", "and", "in", "a", "or", "with","at","of","not","&", "","an", "/"};
		int newCount = 0;
		for(Integer u2id : predictionMap.keySet())
		{
			
			List<PredictionResult> lst = predictionMap.get(u2id);
			for(PredictionResult pr :lst)
			{
				List<App> apps = _apps2Map.get(pr.getU1());
				//List<App> apps = _appsMap.get(u2id);
				for(App a : apps)
				{
					Jobs job = _jobsMap.get(a.getJobId());
					
					User user = _users2Map.get(u2id);

					//System.out.println("u2id: " + u2id + " - " + a.getJobId() + " score: " + pr.getValue());
					
					String major = user.getMajor();
					String reqs = job.getDesc().toLowerCase();
					String title1 = job.getTitle();
					String[] ar = major.split(" ");

					for(int i=0; i < ar.length; i++)
					{
						String w = ar[i];
						boolean cont = true;
						for(int j=0; j < exclude.length; j++)
						{
							if(w.equalsIgnoreCase(exclude[j]))
							{
								cont = false;
								break;
							}
						}
						if(cont)
						{
							String b = base(w).toLowerCase();
							//System.out.println(String.format("%s - %s", w,  base(w)));
							int found = reqs.indexOf(b);
							if(found != -1)
							{
								System.out.println(job.getTitle());
								System.out.println(String.format("uid: %d jobid: %d Major: %s baseword: %s - %d ",u2id, a.getJobId(),major,b,found));
								newCount = newCount + 1;
								//System.out.println(reqs);
								//System.out.println(_jobsMap.size());
							}
							//System.out.println(reqs);
							//System.out.println(b);
						}
					}

				}
			}
			
		}
		System.out.println(newCount);
		System.out.println(selectedUsers.size());
		
		
		
	}
	
	private Float processKDistance(int a, int b, int c, int d)
	{
		int t1 = a + c;
		int t2 = b + d;
		
		Float x =  (float) ((a/(float)t1) - (b/(float)t2));
		Float y = (float) ((c/(float)t1) - (d/(float)t2));
		
		return (float) (Math.abs(x) + Math.abs(y));
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
	private void processUser1() throws Exception
	{
		for (Integer uid : _usersMap.keySet())
		{
			User u = _users2Map.get(uid);
			if (u == null)
			{
				_users1Map.put(uid, _usersMap.get(uid));
			}
		}
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
		br.close();
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
		Date t2 = _sdf2.parse("2012-04-09 00:00:00");
		//System.out.println(t2.toString());
		File appsFile = new File(inputDir, "apps.tsv");
		BufferedReader br = new BufferedReader(new FileReader(appsFile));
		String line = br.readLine();
		while((line = br.readLine()) != null)
		{
			String[] ar = line.split("\t");
			Integer jobId = Integer.valueOf(ar[2]);
			Jobs job = _jobsMap.get(jobId);
			if(job != null)
			{
				if(job.getEnd() == null || job.getEnd().after(t2))
				{
					App app = new App(Integer.valueOf(ar[0]), _sdf2.parse(ar[1]), jobId);
			
					if((app.getDate().after(t2)))
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
			}
		}
		br.close();
	}

	private String base(String str)
	{
		String[] suffix = {"ing","er","able","ational","tional","ate","ive","ful","ation","ator","ment","tions","ess" };
		String s = str;
		for(int i=0; i < suffix.length; i++)
		{
			boolean b = str.toLowerCase().endsWith(suffix[i]);
			if(b)
			{
				s = str.substring(0, str.length() - suffix[i].length());
			}
		}
		return s;
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
