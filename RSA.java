/*
Ian Buitrago: Slip days used for this project: 0  Slip days used (total): 1
Mikita Roharia: Slip days used for this project: 0  Slip days used (total): 0
CS 337
project 2
10/21/2011

Pair programming log (> 80% paired)
10/11 7 - 8p  Ian, 1 hr
10/11 7 - 8p  Mikita, 1 hr
10/14 2:30p - 5:30p Ian, Mikita, 6 hrs
10/18 11a - 12:30p Ian, Mikita, 3 hrs
10/18 1p - 3p Ian,  2 hrs
10/19 5a - 8a Ian,  3 hrs

Total time 22 hrs, 20 hrs of pair programing

Challenges: checking the encryped output, binary files cannont be viewed

Learned:
-bit manipulation
-assert statements can make hard to detect logic errors into runtime errors

Notes:
Java 6

to do:
-generate list of key test cases

run with commands:
javac *.java
java -ea RSA key p q
java -ea RSA encrypt infile keyfile outfile
java -ea RSA decrypt infile keyfile outfile
or with 
./runTest #make sure it's executable
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
			File inFile = new File(args[1]);
			File key = new File(args[2]);
			File outFile = new File(args[3]);
			encrypt(inFile, key, outFile);
	
		}else if (arg.equals("decrypt") ){
			File inFile = new File(args[1]);
			File key = new File(args[2]);
			File outFile = new File(args[3]);
			decrypt(inFile, key, outFile);
	
		}else if(arg.equals("calc")){
			long answer = calculations(4,7,187);
		}else{
			System.out.println("Invalid argument \"" + arg + '"');
		}
		
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
		d = gcd(phi,e);
	
		//output n, e, and d
		System.out.println(n + " " + e + " " + d);
		if(DEBUG) System.out.println(2773 + " " + 17  + " " + 157 + " ***SAMPLE OUTPUT***");
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
	
	static long gcd(long u, long v){
		long s = 1;
		long t = 0;
		long c = 0;
		long d = 1;
		long q;
		
		while (v != 0){
			q = (int)u/v;
			long temp_u = u;
			u = v;
			v = temp_u - (v*q);
			long temp_s = s;
			s = c;
			long temp_t = t;
			t = d;
			c = temp_s - (c*q);
			d = temp_t - (d*q);
		}
		return t;
	}
	
	//Encrypts input using the key (n, e, d) and writes it to the output file
	static void encrypt(File inFile, File key, File outFile) throws Exception
	{
		if(DEBUG) System.out.println("\nEncrypting...");
		DataInputStream in = new DataInputStream( new FileInputStream(inFile) );
		DataOutputStream out = new DataOutputStream( new FileOutputStream(outFile) );
		/*
		//debugging: generates a custom bin file and sets inputstream to new file
		if(DEBUG){
		  inFile = new File("custom");
		  out = new DataOutputStream( new FileOutputStream(inFile) );
		  in = new DataInputStream( new FileInputStream(inFile) );
		  out.writeByte(0x8B);		//msb == 1
		  out.writeByte(0x7C);		//msb == 0
		  out.writeByte(0xF0);		//msb == 1
		  out.writeByte(0xAB);		//msb == 1
		  out.writeByte(-1);		//negative
		  out = new DataOutputStream( new FileOutputStream(outFile) );
		}
		*/
		if(DEBUG) System.out.println("inFile: " + inFile);
		long fileSize = inFile.length();
		if(DEBUG) System.out.println("fileSize: " + fileSize + " bytes");
		long total = 0;		//total number of bytes read
		int s = 0;		//shift multiplier
		
		Scanner sc = new Scanner(key);
		long n = sc.nextLong();
		long e = sc.nextLong();
		long d = sc.nextLong();
		if(DEBUG) System.out.println("n: " + n);
		if(DEBUG) System.out.println("e: " + e);
		if(DEBUG) System.out.println("d: " + d);
		sc.close();
		assert (n > Math.pow(2,24)): "n must be big enough to encrypt 3 bytes";
		
		
		
		try{
			while(true){		//run till end of file
				byte b = 0;
				long M = 0;		//8 bytes long
				long mask = 0x00FF;		//mask out last byte in long 
				
				for(s = 2; s >= 0; s--, total++){		//read in 3 bytes
					if (total == fileSize){
						if(DEBUG) System.out.println("last byte read.");
						break;	//break for loop to encrypt M
					}
					long t = 0;
					b = in.readByte();
					if(DEBUG) System.out.println(String.format(" in.readByte   = 0x%1$X, %1$d", b));
					t = b;		//implicit cast to long (possible sign extend if MSB is 1)
					t &= mask;		//eliminates sign extention
					//if(DEBUG) System.out.println(String.format(" t           = 0x%1$X, %1$d", t) );
					t <<= 8 * s;
					M |= t;		// or byte into M
				}
				
				if(DEBUG) System.out.println(String.format("M           = 0x%1$X, %1$d", M) );
				assert(n > M ): "n must be greater than M";
				
				long Mprime = calculations(M, e, n);
				if(DEBUG) System.out.println(String.format("Mprime      = 0x%1$X, %1$d", Mprime) );		//outputs the int in hex form
				
				//if(DEBUG) System.out.println("test: " + String.format("0x%1$X", (long)(Math.pow(2,32)) ) );
				assert(Mprime >= 0 && Mprime < Math.pow(2,32) ):
					"Encrypted number(long Mprime) " + String.format("0x%1$X", Mprime)
					+ " requires more than 4 bytes(byte concatenation error)";
				
				//int i = (int)Mprime;		//cast to int
				//if(DEBUG) System.out.println(String.format("(int)Mprime = 0x%1$X", i) );		//outputs the int in hex form
				
				out.writeInt( (int)Mprime );		//write the 4 lower bytes as int
				
				if (total == fileSize){
					throw new EOFException("File read successfully.");
				}
			}
		}catch(EOFException ex){
			if(DEBUG) System.out.println("EOF: " + ex);}
		
		in.close();
		out.close();
		
		if(DEBUG) System.out.println("outFile: " + outFile);
	}

	static long calculations(long M,long e,long n) {
	    //ENCRYPTION = M,e,n
	    //DECRYPTION = Mprime,d,n
	    //public key = (e,n)
	    //M = inFile
	    //M' = outFile
	    //M' = M^e(mod n)
	    long x = 1;
	    long y = M;	
	    long Mprime;
	    
	    while (e > 0){
		  if (e % 2 == 1){
			  x= (x*y) % n;
		  }
		  y = (y*y) % n; 
		  e = e / 2;
	    }
	    Mprime = x % n;
	    
	    return Mprime;
	}
	
	static void decrypt(File inFile, File key, File outFile) throws Exception{
		if(DEBUG) System.out.println("\nDecrypting...");
		//decrypting input using the key
		//print it in the output file
		DataInputStream in = new DataInputStream( new FileInputStream(inFile) );
		DataOutputStream out = new DataOutputStream( new FileOutputStream(outFile) );
		/*
		//debugging: generates a custom bin file and sets inputstream to new file
		if(DEBUG){
		  inFile = new File("custom");
		  out = new DataOutputStream( new FileOutputStream(inFile) );
		  in = new DataInputStream( new FileInputStream(inFile) );
		  out.writeByte(0x8B);		//msb == 1
		  out.writeByte(0x7C);		//msb == 0
		  out.writeByte(0xF0);		//msb == 1
		  out.writeByte(0xAB);		//msb == 1
		  out.writeByte(-1);		//negative
		  out = new DataOutputStream( new FileOutputStream(outFile) );
		}
		*/
		if(DEBUG) System.out.println("inFile: " + inFile);
		long fileSize = inFile.length();
		if(DEBUG) System.out.println("fileSize: " + fileSize + " bytes");
		long total = 0;		//total number of bytes read
		
		Scanner sc = new Scanner(key);
		long n = sc.nextLong();
		long e = sc.nextLong();
		long d = sc.nextLong();
		if(DEBUG) System.out.println("n: " + n);
		if(DEBUG) System.out.println("e: " + e);
		if(DEBUG) System.out.println("d: " + d);
		sc.close();
		assert (n > Math.pow(2,24)): "n must be big enough to encrypt 3 bytes";
		
		
		
		try{
			while(true){		//run till end of file
				long M = 0;		//8 bytes long
				long mask = 0x00FF;		//mask out last byte in long 
				
				for(int s = 3; s >= 0; s--, total++){		//read in 4 bytes for decryption
					byte b = 0;
					if (total == fileSize){
						if(DEBUG) System.out.println("last byte read.");
						break;	//break for loop to encrypt M
					}
					long t = 0;
					b = in.readByte();
					if(DEBUG) System.out.println(String.format(" in.readByte   = 0x%1$X, %1$d", b));
					t = b;		//implicit cast to long (possible sign extend if MSB is 1)
					t &= mask;		//eliminates sign extention
					//if(DEBUG) System.out.println(String.format(" t           = 0x%1$X, %1$d", t) );
					t <<= 8 * s;
					M |= t;		// or byte into M
				}
				
				if(DEBUG) System.out.println(String.format("M           = 0x%1$X, %1$d", M) );
				assert(n > M ): "n must be greater than M";
				
				long Mprime = calculations(M, d, n);		// M, d, n for decryption
				if(DEBUG) System.out.println(String.format("Mprime      = 0x%1$X, %1$d", Mprime) );		//outputs the int in hex form
				
				//if(DEBUG) System.out.println("test: " + String.format("0x%1$X", (long)(Math.pow(2,32)) ) );
				assert(Mprime >= 0 && Mprime < Math.pow(2,32) ):
					"Encrypted number(long Mprime) " + String.format("0x%1$X", Mprime)
					+ " requires more than 4 bytes(byte concatenation error)";
				
				//if(DEBUG) System.out.println(String.format("(int)Mprime = 0x%1$X", i) );		//outputs the int in hex form
				
				//write 3 lower bytes
				byte[] b = new byte[3];
				b[0] = (byte)(Mprime >> 8*2);
				b[1] = (byte)(Mprime >> 8);
				b[2] = (byte)Mprime;
				
				out.writeByte(b[0]);	//always write this MSByte
				
				//don't write eof zeroes
				if (total == fileSize){		//last Mprime
					if(b[2] != 0 || b[1] != 0)
						out.writeByte(b[1]);
					
					if(b[2] != 0)
						out.writeByte(b[2]);		//write least sig byte
					
					throw new EOFException("File read successfully.");
				}else{		//default
					out.writeByte(b[1]);
					out.writeByte(b[2]);		//write least sig byte
				}
			}
		}catch(EOFException ex){
			if(DEBUG) System.out.println("EOF: " + ex);}
		
		in.close();
		out.close();
		
		if(DEBUG) System.out.println("outFile: " + outFile);
	}
}
