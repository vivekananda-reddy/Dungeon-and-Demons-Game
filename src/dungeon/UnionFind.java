package dungeon;

/**
 * Weighted union find data structure used in krushals to find if edges are connected.
 * This class is accessed only by DungeonImpl hence made it as package private.
 */
class UnionFind {

  private final int[] parent;
  private final int[] rank;
  private int count;

  UnionFind(int n) {
    if (n < 0) {
      throw new IllegalArgumentException();
    }
    count = n;
    parent = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
      rank[i] = 0;
    }
  }

  int find(int p) {
    if (p < 0 || p >= parent.length) {
      throw new IllegalArgumentException("index " + p + " is not between 0 and "
              + (parent.length - 1));
    }
    while (p != parent[p]) {
      parent[p] = parent[parent[p]];
      p = parent[p];
    }
    return p;
  }


  boolean connected(int a, int b) {
    return find(a) == find(b);
  }

  void union(int a, int b) {
    int rootA = find(a);
    int rootB = find(b);
    if (rootA == rootB) {
      return;
    }

    if (rank[rootA] < rank[rootB]) {
      parent[rootA] = rootB;
    }
    else if (rank[rootA] > rank[rootB]) {
      parent[rootB] = rootA;
    }
    else {
      parent[rootB] = rootA;
      rank[rootA]++;
    }
    count--;
  }


}