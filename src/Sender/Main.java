package Sender;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		
		Sender alice = new Sender("Alice");
		Receiver bob = new Receiver("Bob");
		Assailant oscar = new Assailant("Oscar");
		
		String sentence = "Salut c'est Jack";
		System.out.println("Texte à transmettre : " +sentence);
		
		BigInteger texteBig = new BigInteger(Actor.StringToInt(sentence));
		System.out.println("Texte biginteger à transmettre : " +texteBig.toString());
		
		BigInteger encrypt = alice.Encrypt(texteBig, bob.getPublicKey());
		System.out.println("Texte crypté : " +  encrypt.toString());
		
		oscar.getMessages().add(encrypt);
		
		//BigInteger decrypt = bob.Decrypt(encrypt);
		//System.out.println("Texte décrypté : " +  decrypt.toString());
		
		sentence = "Salut c'est Jacl";
		
		texteBig = new BigInteger(Actor.StringToInt(sentence));
		System.out.println("Texte biginteger à transmettre : " +texteBig.toString());
		
		encrypt = alice.Encrypt(texteBig, bob.getPublicKey());
		System.out.println("Texte crypté : " +  encrypt.toString());
		
		oscar.getMessages().add(encrypt);
		
		//decrypt = bob.Decrypt(encrypt);
		//System.out.println("Texte décrypté : " +  decrypt.toString());
		
		BigInteger message = oscar.attaque(bob.getPublicKey());
		System.out.println(message.toString());
		System.out.println("Hack code : "+message);
		System.out.println("Hack texte : "+Actor.IntToString(message.toString()));
	}
}
