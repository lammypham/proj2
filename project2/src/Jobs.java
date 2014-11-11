import java.util.*;

public class Jobs {

	private Integer _id;
	String _title;
	String _desc;
	String _reqs;
	String _city;
	String _state;
	String _country;
	String _zip;
	Date _start;
	Date _end;
	
	public Jobs (Integer id)
	{
		_id = id;
	}
	
	public Integer getId()
	{
		return _id;
	}
	
	public void setTitle(String title)
	{
		_title = title;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	public void setDesc(String desc)
	{
		_desc = desc;
	}
	
	public String getDesc()
	{
		return _desc;
	}
	
	public void setReqs (String reqs)
	{
		_reqs = reqs;
	}
	
	public String getReqs ()
	{
		return _reqs;
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
	
	public void setZipcode (String zipcode)
	{
		_zip = zipcode;
	}
	
	public String getZipcode ()
	{
		return _zip;
	}
	
	public void setStart(Date start)
	{
		_start = start;
	}
	
	public Date getStart()
	{
		return _start;
	}
	
	public void setEnd(Date end)
	{
		_end = end;
	}
	
	public Date getEnd()
	{
		return _end;
	}
}
