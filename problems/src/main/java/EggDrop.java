import java.util.List;
import java.util.Map;

/**
 * The EggDrop class implements static methods to solve the egg drop problem.
 * Given a number of floors and eggs, the goal is to find the minimum number
 * of egg drops that are required to determine the first floor at which the eggs
 * break.
 */
public class EggDrop {
  /**
   * This method is an example of dynamic programming: the problem is solved
   * by solving smaller instances of the problem until we reach a bottom case.
   *
   * The memoization, i.e. using a map to store the solutions for the smaller
   * problems, is essential as it prevents the method from reaching exponential
   * complexity.
   *
   * The idea behind the solution is the min-max approach. For each floor, there
   * is two outcomes: either the egg breaks or it does not. If it breaks, this means
   * that the breaking floor is below; otherwise, the breaking floor has not been reached yet.
   * The min-max approach takes the max (worst case) for each floor, and selects
   * the minimum (best case) out of all the maxima. This guarantees that
   * whatever the true breaking floor is, we will not find it in more than the min value,
   * because we can always start our search at the floor which corresponds to this min.
   * @param nbFloors number of floors to evaluate.
   * @param nbEggs number of eggs to drop. If the egg breaks, we cannot get it back.
   * @param eggMemoization a {@code Map} that stores solutions for subproblems
   *                       of the eggDrop
   * @return the minimum number of drops to guarantee finding the breaking floor.
   */
  public static int eggDrop(int nbFloors, int nbEggs, Map<List<Integer>, Integer> eggMemoization) {
    if (nbFloors == 0 || nbFloors == 1 || nbEggs == 1)
      return nbFloors;
    List<Integer> floorEggPair = List.of(nbFloors, nbEggs);
    if (eggMemoization.containsKey(floorEggPair)) {
      return eggMemoization.get(floorEggPair);
    }
    int minNbFloors = nbFloors + 1;
    for (int floor = 1; floor < nbFloors; floor++) {
      int maxDrops = Math.max(eggDrop(floor - 1, nbEggs - 1, eggMemoization),
              eggDrop(nbFloors - floor, nbEggs, eggMemoization));
      if (maxDrops < minNbFloors)
        minNbFloors = maxDrops;
    }
    eggMemoization.put(floorEggPair, minNbFloors + 1);
    return minNbFloors + 1;
  }
}
