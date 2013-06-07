package org.jbpm.demo.common.test;

import org.jbpm.demo.common.util.EncryptionUtil;

public class CryptoTest {

	public static void main(String[] args) throws Exception {

		System.out.println(EncryptionUtil.doEncryption("1213#"));
	}

}
