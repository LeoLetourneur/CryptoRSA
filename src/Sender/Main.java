package Sender;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		Sender alice = new Sender("Alice");
		Receiver bob = new Receiver("Bob");
		Assailant oscar = new Assailant("Oscar");
		
		BigInteger texteBig = new BigInteger("1317");
		System.out.println("Texte biginteger à transmettre : " +texteBig.toString());
		
		BigInteger encrypt = alice.Encrypt(texteBig, bob.getPublicKey());
		System.out.println("Texte crypté : " +  encrypt.toString());
		
		oscar.getMessages().add(encrypt);
		
		//BigInteger decrypt = bob.Decrypt(encrypt);
		//System.out.println("Texte décrypté : " +  decrypt.toString());
		
		texteBig = new BigInteger("1318");
		System.out.println("Texte biginteger à transmettre : " +texteBig.toString());
		
		encrypt = alice.Encrypt(texteBig, bob.getPublicKey());
		System.out.println("Texte crypté : " +  encrypt.toString());
		
		oscar.getMessages().add(encrypt);
		
		//decrypt = bob.Decrypt(encrypt);
		//System.out.println("Texte décrypté : " +  decrypt.toString());
		
		BigInteger message = oscar.attaque(bob.getPublicKey());
		System.out.println("Hack : "+message);
	}
}
