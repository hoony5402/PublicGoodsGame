
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.Scanner;

public class PGG {
	static final int MAX_PERSON = 4;
	static final int GENERATION = 10000;

	public static void main(String[] args) throws IOException {
//		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter("c:/out.txt");
		String data = "";

//		boolean run = true;
		Person[] community = new Person[MAX_PERSON];
		for (int i = 0; i < MAX_PERSON; i++)
			community[i] = new Person();

		Person.setGene(99999999);
		Person.setGain(90);

		for (int j = 0; j < GENERATION; j++) {
			Person.MUTANT -= Person.DeltaMutant;
			int totalDonation = 0;
			for (int i = 0; i < MAX_PERSON; i++) {
				community[i].setInitiolFunds();
				totalDonation += community[i].donation(i);
			}
			int newGene = 0;
			for (int i = 0; i < MAX_PERSON; i++) {
				newGene *= 10;
				newGene += community[i].repartition(totalDonation, i);
			}
			Person.mkGene(newGene);
			data = Person.getGene() + "";
			pw.println(data);
//			for (int i = 0; i < MAX_PERSON; i++)
//				System.out.println("사람" + (i + 1) + ": " + community[i].getFunds());
		}

		pw.println(-1);

		for (int i = 0; i < 4; i++) {
			int b = 0;
			int max = 0;
			for (int j = 0; j < 10; j++) {
				b += 10;
				if(j%2 == 0 && j != 0)
					System.out.println();
				if (community[i].getMeanProfit(max) < community[i].getMeanProfit(j)
						|| Double.isNaN(community[i].getMeanProfit(max)))
					max = j;
				System.out.print(b - 10 + "%~" + b + "%기부(비중: " + community[i].getNum(j)*100/(double)GENERATION + "%):"
						+ Math.round(community[i].getMeanProfit(j) * 100) / 100.0 + "원 이득 ");
			}
//			System.out.println(max + " " + community[i].getMeanProfit(max));
			System.out.println("\n");
		}

		pw.close();
	}
}
