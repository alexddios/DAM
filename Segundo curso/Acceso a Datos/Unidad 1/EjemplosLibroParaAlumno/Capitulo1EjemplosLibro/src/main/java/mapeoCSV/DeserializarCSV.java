package mapeoCSV;

/*
 * Deserializar personas 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class DeserializarCSV {
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new FileReader("personas.csv"));
		CsvToBean<Persona> csvToBean = new CsvToBeanBuilder<Persona>(br).withType(Persona.class)
				.withIgnoreLeadingWhiteSpace(true).build();

		List<Persona> personas = csvToBean.parse();
		for (Persona p : personas) {
			System.out.println(p);
		}
	}
}