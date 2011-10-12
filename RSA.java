/*
Ian Buitrago: Slip days used for this project: 0  Slip days used (total): 1
Mikita Roharia: Slip days used for this project: 0  Slip days used (total): 0
CS 337
project 2
10/21/2011

Pair programming log (> 80% paired)
10/11 7 - 8p  Ian, 1 hr
10/12 7p - 10p Ian, Mikita, 6 hrs

Total time 22 hrs, 20 hrs of pair programing

Challenges: Converting the first argument from string to character
(Mikita) Creating the trie data structure.
(Ian) Understanding the LZ algorithm 

Learned: RSA
More Java API

Notes:
2 space indent (google standard)

run with commands:
javac *.java
java -ea RSA key p q
java -ea RSA encrypt infile keyfile outfile
java -ea RSA decrypt infile keyfile outfile
*/

public class RSA{
  static final boolean DEBUG = false;
  
  public static void main(String[] args) throws Exception{
    
  	String arg = args[0];
  	
  	if (arg.equals("key") ){
  	  int p = Integer.parseInt(args[1]);
  	  int q = Integer.parseInt(args[2]);
  	  // 2^24 < p,q < 2^30
  	  assert (Math.pow(2,24) < p);
  	  
      generateKey(p, q);
    }else if (arg.equals("encrypt") )
      encrypt();
    else if (arg.equals("decrypt") )
      decrypt();
    else{
      System.out.println("Invalid argument \"" + arg + '"');
      return;
    }
  	
  }
  
  static void generateKey(int p, int q){
    int n = 0;
    int e = 0;
    int d = 0;
    
  	//output n, e, and d
    System.out.println(n + " " + e + " " + d);
  }
  
  static void encrypt(){
    
  }
  
  static void decrypt(){
    
  }
}
