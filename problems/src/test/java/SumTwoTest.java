import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SumTwoTest {

  @Test
  void sum2bruteForceTrue() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertTrue(SumTwo.sum2bruteForce(arr, 81));
  }

  @Test
  void sum2bruteForceFalse() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertFalse(SumTwo.sum2bruteForce(arr, 82));
  }

  @Test
  void sum2bruteForceEmpty() {
    int[] arr = {};
    assertFalse(SumTwo.sum2bruteForce(arr, 81));
  }

  @Test
  void sum2bruteForceSingleElement() {
    int[] arr = {72};
    assertFalse(SumTwo.sum2bruteForce(arr, 72));
  }

  @Test
  void sum2withSortingTrue() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertTrue(SumTwo.sum2withSorting(arr, 81));
  }

  @Test
  void sum2withSortingFalse() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertFalse(SumTwo.sum2withSorting(arr, 82));
  }

  @Test
  void sum2withSortingEmpty() {
    int[] arr = {};
    assertFalse(SumTwo.sum2withSorting(arr, 81));
  }

  @Test
  void sum2withSortingSingleElement() {
    int[] arr = {72};
    assertFalse(SumTwo.sum2withSorting(arr, 72));
  }

  @Test
  void sum2WithHashTrue() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertTrue(SumTwo.sum2WithHash(arr, 81));
  }

  @Test
  void sum2withHashFalse() {
    int[] arr = {72, 62, 19, 51, 91, 15, 40, 50};
    assertFalse(SumTwo.sum2WithHash(arr, 82));
  }

  @Test
  void sum2withHashEmpty() {
    int[] arr = {};
    assertFalse(SumTwo.sum2WithHash(arr, 81));
  }

  @Test
  void sum2withHashSameElements() {
    int[] arr = {21, 21};
    assertTrue(SumTwo.sum2WithHash(arr, 42));
  }

  @Test
  void sum2withHashSingleElementDouble(){
    int[] arr = {21};
    assertFalse(SumTwo.sum2WithHash(arr, 42));
  }

  @Test
  void sum2withHashSingleElement() {
    int[] arr = {72};
    assertFalse(SumTwo.sum2WithHash(arr, 72));
  }
}
