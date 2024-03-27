package coffee.provider.sa.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.springframework.stereotype.Service;

import coffee.provider.lotto.controller.LottoProviderController;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EncryptionService {
	
	public String desEncrypt(String queryString,String encryptKey) {
		try{
			byte[] key = encryptKey.getBytes();
			KeySpec keySpec = new DESKeySpec(key);
			SecretKey myDesKey = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
			IvParameterSpec iv = new IvParameterSpec(key);

		    Cipher desCipher;
		    desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey, iv);

		    //sensitive information
		    byte[] text = queryString.getBytes();

		    log.info("Text [Byte Format] : " + text);
		    System.out.println("Text : " + new String(text));
		   
		    byte[] textEncrypted = desCipher.doFinal(text);
			String t = Base64.getEncoder().encodeToString(textEncrypted);
			
			log.info("Text Encryted [Byte Format] : " + textEncrypted);
			log.info("Text Encryted : " + t);
		   
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey, iv);
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
		    
		    log.info("Text Decryted : " + new String(textDecrypted));
		    return t;
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(InvalidKeySpecException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(InvalidAlgorithmParameterException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		}
        return null;
    }
	
	public String buildMD5(String plaintext) {
		MessageDigest m;
		byte[] digest;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
