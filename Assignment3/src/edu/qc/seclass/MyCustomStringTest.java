package edu.qc.seclass;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;

import edu.qc.seclass.MyCustomStringInterface;
import edu.qc.seclass.MyIndexOutOfBoundsException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(9, mycustomstring.countNumbers());
    }

    @Test     //count digits in beginning
    public void testCountNumbers2() {
    	mycustomstring.setString("18 february is president day");
        assertEquals(2, mycustomstring.countNumbers());
        
    }

    @Test
    public void testCountNumbers3() {
    	mycustomstring.setString("14 and 7 februray is valentine and rose day");
        assertEquals(3, mycustomstring.countNumbers());
        
   
    }
    @Test // count digits at last 
    public void testCountNumbers4() {
    	mycustomstring.setString("our spring break is from 18 ");
        assertEquals(2, mycustomstring.countNumbers());
    
       
    }

    @Test     //test whether there is any digit or not
    public void testCountNumbers5() {
    	mycustomstring.setString("i have zero dollar");
        assertEquals(0, mycustomstring.countNumbers());
        
    }

    @Test      
    public void testCountNumbers6() {
    	mycustomstring.setString("i have 210 credits");
    	assertEquals(3, mycustomstring.countNumbers());
        
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(" d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
    	 mycustomstring.setString("Hello guys"); //n=4
         assertEquals("lg ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {
    	 mycustomstring.setString("this test is very hard");    //n=3 
         assertEquals(" this test is very hard", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, false));
    }

    @Test   (expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
    	 mycustomstring.setString("hi");    //n =2
         mycustomstring.getEveryNthCharacterFromBeginningOrEnd(0, false);
    }

    @Test     
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
    	 mycustomstring.setString("didnt get this part");    //n=3
         assertEquals("nehp ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, true));
    }

    @Test   (expected=IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
    	 mycustomstring.setString("we will code 15 assign");
          mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-2, false);
    }

    @Test     
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
    	 mycustomstring.setString("I am taking four classes");
         assertEquals( " tnu ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {
    	 mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
         assertEquals("'bt t0 6snh r6rh ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    @Test   (expected = java.lang.NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {
    	 mycustomstring.setString("null");
         mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, false);
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {
    	 mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
         assertEquals("'bt t0 6snh r6rh ", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {
    	 mycustomstring.setString("my nick name is uzma");
         assertEquals("n e a", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {
    	 mycustomstring.setString("");
         assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test (expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd14() { //n>0
    	 mycustomstring.setString("null");
         mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, true);
    }

    @Test
    public void testConvertDigitsToNamesInSubstring1() {
    	mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    @Test
    public void testConvertDigitsToNamesInSubstring2() {
    	mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    @Test   (expected=MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring3() {
    	mycustomstring.setString("this is very easy test");
        mycustomstring.convertDigitsToNamesInSubstring(0, 3);
        
    }

    @Test   (expected=IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring4() {
    	mycustomstring.setString("Ihis is very hard test");
        mycustomstring.convertDigitsToNamesInSubstring(4, 3);
        
    }

    @Test         (expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring5() {
    	mycustomstring.setString("I m 33 years old now");
        mycustomstring.convertDigitsToNamesInSubstring(1, 22);
        
    }

    @Test          (expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring6() {
    	mycustomstring.setString("my birthday is on 22 may");
        mycustomstring.convertDigitsToNamesInSubstring(8, 40);
        
    }

    @Test
    public void testConvertDigitsToNamesInSubstring7() {
    	mycustomstring.setString("00101000");
        mycustomstring.convertDigitsToNamesInSubstring(1, 8);
        assertEquals("ZeroZeroOneZeroOneZeroZeroZero", mycustomstring.getString());
    }

    @Test       (expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring8() {
    	mycustomstring.setString("i hate programs");
    	mycustomstring.convertDigitsToNamesInSubstring(0, 3);
      
        
    }

}
