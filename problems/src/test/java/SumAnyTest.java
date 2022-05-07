import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SumAnyTest {
  @Test
  void sumAnyEmpty() {
    int[] arr = {};
    assertFalse(SumAny.sumAny(arr, 42));
  }

  @Test
  void sumAnySingletonArrayTrue() {
    int[] arr = {42};
    assertTrue(SumAny.sumAny(arr, 42));
  }

  @Test
  void sumAnySingletonArrayFalse() {
    int[] arr = {42};
    assertFalse(SumAny.sumAny(arr, 43));
  }

  @Test
  void sumAnySingleElement() {
    int[] arr = {42, 46, 4, 77, 62, 14, 92, 89, 48, 31};
    assertTrue(SumAny.sumAny(arr, 42));
  }

  @Test
  void sumAnyTwoElements() {
    int[] arr = {42, 46, 4, 77, 62, 14, 92, 89, 48, 31};
    assertTrue(SumAny.sumAny(arr, 50));
  }

  @Test
  void sumAnyThreeElements() {
    int[] arr = {42, 46, 4, 77, 62, 14, 92, 89, 48, 31};
    assertTrue(SumAny.sumAny(arr, 110));
  }

  @Test
  void sumAnyAllElements() {
    int[] arr = {42, 46, 4, 77, 62, 14, 92, 89, 48, 31};
    assertTrue(SumAny.sumAny(arr, 505));
  }

  @Test
  void sumAnySingleElementDouble() {
    int[] arr = {21};
    assertFalse(SumAny.sumAny(arr, 42));
  }
}
