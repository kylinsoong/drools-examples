package org.jbpm.demo.common.test;

import org.jbpm.demo.common.util.EncryptionUtil;

public class CryptoTest {

	public static void main(String[] args) throws Exception {
		
		String encryption = "Soong1214#";
		
		encryption = EncryptionUtil.doEncryption(encryption);

		System.out.println(encryption);
		
		System.out.println(EncryptionUtil.doDecryption(encryption));
	}

}
