package edu.qc.seclass;

public class MyCustomString implements MyCustomStringInterface{
	
	 private String current = null;
               // Returns the current string. If the string is null, it should return null.

     public String getString(){
    	 
       return this.current;                 //Sets the value of the current string.
      }

     public void setString(String string)
        {
          this.current = string;
              //Returns the number of numbers in the current string, where a number is defined as a

        }
     public int countNumbers(){

     if (this.current.isEmpty()){
               return 0;
        }

      if (this.current== null){
              throw new NullPointerException("NullPointerException: the current string is null");
        }
      int countdigit=0;

       for(int i =0; i <this.current.length(); i++) {

          char example = this.current.charAt(i);

          if(!Character.isDigit(example)){
                continue;

            }else { 

              countdigit++;
               }if (i == this.current.length()-1){
                    countdigit++;

                   }else if (Character.isDigit(this.current.charAt(i+1))){
                     continue;
                        }
               }
         return countdigit;

          }

    public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd){

      String output = " ";

        if(this.current.isEmpty()||this.current.length()<n) {  //then either it is empty or less then n
            return "";    //return empty string
           }

         if(n <= 0) {
            throw new IllegalArgumentException("n is either less then zero or is 0 ");
             }

          if(n > 0 && this.current == null) {
            throw new NullPointerException("NullPointerException: the current string is null and is greater than zero");
             }

          String str="";  //  string

           if(startFromEnd==false) {  //donot flip the string
              str=this.current;
              }else { //flip the string 
               str=new StringBuilder(this.current).reverse().toString();
                }

           int begin=1;
           while((n*begin)<=str.length()) {
           int index=(n*begin)-1;     //get actual index
           output=output+String.valueOf(str.charAt(index));	
           begin++;    
           } 

           if(startFromEnd==true) {  //flip the result if to start from end
           output=new StringBuilder(output).reverse().toString();
           }

         return output;

        }
  public void convertDigitsToNamesInSubstring(int startPosition, int endPosition)
    {
    if(startPosition > endPosition) {
    throw new IllegalArgumentException("Illegal Argument: startPosition is greater than endPosition");
    }

    if((startPosition <=endPosition)&& (startPosition<1|| endPosition<1 || startPosition>this.current.length()||endPosition>this.current.length())) {
    throw new MyIndexOutOfBoundsException("Index out of bounds");
    }
 
    if(startPosition<=endPosition && (startPosition>0 && endPosition>0)&& this.current==null) {
    throw new NullPointerException("NullPointerException  string is empty");
     }


     String str="";
     String leftmost="";   
     String rightmost="";   
    //the first letter is considered to be 1
    int starts=startPosition-1;
    int ends=endPosition;
    str=current.substring(starts, ends);  //str holds a substring of the current string

    leftmost=current.substring(0, starts);
    rightmost=current.substring(ends);
       //replacing all digits with their numerical words

    str=str.replaceAll("0", "Zero");
    str=str.replaceAll("1", "One");
    str=str.replaceAll("2", "Two");
    str=str.replaceAll("3", "Three");
    str=str.replaceAll("4", "Four");
    str=str.replaceAll("5", "Five");
    str=str.replaceAll("6", "Six");
    str=str.replaceAll("7", "Seven");
    str=str.replaceAll("8", "Eight");
    str=str.replaceAll("9", "Nine");

     setString(leftmost+str+rightmost);  //set strings to Leftmostside to  Rightmostside




        }
}




