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
import java.util.*;
import java.lang.Math;

public class RSA{
  static final boolean DEBUG = false;
  
  public static void main(String[] args) throws Exception{
    
  String arg = args[0];
 
    if (arg.equals("key") ){
         long p = Integer.parseInt(args[1]);
         long q = Integer.parseInt(args[2]);
         generateKey(p, q);
    }else if (arg.equals("encrypt") ){
    	    String input = args[1];
    	    String key = args[2];
    	    String output = args[3];
    	    encrypt(input,key,output);
    }else if (arg.equals("decrypt") ){
    	    String input = args[1];
    	    String key = args[2];
    	    String output = args[3];
    	    decrypt(input,key,output);
    }else{
    	    System.out.println("Invalid argument \"" + arg + '"');
      return;
    }
 
  }
  
  static void generateKey(long p, long q){
    long n = 0;
    long e = 0;
    long d = 0;
    long phi = (p-1)*(q-1);
    //calculate n
    n = p * q;
    //checking if the higher limit is holding
    assert (Math.pow(2,24) < n);
    assert (Math.pow(2,30) > n);
    
    //choose e st 1<e<n and e and phi are relatively prime.
    for(long i = 2; i<n; i++){
    	    System.out.println(i);
    	    Boolean result = isPrime(i);
    	    System.out.println(result);
    	    if(result == true){
    	    	    if((phi % i) != 0){
			    e = i;
			    break;
		    }
	    }
    }

    //calculate d
    d = gcd(e,phi);

    //output n, e, and d
    System.out.println(n + " " + e + " " + d);
  }

  static Boolean isPrime(long n){
  	if (n == 2) 
  		return true;
  	if (n == 3)
  		return true;
  	if ((n % 2) == 0)
  		return false;
  	if ((n % 3) == 0)
  		return false;
  	
  	long i = 5;
  	long w = 2;
  	while (i * i <= n){
  		if ((n % i) == 0){
  			return false;
  		}
  		i += w;
  		w = 6 - w;
  	}
        return true;
  }
  
  static long gcd(long a, long b){    
  	  return 0;
  }
  
  static void encrypt(String input, String key, String output){
    //encrypting input using the key
    //print it in the output file
  }
  
  static void decrypt(String inpu2t, String key, String output){
    //decrypting input using the key
//print it in the output file
  }
}