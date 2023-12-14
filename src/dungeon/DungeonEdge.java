package dungeon;

/**
 * A class to maintain edges of the dungeon for Krushkals. This class is used only by DungeonImpl
 * hence it is made package default.
 * @param <T> generic parameter
 */
class DungeonEdge<T> {
  T v;
  T w;

  DungeonEdge(T v, T w) {
    this.v = v;
    this.w = w;
  }

  T either() {
    return v;
  }

  T other() {
    return w;
  }
}
