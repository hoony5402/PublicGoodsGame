import java.util.Random;

public class Person {
	static final int GENE_LEN = 100; // more than 2
	static final int INITIAL_MONEY = 1000;
	static final int MUTANT_GAP = 100;
	static final int FIRST = 50;
	static final double MUTANT = 5;
	static final int SELECTION_PRESSURE = 4;
	Random rand = new Random();

	private int[] gene = new int[GENE_LEN];
	private int[] gain = new int[GENE_LEN];
	private int[] fitness = new int[GENE_LEN];
	private int funds;

	public void setInitiolFunds() {
		this.funds = INITIAL_MONEY;
	}

	public int getFunds() {
		return this.funds;
	}

	public void setGene(int n) {
		for (int i = 0; i < GENE_LEN; i++)
			this.gene[i] = n;
	}

	public void setGain(int n) {
		for (int i = 0; i < GENE_LEN; i++)
			this.gain[i] = n;
	}

	public int mutant(int money) {
		int randint = rand.nextInt(2 * MUTANT_GAP + 1);
		int reMon = money;
		reMon += (randint - MUTANT_GAP);
		if (reMon > 1000)
			reMon = 1000;
		else if (reMon < 0)
			reMon = 0;
		return reMon;
	}

	public int rouletteWheel() {
		int money = -1;
		int bestGain = this.gain[0];
		int worstGain = this.gain[0];
		double totalFitness = 0;
		for (int i = 1; i < GENE_LEN; i++) {
			if (bestGain < this.gain[i])
				bestGain = this.gain[i];
			if (worstGain > this.gain[i])
				worstGain = this.gain[i];
		}
		int C = (bestGain - worstGain) / (SELECTION_PRESSURE - 1);
		for (int i = 0; i < GENE_LEN; i++)
			this.fitness[i] = (this.gain[i] - worstGain) + C;

		for (int i = 0; i < GENE_LEN; i++)
			totalFitness += this.fitness[i];
		double Rate = (100.0 - MUTANT) * 100 / totalFitness;
		int randint = rand.nextInt(10000);
		if(totalFitness == 0 && randint >= MUTANT)
			money = this.gene[0];
		double bound = 0;
		for (int i = 0; i < GENE_LEN; i++) {
			bound += this.fitness[i] * Rate;
			if (bound > randint) {
				money = this.gene[i];
				break;
			}
		}
		if (money == -1)
			money = mutant(this.gene[0]);
		this.funds -= money;
		return money;
	}

	public int ranking() {
		int money = -1;
		int firstGain = -10000;
		int secondGain = -10000;
		int firstGene = 0, secondGene = 0;
		for (int i = 0; i < GENE_LEN; i++) {
			if (firstGain < this.gain[i]) {
				secondGain = firstGain;
				firstGain = this.gain[i];
				secondGene = firstGene;
				firstGene = i;
			} else if (secondGain < this.gain[i]) {
				secondGain = this.gain[i];
				secondGene = i;
			}
		}
		int randint = rand.nextInt(100);
		if (randint < FIRST)
			money = this.gene[firstGene];
		else if (randint > 99 - MUTANT)
			money = mutant(this.gene[firstGene]);
		else
			money = this.gene[secondGene];
		this.funds -= money;
		return money;
	}

	public int donation() {
		return rouletteWheel();
		//return ranking();
	}

	public void repartition(int money) {
		int dona = INITIAL_MONEY - this.funds;
		this.funds += 0.5 * money;
		int profit = this.funds - INITIAL_MONEY;

		for (int x = GENE_LEN - 1; x > 0; x--) {
			this.gene[x] = this.gene[x - 1];
			this.gain[x] = this.gain[x - 1];
		}
		this.gene[0] = dona;
		this.gain[0] = profit;
		System.out.print(" ");
	}

	public String getGene() {
		String data = "";
		for (int i = 0; i < GENE_LEN; i++)
			data += this.gene[i] + " ";
		return data;
	}
}
