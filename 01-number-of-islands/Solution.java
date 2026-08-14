class Solution {
    public int numIslands(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int islands = 0;

        int[] stackRow = new int[m * n];
        int[] stackCol = new int[m * n];

        for (int r = 0; r < m; r++) {
            char[] current = grid[r];

            for (int c = 0; c < n; c++) {
                if (current[c] != '1') {
                    continue;
                }

                islands++;

                int top = 0;
                stackRow[0] = r;
                stackCol[0] = c;
                top++;

                current[c] = '0';

                while (top > 0) {
                    top--;
                    int row = stackRow[top];
                    int col = stackCol[top];

                    char[] cells = grid[row];

                    if (row > 0) {
                        char[] next = grid[row - 1];
                        if (next[col] == '1') {
                            next[col] = '0';
                            stackRow[top] = row - 1;
                            stackCol[top] = col;
                            top++;
                        }
                    }

                    if (row + 1 < m) {
                        char[] next = grid[row + 1];
                        if (next[col] == '1') {
                            next[col] = '0';
                            stackRow[top] = row + 1;
                            stackCol[top] = col;
                            top++;
                        }
                    }

                    if (col > 0 && cells[col - 1] == '1') {
                        cells[col - 1] = '0';
                        stackRow[top] = row;
                        stackCol[top] = col - 1;
                        top++;
                    }

                    if (col + 1 < n && cells[col + 1] == '1') {
                        cells[col + 1] = '0';
                        stackRow[top] = row;
                        stackCol[top] = col + 1;
                        top++;
                    }
                }
            }
        }

        return islands;
    }
}