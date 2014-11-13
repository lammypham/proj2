
public class PredictionResult {
	Integer _u2, _u1;
	float _minValue;
	int _rank;
	
	public PredictionResult(Integer u2, Integer u1, float value)
	{
		_u2 = u2;
		_u1 = u1;
		_minValue = value;
	}
	
	public Integer getU2()
	{
		return _u2;
	}
	
	public Integer getU1()
	{
		return _u1;
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
}
