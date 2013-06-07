package org.jbpm.demo.common.mail.crypto;

import org.jbpm.demo.common.util.EncryptionUtil;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println(EncryptionUtil.doEncryption(args[0]));
	}

}
