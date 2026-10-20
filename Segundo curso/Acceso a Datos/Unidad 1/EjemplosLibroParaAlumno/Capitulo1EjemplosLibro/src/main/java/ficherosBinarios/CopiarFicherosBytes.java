package ficherosBinarios;

import java.io.*;

public class CopiarFicherosBytes {

		public static void main(String[] args) throws IOException {
			
			FileInputStream in = new FileInputStream(new File("Ffuente.txt"));
			FileOutputStream out = new FileOutputStream(new File("Fdestino.txt"));
			
			int c;
			while ((c = in.read()) != -1)
			out.write(c);
			
			in.close();
			out.close();
	}

}
