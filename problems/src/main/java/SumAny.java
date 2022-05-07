import java.util.HashSet;
import java.util.Set;

/**
 * The SumTwo class implements static methods that determine
 * whether a given array contains a subset of elements that sum up
 * to a given integer.
 */
public class SumAny {
  /**
   * This method uses a {@code Set} to store the encountered values in {@code arr}.
   * Since the number of elements to sum is arbitrary, we also add to the {@code Set}
   * all the sums involving each element of the array.
   * <p>
   * The time complexity of the method is {@code O(n^2)}: we iterate through {@code arr},
   * while an inner loop iterates through the populating {@code Set}.
   *
   * @param arr {@code int} array that contains the values to sum
   * @param sum the desired sum of elements
   * @return {@code true} if any subset of elements in {@code arr} add up to {@code sum};
   * {@code false} otherwise.
   */
  public static boolean sumAny(int[] arr, int sum) {
    Set<Integer> arrayElements = new HashSet<>();
    for (int element : arr) {
      if (element == sum || arrayElements.contains(sum - element)) {
        return true;
      }
      Set<Integer> sumSet = new HashSet<>();
      sumSet.add(element);
      for (int setElem : arrayElements) {
        sumSet.add(element + setElem);
      }
      for (int elementToAdd : sumSet) {
        arrayElements.add(elementToAdd);
      }
    }
    return false;
  }
}
