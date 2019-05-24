import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PGG {
	static final int MAX_PERSON = 4;
	static final int GENERATION = 100000;

	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("c:/out.txt");
		String data = "";

		Person[] community = new Person[MAX_PERSON];
		for (int i = 0; i < MAX_PERSON; i++)
			community[i] = new Person();

		for (int i = 0; i < MAX_PERSON; i++)
			community[i].setGene(1000);
		for (int i = 0; i < MAX_PERSON; i++)
			community[i].setGain(1000);

		for (int j = 0; j < GENERATION; j++) {
			int totalDonation = 0;
			for (int i = 0; i < MAX_PERSON; i++) {
				community[i].setInitiolFunds();
				totalDonation += community[i].donation();
			}
			for (int i = 0; i < MAX_PERSON; i++)
				community[i].repartition(totalDonation);
			for (int i = 0; i < MAX_PERSON; i++)
				data += community[i].getGene();
			pw.println(data);
			data = "";
		}
		pw.println(-1);
		
		
		
		pw.close();
	}
}
