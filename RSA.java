/*
Ian Buitrago: Slip days used for this project: 0  Slip days used (total): 1
Mikita Roharia: Slip days used for this project: 0  Slip days used (total): 0
CS 337
project 2
10/21/2011

Pair programming log (> 80% paired)
10/11 7 - 8p  Ian, 1 hr
10/14 2:30p - 5:30p Ian, Mikita, 6 hrs

Total time 22 hrs, 20 hrs of pair programing

Challenges: Converting the first argument from string to character
(Mikita) Creating the trie data structure.
(Ian) Understanding the LZ algorithm 

Learned: RSA
More Java API

Notes:
Java 6

run with commands:
javac *.java
java -ea RSA key p q
java -ea RSA encrypt infile keyfile outfile
java -ea RSA decrypt infile keyfile outfile
*/
import java.util.*;
import java.io.*;
import java.lang.Math;

public class RSA{
	static final boolean DEBUG = true;
	
	public static void main(String[] args) throws Exception
	{
		String arg = args[0];
		/* java 7
		switch('a'){
		case 'a':
			System.out.println("BOOYAKASHA");
		}*/
		
		// measure elapsed time
		long start = System.currentTimeMillis();
		
		if (arg.equals("key") ){
			 long p = Integer.parseInt(args[1]);
			 long q = Integer.parseInt(args[2]);
			 if(DEBUG) System.out.println("p: " + p);
			 if(DEBUG) System.out.println("q: " + q);
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
		}
		
		if(DEBUG) System.out.println("hex: "+ String.format("%1$X", 13) );
		
		// time of execution
		long elapsed = System.currentTimeMillis() - start;
		if(DEBUG) System.out.println("elapsed run time: "+ elapsed +"ms");
  }
  
	static void generateKey(long p, long q){
		long n = 0;
		long e = 0;
		long d = 0;
		long phi = (p-1)*(q-1);
		//calculate n
		n = p * q;
		//checking if the higher limit is holding
		//assert (Math.pow(2,24) < n);
		//assert (Math.pow(2,30) > n);
		
		//choose e st 1<e<n and e and phi are relatively prime.
		for(long i = 2; i < n; i++){
			if(DEBUG) System.out.println("i: " + i);
				Boolean result = isPrime(i);
				if(DEBUG) System.out.println("isPrime: " + result);
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
		if(DEBUG) System.out.println(2773 + " " + 17  + " " + 157 + " ***SAMPLE OUTPUT***");
	}
	
	static long gcd(long a, long b){
		
		
		return 0;
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
	
	static void encrypt(String inFile, String key, String outFile) throws Exception
	{
		//encrypting input using the key
		//print it in the output file
		if(DEBUG) System.out.println("inFile: " + inFile);
		
		DataOutputStream out = new DataOutputStream( new FileOutputStream(outFile) );
		
		out.writeByte(0x24);
		out.writeByte(0x9);
		out.writeByte(0x27);
		/*out.writeByte(128);	//overflow
		out.writeByte(129);	//overflow
		out.writeByte(-127);
		out.writeByte(-128);	// in range
		out.writeByte(-129);	//overflow
		out.writeInt(10);		//4 bytes
		out.writeLong(20);		//8 bytes
		//out.writeInt(17);
		//out.writeInt(255);
		//out.writeInt(256);
		*/
		out.close();
		
		try{
			DataInputStream in = new DataInputStream( new FileInputStream(inFile) );
			
			for(int i = 0; true|| i < 2; i++){
				byte b = 0;
				long l = 0;
				for(int s = 2; s >= 0; s--){		//read in 3 bytes
					long t = 0;
					b = in.readByte();
					if(DEBUG) System.out.println("in.readByte: " + b);
					t = b;
					t <<= 8 * s;		// same as l * 2^8 or 256*l
					l |= t;
					
					if(DEBUG) System.out.println(String.format("l = 0x%1$X", l) );
				}
			}
				
			in.close();
		}catch(EOFException e){
			if(DEBUG) System.out.println("EOF: " + e);
		}	
	}

/*
	static void encrypt (String inFile, String key, String outFile){
		try{
			byte byte_one = 0;
			byte byte_two = 0;
			byte byte_three = 0;
			byte next = 5;
			long word = 0;
			DataInputStream in = new DataInputStream( new FileInputStream(inFile));
			DataOutputStream out = new DataOutputStream (new FileOutputStream(outFile));

			while(in != null){
				byte_one = in.readByte();
				byte_two = in.readByte();
				byte_three = in.readByte();
				byte_one++;
				//concatenate all the bytes into the word

				//make changes to the word

				//figure out how to write a long... instead of just a byte. or if not, just write the bytes.
				System.out.println("word:"+word);
				
				out.write(byte_one);
				out.write(byte_two);
				out.write(byte_three);
				out.write(next);
			}
			out.close();
		}catch(Exception e){}
	}
	*/
	static void decrypt(String inpu2t, String key, String output){
		//decrypting input using the key
		//print it in the output file
		
		
	}
}
