package edu.qc.seclass;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BuggyClassTestSC2 {
   BuggyClass myBuggyClass;
	
	@Before
	public void setUp() {
	myBuggyClass= new BuggyClass();
	}
	
	@After            
    public void tearDown() {
        myBuggyClass = null;
    }
	
	@Test 
	public void testBuggyClassMethod2a2() {
	assertEquals(5, myBuggyClass.buggyMethod2(2, 6));
	}
}
