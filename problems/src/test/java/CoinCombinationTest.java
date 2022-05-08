import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoinCombinationTest {
  @Test
  void coinCombinationCountEmpty() {
    int[] coins = {};
    int[][] coinMemoization = new int[1][1];
    assertEquals(0, CoinCombination.coinCombinationCount(88, coins, 0, coinMemoization));
  }

  @Test
  void coinCombinationCountSingleTypeOfCoinNotPossible() {
    int[] coins = {42};
    int[][] coinMemoization = new int[90][1];
    assertEquals(0, CoinCombination.coinCombinationCount(89, coins, 0, coinMemoization));
  }

  @Test
  void coinCombinationCountSingleTypeOfCoinExist() {
    int[] coins = {42};
    int[][] coinMemoization = new int[85][1];
    assertEquals(1, CoinCombination.coinCombinationCount(84, coins, 0, coinMemoization));
  }

  @Test
  void coinCombinationCountTwoTypesOfCoins() {
    int[] coins = {5, 1};
    int[][] coinMemoization = new int[11][2];
    assertEquals(3, CoinCombination.coinCombinationCount(10, coins, 0, coinMemoization));
  }

  @Test
  void coinCombinationCountBigger() {
    int[] coins = {200, 100, 50, 20, 10};
    int[][] coinMemoization = new int[10001][5];
    assertEquals(22457476, CoinCombination.coinCombinationCount(10000, coins, 0, coinMemoization));
  }
}