import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EggDropTest {

  @Test
  void eggDropOneFloor() {
    Map<List<Integer>, Integer> eggMemoization = new HashMap<>();
    assertEquals(1, EggDrop.eggDrop(1, 2, eggMemoization));
  }

  @Test
  void eggDropZeroFloors() {
    Map<List<Integer>, Integer> eggMemoization = new HashMap<>();
    assertEquals(0, EggDrop.eggDrop(0, 2, eggMemoization));
  }

  @Test
  void eggDropOneEgg() {
    Map<List<Integer>, Integer> eggMemoization = new HashMap<>();
    assertEquals(9, EggDrop.eggDrop(9, 1, eggMemoization));
  }

  @Test
  void eggDropHundredFloorsAndTwoEggs() {
    Map<List<Integer>, Integer> eggMemoization = new HashMap<>();
    assertEquals(14, EggDrop.eggDrop(100, 2, eggMemoization));
  }
}
