package model;

import javax.crypto.spec.SecretKeySpec;

import exception.ExceptionDialog;
import exception.PassException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * @author Abhisek Jana
 * @see <a href=
 *      "http://www.adeveloperdiary.com/java/how-to-easily-encrypt-and-decrypt-text-in-java/">How
 *      to easily encrypt and decrypt text in Java</a>
 *
 */
public class PasswordHandler {
	/**
	 * Encrypts a string of text using the javax crypto library, using Blowfish
	 * algorithm
	 * 
	 * @param strClearText
	 *            String of text that needs encryption
	 * @param strKey
	 *            String of text that will be used by the hashing algorithm to
	 *            encrypt
	 * @return a hashed encrypted string of text using the algorithm
	 * @throws Exception
	 */
	public static String encrypt(String strClearText, String strKey) throws PassException {
		String strData = "";

		try {
			SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted = cipher.doFinal(strClearText.getBytes());
			strData = new String(encrypted);

		} catch (NoSuchAlgorithmException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"There was a problem with the encryption algorithm.", e);
			exWin.show();
		} catch (BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"The hashed password is corrupted or was unable to be saved.", e);
			exWin.show();
		} catch (InvalidKeyException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"The encryption key provided does not match the one provided to decrypt.", e);
			exWin.show();
		} catch (Exception e) {
			throw new PassException("Fatal: Encryption problem during password handling.");
		}
		return strData;
	}

	/**
	 * Decrypts a string of text using the javax crypto library, using Blowfish
	 * algorithm
	 * 
	 * @param strEncrypted
	 *            String of text that will be decrypted
	 * @param strKey
	 *            String of text that will be used by the hashing algorithm to
	 *            decrypt
	 * @return a decrypted string of text using the same parameters used to encrypt
	 * @throws Exception
	 */
	public static String decrypt(String strEncrypted, String strKey) throws PassException {
		String strData = "";

		try {
			SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
			strData = new String(decrypted);

		} catch (NoSuchAlgorithmException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"There was a problem with the encryption algorithm.", e);
			exWin.show();
		} catch (BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"The hashed password is corrupted or was unable to be loaded.", e);
			exWin.show();
		} catch (InvalidKeyException e) {
			ExceptionDialog exWin = new ExceptionDialog("Critical Error",
					"The encryption key provided does not match the one provided to decrypt.", e);
			exWin.show();
		} catch (Exception e) {
			throw new PassException("Fatal: Encryption problem during password handling.");
		}
		return strData;
	}
}
