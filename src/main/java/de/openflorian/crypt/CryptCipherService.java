package de.openflorian.crypt;

import de.openflorian.OpenflorianApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.openflorian.config.OpenflorianConfig;
import de.openflorian.crypt.provider.BlowfishCipher;
import de.openflorian.crypt.provider.Md5Cipher;
import de.openflorian.crypt.provider.XorCipher;

/**
 * Crypt Cipher Web Service Resource<br/>
 * <br/>
 * Integrates following crypt cipher resources:<br/>
 * - {@link BlowfishCipher}<br/>
 * - {@link XorCipher}<br/>
 * - {@link Md5Cipher}<br/>
 * <br/>
 * Important:<br/>
 * ==========<br/>
 * For correct usage of blowfish cipher, the JVM / JRE has to be extended with:
 * <br/>
 * Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy<br/>
 * {@link http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html}
 * <br/>
 * <br/>
 * Keys are stored web service side.<br/>
 * 
 * @author Bastian Kraus <bofh@k-hive.de>
 */
public class CryptCipherService {

	private static final Logger log = LoggerFactory.getLogger(CryptCipherService.class);

	public static final String SECRET_BLIND_STRING = "*******";

	private static CryptCipherService instance;

	public static CryptCipherService service() {
		if (instance == null) {
			instance = new CryptCipherService();
		}
		return instance;
	}

	private final BlowfishCipher blowfishCipher;
	private final XorCipher xorCipher;
	private final Md5Cipher md5Cipher;

	public enum CipherTarget {
		Blowfish, Xor, MD5
	}

	/**
	 * Singleton CTOR
	 */
	private CryptCipherService() {
		log.info("Initializing CryptCipherService...");

		this.xorCipher = new XorCipher(OpenflorianApplication.getConfig().getCipherKeys().getXor());
		this.blowfishCipher = new BlowfishCipher(OpenflorianApplication.getConfig().getCipherKeys().getBlowfish());
		this.md5Cipher = new Md5Cipher();

		log.info(".. finished!");
	}

	/**
	 * Encrypt {@code plaintext} by {@link CipherTarget} {@code target}
	 * 
	 * @param plaintext
	 *            plain {@link String} to encrypt
	 * @param target
	 *            {@link CipherTarget}
	 * @return {@link String}
	 */
	public String encrypt(String plaintext, CipherTarget target) {
		try {
			switch (target) {
			case Blowfish:
				return this.blowfishCipher.encrypt(plaintext);
			case Xor:
				return this.xorCipher.encrypt(plaintext);
			case MD5:
				return this.md5Cipher.encrypt(plaintext);
			default:
				throw new RuntimeException("Method not implemented.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Decrypt {@code cryptedtext} by {@link CipherTarget} {@code target}
	 * 
	 * @param cryptedtext
	 *            crypted {@link String} to encrypt
	 * @param target
	 *            {@link CipherTarget}
	 * @return {@link String}
	 */
	public String decrypt(String cryptedtext, CipherTarget target) {
		try {
			switch (target) {
			case Blowfish:
				return this.blowfishCipher.decrypt(cryptedtext);
			case Xor:
				return this.xorCipher.decrypt(cryptedtext);
			default:
				throw new RuntimeException("Method not implemented.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
