import java.util.Date;


public class App {
	private Integer _userId;
	private Date _date;
	private Integer _jobId;
	
	public App(Integer userId, Date date, Integer jobId)
	{
		_userId = userId;
		_date = date;
		_jobId = jobId;
	}
	
	public Integer getUserId()
	{
		return _userId;
	}
	public Date getDate()
	{
		return _date;
	}
	public Integer getJobId()
	{
		return _jobId;
	}
}

