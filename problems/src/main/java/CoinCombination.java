/**
 * The CoinCombination class implements static methods to solve the problem
 * of coin combination counting.
 * Given different types of coins, e.g. 2€, 1€, 0.50, etc., how many
 * combinations are possible to reach a value sum?
 */
public class CoinCombination {
  /**
   * Compute the number of ways the given types of coins can be combined to
   * reach the given amount, e.g. how many ways are there to reach 10€ given
   * 5€ (hypothetical ones) and 1€ coins?
   * <p>
   * The solution follows the dynamic programming paradigm: the problem is reduced
   * by considering one type of coin at a time until there are no more coins to use.
   *
   * @param amount          the value that we want to reach with the different coins
   * @param coins           an array containing the available types of coins
   * @param coinIndex       the the index of the first type of coin that we can use
   * @param coinMemoization a 2D array containing solutions for subproblems.
   * @return the total number of ways the coins can be combined to reach the given amount.
   */
  public static int coinCombinationCount(int amount, int[] coins, int coinIndex, int[][] coinMemoization) {
    if (coins.length > 0 && amount == 0)
      return 1;
    if (coinIndex >= coins.length)
      return 0;
    if (coinMemoization[amount][coinIndex] > 0)
      return coinMemoization[amount][coinIndex];
    int total = 0;
    for (int i = 0; i * coins[coinIndex] <= amount; i++) {
      total += coinCombinationCount(amount - i * coins[coinIndex], coins, coinIndex + 1, coinMemoization);
    }
    coinMemoization[amount][coinIndex] = total;
    return total;
  }
}
