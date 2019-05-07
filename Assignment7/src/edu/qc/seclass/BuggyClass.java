package edu.qc.seclass;

public class BuggyClass {

	
	public int buggyMethod1(int x, int y)
	{
     int result=1;
		
		if(x<4) {
			x=x-3;
		}else {
			x=x-1;
		}
		
		result=x/y;
		
		if(x>=1) {
			result=+4;
		}else {
			result=+3;
		}
		
		if(y<3) {
			y=y+1;
		}
		
		return result;
       }
	

	public int buggyMethod2(int p, int q)
	{
		int result = 2;
		if(p<4) 
		{
			p=+3;
		}else {
			p=-3;
		}
		
		if(q<4) 
		{
			q=+3;
		}else {
			
			q=-3;
		}
		
		if(p<=4) 
		{
			result=result+p+q+3;
			
		}else {
			p=-7;
			result=(q/p);  // 
		}
		
		return result;
	}
	
	public int buggyMethod3(int x, int y)
	{
		  
		/*We observe that if we look at each and every possible branch, 
		 * it is possible to create a test suite that achieves 100% branch coverage. 
		It is also possible to obtain a test suite that achieves 100% statement coverage, 
		however if it reveals a fault then it CANNOT achieve a 100% statement coverage.
		Therefore, The one who reveals the fault  cannot produce 100% coverage    */
		int result=0;
		
		return result;
	}
	public int buggyMethod4(int x, int y)
	{
		/* as seen in above method 100% coverage doesn't provide a solution to problem. 
		 * if we will create a possible 100% statement, 
		 * it will reveal fault which means its not 100% coverage.
		 we can run the statement but it will reveal faults.
		 therefore not possible.
		*/
		int ans=0;
		return ans;
	}
	
	public int buggyMethod5(int x)
	{
		/* As discuss in method3 100% coverage will not provides solution to our problem.
		  it is possible to create it but it will reveals the fault.
		  As we seen in the psedocode, we observe that line 4 will reveal a fault which implies that the test suite,
		   will not achieve 100% statement coverage.
	 
	 Thus it is not possible to create such a method which achieve 100% statement coverage and at the same time expose the fault.
		*/
		int ans=0;
		
		return ans;
	}
}
