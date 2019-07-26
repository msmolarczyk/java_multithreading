package _12.future.task.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Factorizer {
	private final Computable<BigInteger, List<BigInteger>> c = new Computable<BigInteger, List<BigInteger>>() {
		
		private List<BigInteger> primeFactors(BigInteger arg) {
		    int n = arg.intValue();
		    List<BigInteger> factors = new ArrayList<BigInteger>();
		    for (long i = 2; i <= n / i; i++) {
		      while (n % i == 0) {
		        factors.add(BigInteger.valueOf(i));
		        n /= i;
		      }
		    }
		    if (n > 1) {
		      factors.add(BigInteger.valueOf(n));
		    }
		    return factors;
		  }
		
		public List<BigInteger> compute(BigInteger arg) {
			return primeFactors(arg);
		}
	};
	
	private final Computable<BigInteger, List<BigInteger>> cache = new Memorizer<BigInteger, List<BigInteger>>(c);

	public List<BigInteger> compute(BigInteger arg) throws InterruptedException{
		return cache.compute(arg);
	}
	
	public static void main(String[] args) {
		try {
			Factorizer factorizer = new Factorizer();
			List<BigInteger> primes = factorizer.compute(BigInteger.valueOf(223092870));
			
			System.out.println(primes);
		} catch (InterruptedException e) {}
	}
}
