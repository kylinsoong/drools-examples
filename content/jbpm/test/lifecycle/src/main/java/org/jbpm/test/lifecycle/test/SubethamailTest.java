package org.jbpm.test.lifecycle.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.mail.MessagingException;

import org.subethamail.wiser.Wiser;

public class SubethamailTest {

	public static void main(String[] args) throws MessagingException, IOException {

		Wiser wiser = new Wiser();
		wiser.setHostname("localhost");
		wiser.setPort(1125);
		wiser.start();

		String line = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		do {
			line = in.readLine();
			line = line.trim();

			if ("dump".equals(line))
				wiser.dumpMessages(System.out);

			if (line.startsWith("dump ")) {
				line = line.substring("dump ".length());
				File f = new File(line);
				OutputStream out = new FileOutputStream(f);
				wiser.dumpMessages(new PrintStream(out));
				out.close();
			}
		} while (!"quit".equals(line));

		wiser.stop();
	}

}
