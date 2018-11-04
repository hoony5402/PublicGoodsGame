import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Person {
	Scanner sc = new Scanner(System.in);
	static Random rand = new Random();
	private long[] pfSum = new long[10];
	private int[] num = new int[10];
	private static int[] profit = new int[4];
	private static int gene;

	static final int GENE_LEN = 8;
	private static int[] gain = new int[GENE_LEN];
	static final int INITIAL_MONEY = 900;
	static double MUTANT = 1 * 100;
	static double DeltaMutant = 0;
	private int funds;

	public void setInitiolFunds() {
		funds = INITIAL_MONEY;
	}

	public int getFunds() {
		return funds;
	}

	public static void setGene(int n) {
		gene = n;
	}

	public static void setGain(int n) {
		for (int i = 0; i < 8; i++)
			gain[i] = n;
	}

	public int getNum(int donation) {
		return num[donation];
	}

	public double getMeanProfit(int donation) {
		return pfSum[donation] / (double) num[donation];
	}

	public int first() {
//		System.out.print("기부할 금액을 입력하세요> ");
		int money = rand.nextInt(INITIAL_MONEY / 100 + 1) * 100;
//		System.out.println(money);
//		money = INITIAL_MONEY;
		funds -= money;
		return money;
	}

	public int select(int money) {
		funds -= money * 100;
		return money * 100;
	}

	public int mutant(int money) {
		int randint = rand.nextInt(10);
		int reMon = money;
		if (randint == 0)
			reMon -= 2;
		else if (randint < 5)
			reMon -= 1;
		else if (randint >= 9)
			reMon += 2;
		else
			reMon += 1;
//		System.out.println(reMon);
		if (reMon >= 0 && reMon <= 9)
			return reMon;
		else
			return mutant(money);
	}

	public static int pow(int n) {
		if (n == 0)
			return 1;
		else
			return 10 * pow(n - 1);
	}

	public int donation(int idx) {
		int money = -1;
		int totalGain = 0;
		for (int i = 0; i < GENE_LEN; i++) {
			if (gain[i] > 0)
				totalGain += gain[i];
		}
		double gainRate = (10000.0 - MUTANT) / totalGain;
		int randint = rand.nextInt(10000);
		double bound = 0;
		for (int i = 0; i < GENE_LEN; i++) {
			if (gain[i] > 0) {
				bound += gain[i] * gainRate;
				if (bound > randint) {
					money = (gene / pow(GENE_LEN - i - 1)) % 10;
					break;
				}
			}
		}
		if (money == -1) {
			int geneSum = 0;
			int m = gene;
			for (int i = 0; i < 8; i++) {
				geneSum += m % 10;
				m /= 10;
			}
			money = mutant(geneSum / 8);
//			System.out.println(money);
		}
		funds -= money * 100;
		return money * 100;
	}

	public int repartition(int money, int idx) {
		int dona = (INITIAL_MONEY - funds) / 100;
		funds += 0.5 * money;
		int profit = funds - INITIAL_MONEY;
		pfSum[dona] += profit;
		num[dona] += 1;
		this.profit[idx] = profit / 10;
		return dona;
	}

	public static void mkGene(int newGene) {
		for (int i = 0; i < PGG.MAX_PERSON; i++) {
			int nw = newGene % 10;
			newGene /= 10;
			int idx = rand.nextInt(8);
			int old = (gene / pow(idx)) % 10;
			gene += (nw - old) * pow(idx);
			gain[7 - idx] = profit[3 - i];
		}
	}

	public static int getGene() {
		return gene;
	}
}
