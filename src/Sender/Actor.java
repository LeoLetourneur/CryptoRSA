package Sender;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Actor {

	public static final boolean DEBUG = false;
	public static final int KEY_SIZE = 512;
	public static final int GEN_SIZE = 16;
	
	private String name;
	private boolean forceE3;
	private HashMap<String, BigInteger> keys;
    
	public Actor(String p_name) {
		
		this.setName(p_name);
		this.setForceE3(false);
		this.setKeys(new HashMap<>());
	}

	public ArrayList<BigInteger> getPublicKey() {
        ArrayList<BigInteger> retour = new ArrayList<>();
        retour.add(this.keys.get("e"));
        retour.add(this.keys.get("n"));
        return retour;
    }
	
	public BigInteger Encrypt(BigInteger x, ArrayList<BigInteger> pKeys) {
        return x.modPow(pKeys.get(0), pKeys.get(1));
    }

	public BigInteger Decrypt(BigInteger x) {
        return x.modPow(this.getKeys().get("d"), this.getKeys().get("n"));
    }
	
	public void Keygen() {
		
		Random rand = new Random();
        HashMap<String, BigInteger> keygen = new HashMap<>();
        
        BigInteger e = new BigInteger(GEN_SIZE, 2, rand);
        if(forceE3) e = new BigInteger("3");
        System.out.println("e : " + e.toString());
        
        BigInteger p, q, n, phi_n;
        do {
	        p = new BigInteger(KEY_SIZE, 2, rand);
	        while (!primalite_fermat(p)) { p = p.nextProbablePrime(); }
	        if(DEBUG) System.out.println("p premier : " + p.toString());
	        
	        q = new BigInteger(KEY_SIZE, 2, rand);
	        while (!primalite_fermat(q)) { q = q.nextProbablePrime(); }
	        if(DEBUG) System.out.println("q premier : " + q.toString());
	        
	        n = p.multiply(q);
	        if(DEBUG) System.out.println("n : " + n.toString());
	        
	        phi_n = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
	        if(DEBUG) System.out.println("phi_n : " + phi_n.toString());
        
        } while (!phi_n.gcd(e).equals(BigInteger.ONE));
        
        keygen.put("n", n);
        keygen.put("e", e);
        
		try {
			BigInteger d = e.modInverse(phi_n);
			keygen.put("d", d);
			if(DEBUG) System.out.println("d : "+d);
		} catch(ArithmeticException exce) {
			System.out.println(exce.getMessage());
			//System.exit(-1);
		}
		
        setKeys(keygen);
    }
	
	public static boolean primalite_fermat(BigInteger m) {
        return new BigInteger("2").modPow(m.subtract(BigInteger.ONE), m).equals(BigInteger.ONE);
    }
	
	public static String IntToString(String s) {
        String msg = "";
        while (s.length() % 3 != 0) {
            s = "0" + s;
        }
        for (int i = 0; i < s.length() / 3; i++) {
            int temp = Integer.parseInt(s.subSequence(i * 3, (i + 1) * 3).toString());
            msg += Character.toString((char) temp);
        }
        return msg;
    }

    public static String StringToInt(String s) {
        String msg = "";
        for (int i = 0; i < s.length(); i++) {
            String character = ((int) s.charAt(i)) + "";
            while (character.length() < 3) {
                character = "0" + character;
            }
            msg += character;
        }
        return msg;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isForceE3() {
		return forceE3;
	}

	public void setForceE3(boolean forceE3) {
		this.forceE3 = forceE3;
	}

	public HashMap<String, BigInteger> getKeys() {
		return keys;
	}

	public void setKeys(HashMap<String, BigInteger> keys) {
		this.keys = keys;
	}
	
	public String toString() {
		
		return "Actor "+this.getName();
	}
}
