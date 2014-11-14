
public class PredictionResult implements Comparable<PredictionResult> {
	private Integer _u2, _u1;
	private float _minValue;
	private int _rank;
	private int _count;
	private int _jobid;
	
	public PredictionResult(Integer u2, Integer u1) 
	{
		_u2 = u2;
		_u1 = u1;

	}
	
	public Integer getU2()
	{
		return _u2;
	}
	
	public Integer getU1()
	{
		return _u1;
	}
	
	public void setValue(float value)
	{
		_minValue = value;
	}
	
	public float getValue()
	{
		return _minValue;
	}
	
	public void setRank(int r)
	{
		_rank = r;
	}
	
	public int getRank()
	{
		return _rank;
	}
	public void setCount (int c)
	{
		_count = c;
	}
	
	public int getCount()
	{
		return _count;
	}
	
	public void setJobID(int jobid)
	{
		_jobid = jobid;
	}
	
	public int getJobID()
	{
		return _jobid;
	}
	
	public void printResult()
	{
		System.out.println(String.format("U2ID: %d\tJOBID: %d\t Count: %d",_u2,_jobid,_count));
	}

	@Override
	public int compareTo(PredictionResult pr) {
		// TODO Auto-generated method stub
		int compareCount = ((PredictionResult)pr).getCount();
		return compareCount-this._count;
	}


}
