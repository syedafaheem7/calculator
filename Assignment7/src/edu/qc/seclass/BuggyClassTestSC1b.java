package edu.qc.seclass;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BuggyClassTestSC1b 
{
    BuggyClass myBuggyClass;
	
	@Before
	public void setUp() {
	myBuggyClass= new BuggyClass();
	}
	
	@After            //after each test
    public void tearDown() {
        myBuggyClass = null;
    }
	
	
	@Test 
	public void testBuggyClassMethod1b1() {
	assertEquals(0, myBuggyClass.buggyMethod1(5, 0));
	}



}
