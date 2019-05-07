package edu.qc.seclass;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BuggyClassTestSC1a {
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
	public void testBuggyClassMethod1a1() {
	assertEquals(3, myBuggyClass.buggyMethod1(2, 2));
	}
	
}
