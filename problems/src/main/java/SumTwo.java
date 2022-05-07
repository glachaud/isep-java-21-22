import java.util.*;

/**
 * The SumTwo class implements static methods that determine
 * whether a given array contains two elements that sum up
 * to a given integer.
 */
public class SumTwo {
  /**
   * This method uses brute force: it iterates through the array
   * and creates the second loop to perform the summation element by element.
   * Its time complexity is {@code O(n^2)}.
   *
   * @param arr {@code int} array that contains the values to sum
   * @param sum the desired sum of elements
   * @return {@code true} if two elements in {@code arr} add up to {@code sum};
   * {@code false} otherwise.
   */
  public static boolean sum2bruteForce(int[] arr, int sum) {
    for (int i = 0; i < arr.length - 1; i++) {
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[i] + arr[j] == sum) return true;
      }
    }
    return false;
  }

  /**
   * This method first sorts the array, then tries to find the sum
   * by adding the smallest element with the biggest element. If this sum
   * is bigger than the desired sum, we take the next biggest element. Similarly,
   * if the sum is too small, we take the second next smallest element.
   * The time complexity of the method if {@code O(nlogn)}: the sorting takes
   * {@code O(nlogn)} and the iteration through the array {@code O(n)}.
   *
   * @param arr {@code int} array that contains the values to sum
   * @param sum the desired sum of elements
   * @return {@code true} if two elements in {@code arr} add up to {@code sum};
   * * {@code false} otherwise.
   */
  public static boolean sum2withSorting(int[] arr, int sum) {
    Arrays.sort(arr);
    int low_index = 0, high_index = arr.length - 1;
    while (high_index - low_index > 0) {
      int tentativeSum = arr[low_index] + arr[high_index];
      if (tentativeSum < sum) {
        low_index++;
      } else if (tentativeSum > sum) {
        high_index--;
      } else {
        return true;
      }
    }
    return false;
  }

  /**
   * This method uses a {@code Set} Collection to store the encountered values
   * in the array. The problem of finding whether the sum of two elements of the
   * array is equal to the desired sum can then be seen as the problem of whether
   * the Set contains an element and its complement with respect to the sum
   * (i.e. if {@code x} is in a set {@code S}, and {@code sum} is the desired sum,
   * we check if {@code sum - x} is also in {@code S}).
   * <p>
   * The time complexity of the method is {@code O(n)} (for the iteration through
   * the array). Its memory complexity is {@code O(n^2)} (the number of possible
   * combinations of two elements from an array of {@code n} elements).
   *
   * @param arr {@code int} array that contains the values to sum
   * @param sum the desired sum of elements
   * @return {@code true} if two elements in {@code arr} add up to {@code sum};
   * * {@code false} otherwise.
   */
  public static boolean sum2WithHash(int[] arr, int sum) {
    Set<Integer> arrayElements = new HashSet<>();
    for (int element : arr) {
      if (arrayElements.contains(sum - element)) {
        return true;
      }
      arrayElements.add(element);
    }
    return false;
  }
}
