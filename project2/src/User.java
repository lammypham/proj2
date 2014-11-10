import java.text.SimpleDateFormat;
import java.util.*;

public class User {
	private Integer _id;
	String _city;
	String _state;
	String _country;
	Integer _zipcode;
	String _degree;
	String _major;
	Date _gradDate;
	Integer _workHistCount;
	Integer _totalYrsExp;
	String _currentEmp;
	String _manage;
	Integer _manageCount;
	
	
	List<String> _jobsHistory = new ArrayList<String>();
	
	public User(Integer id)
	{
		_id = id;
	}
	
	public Integer getId(){
		return _id;
	}
	
	public void setCity(String city){
		_city = city;
	}
	
	public String getCity()
	{
		return _city;
	}
	
	public void setState(String state){
		_state = state;
	}
	
	public String getState()
	{
		return _state;
	}
	
	public void setCountry (String country)
	{
		_country = country;
		
	}
	
	public String getCountry ()
	{
		return _country;
	}
	
	public void setZipcode (Integer zipcode)
	{
		_zipcode = zipcode;
	}
	
	public Integer getZipcode ()
	{
		return _zipcode;
	}
	
	public void setDegree (String degree)
	{
		_degree = degree;
	}
	
	public String getDegree ()
	{
		return _degree;
	}
	
	public void setMajor (String major)
	{
		_major = major;
	}
	
	public String getMajor ()
	{
		return _major;
	}
	
	public void setGradDate (Date gradDate)
	{
		_gradDate = gradDate;
	}
	
	public Date getGradDate ()
	{
		return _gradDate;
	}
	
	public void setWorkHistCount (Integer whc)
	{
		_workHistCount = whc;
	}
	
	public Integer getWorkHistCount()
	{
		return _workHistCount;
	}
	
	public void setTotalYrsExp (Integer tye)
	{
		_totalYrsExp = tye;
	}
	
	public Integer getTotalYrsExp()
	{
		return _totalYrsExp;
	}
	
	public void setCurrentEmp (String currentEmp)
	{
		_currentEmp = currentEmp;
	}
	
	public String getCurrentEmp()
	{
		return _currentEmp;
	}
	
	public void setManage (String manage)
	{
		_manage = manage;
	}
	
	public String getManage()
	{
		return _manage;
	}
	
	public void setManageCount(Integer manageCount)
	{
		_manageCount = manageCount;
	}
	
	public Integer getManageCount()
	{
		return _manageCount;
	}
	public void addJobHistory(String job)
	{
		_jobsHistory.add(job);
	}
	
	public List<String> getJobHistory()
	{
		return _jobsHistory;
	}
}
