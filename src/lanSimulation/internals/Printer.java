package lanSimulation.internals;

import java.io.IOException;
import java.io.Writer;

import lanSimulation.Network;

public class Printer extends Node {

	public Printer(byte type, String name) {
		super(type, name);
		// TODO Auto-generated constructor stub
	}

	public boolean printDocument(Packet packet, Network network, Writer report) {
		String author = "Unknown";
		String title = "Untitled";
		int startPos = 0, endPos = 0;
		try {
			if (packet.message_.startsWith("!PS")) {
				startPos = packet.message_.indexOf("author:");
				if (startPos >= 0) {
					endPos = packet.message_.indexOf(".", startPos + 7);
					if (endPos < 0) {
						endPos = packet.message_.length();
					}
					;
					author = packet.message_.substring(startPos + 7, endPos);
				}
				;
				startPos = packet.message_.indexOf("title:");
				if (startPos >= 0) {
					endPos = packet.message_.indexOf(".", startPos + 6);
					if (endPos < 0) {
						endPos = packet.message_.length();
					}
					;
					title = packet.message_.substring(startPos + 6, endPos);
				}
				;
				network.accounting(report, author, title);
				report.write(">>> Postscript job delivered.\n\n");
				report.flush();
			} else {
				title = "ASCII DOCUMENT";
				if (packet.message_.length() >= 16) {
					author = packet.message_.substring(8, 16);
				}
				;
				network.accounting(report, author, title);
				report.write(">>> ASCII Print job delivered.\n\n");
				report.flush();
			}
			;
		} catch (IOException exc) {
			// just ignore
		}
		;
		return true;

	}

}
