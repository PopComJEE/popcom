package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Hash {


	public static String encode(String s){
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashOne = sha.digest(s.getBytes());
			sha.reset();
			return hexEncode(hashOne);
		}
		catch (NoSuchAlgorithmException ex){
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean compare(String readable, String encoded){
		return encode(readable).equals(encoded);
	}

	/**
	 * The byte[] returned by MessageDigest does not have a nice
	 * textual representation, so some form of encoding is usually performed.
	 *
	 * This implementation follows the example of David Flanagan's book
	 * "Java In A Nutshell", and converts a byte array into a String
	 * of hex characters.
	 */
	static private String hexEncode( byte[] aInput){
		StringBuffer result = new StringBuffer();
		char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append( digits[ (b&0xf0) >> 4 ] );
			result.append( digits[ b&0x0f] );
		}
		return result.toString();
	}  
}