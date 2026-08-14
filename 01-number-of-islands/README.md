# 200. Number of Islands

**Topik:** Graph: Depth-First Search (DFS), Array, Matrix

**Difficulty:** Medium

**Link Soal:** https://leetcode.com/problems/number-of-islands/

**Algoritma Kuliah Terkait:** Graph Traversal (DFS/BFS) pada representasi grid sebagai graf implisit

---

## Deskripsi Singkat

Diberikan grid 2D berukuran `m x n` berisi karakter `'1'` (daratan) dan `'0'`
(air). Sebuah pulau terbentuk dari kumpulan daratan yang saling terhubung
secara horizontal atau vertikal (bukan diagonal). Tugasnya adalah menghitung
jumlah pulau yang ada di dalam grid tersebut.

**Contoh:**
```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

---

## Status Pengerjaan

**Accepted**: seluruh test case lolos di LeetCode

| Metrik  | Hasil | Syarat | 
|---------|-------|--------|
| Runtime beats | **45.90%** | ≥ 30% |
| Memory beats  | **89.35%** | ≥ 30% | 

> Screenshot bukti Accepted ada di `screenshots/accepted1.jpg`.

---

## Video Penjelasan

- **Video 1 Penjelasan Teknis:** https://drive.google.com/file/d/1uqRYjMr-jVThlQr6l2uX-z7h-Oag95WP/view?usp=sharing
- **Video 2 Penjelasan Nonteknis:** https://drive.google.com/file/d/1BiiHEqdJ60oyeE-q4SOUyNLSvb3Y3wJa/view?usp=sharing

---

## Pendekatan yang Digunakan

Solusi ini menggunakan **DFS (Depth-First Search) iteratif** dengan
**flood fill** untuk menandai seluruh daratan yang terhubung sebagai satu
pulau, lalu menghitung berapa kali proses flood fill ini dimulai dari sel
yang belum dikunjungi.

### Ide Utama

1. Iterasi seluruh sel pada grid baris demi baris, kolom demi kolom.
2. Setiap kali ditemukan sel `'1'` yang belum pernah dikunjungi, artinya kita
   menemukan pulau **baru** → tambahkan penghitung `islands`.
3. Dari sel tersebut, lakukan **flood fill** (DFS) ke segala arah (atas,
   bawah, kiri, kanan) untuk menandai seluruh daratan yang terhubung dengan
   sel itu sebagai sudah dikunjungi, dengan mengubah nilainya dari `'1'`
   menjadi `'0'` secara langsung di dalam grid (in-place marking).
4. Ulangi sampai seluruh grid selesai diperiksa. Nilai `islands` di akhir
   adalah jawabannya.

### Detail Implementasi (Optimasi)

Implementasi ini sengaja **tidak** menggunakan rekursi maupun struktur data
bawaan Java seperti `Deque<int[]>` untuk stack. Sebagai gantinya, dipakai
**stack manual berbasis dua array primitif** (`stackRow` dan `stackCol`)
berukuran `m * n`, dengan variabel `top` sebagai penunjuk posisi puncak stack.

Alasan pendekatan ini dipilih:

- **Menghindari overhead call stack** dari rekursi yang bisa membengkakkan
  penggunaan memori terutama saat pulau berukuran besar (mendekati seluruh
  grid terisi daratan).
- **Menghindari autoboxing** yang terjadi jika memakai `Deque<Integer>` atau
  `Deque<int[]>`, setiap `Integer` atau `int[]` yang dibuat berulang kali di
  dalam loop berarti alokasi objek baru di heap yang memperlambat eksekusi
  dan menambah beban *garbage collector*.
- Dengan array primitif yang dialokasikan **sekali di awal** (ukuran
  maksimal `m * n` karena paling banyak seluruh sel grid bisa masuk ke
  dalam satu pulau), tidak ada alokasi memori tambahan selama proses
  flood fill berlangsung.
- Sel ditandai sebagai *dikunjungi* (`'0'`) **tepat saat dimasukkan ke
  stack**, bukan saat diproses untuk mencegah sel yang sama dimasukkan ke
  stack lebih dari satu kali.

### Kode Solusi

```java
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
```

---

## Analisis Kompleksitas

| Aspek | Kompleksitas | Penjelasan |
|-------|--------------|------------|
| **Time** | `O(m × n)` | Setiap sel pada grid dikunjungi dan diproses paling banyak satu kali (baik saat loop utama maupun saat flood fill). |
| **Space** | `O(m × n)` | Dua array `stackRow` dan `stackCol` masing-masing berukuran `m * n`, dialokasikan untuk mengantisipasi kasus terburuk di mana seluruh grid adalah satu pulau besar. Tidak ada rekursi sehingga tidak ada penggunaan call stack tambahan. |

**Catatan:** Solusi alternatif dapat menggunakan **BFS** (dengan queue) atau
**Union-Find (Disjoint Set Union)**, keduanya juga memiliki kompleksitas
waktu `O(m × n)` (untuk Union-Find dengan path compression, kompleksitasnya
mendekati `O(m × n × α(m × n))`, di mana `α` adalah fungsi inverse Ackermann
yang nilainya sangat kecil sehingga dianggap konstan dalam praktik).

---

## Analogi untuk Penjelasan Nonteknis

Bayangkan sebuah **peta pulau-pulau**, di mana kotak berwarna coklat adalah
daratan dan kotak biru adalah air. Untuk menghitung ada berapa pulau:

1. Kita jelajahi peta dari kiri atas ke kanan bawah, kotak demi kotak.
2. Setiap kali menemukan daratan yang **belum pernah kita warnai**, itu
   berarti kita menemukan pulau baru, catat satu pulau.
3. Lalu kita "cat" seluruh daratan yang menempel dengannya (atas, bawah,
   kiri, kanan) dengan warna lain supaya nanti tidak dihitung dua kali.
4. Lanjutkan sampai seluruh peta selesai dijelajahi.

Ini seperti bermain **"warnai pulau"**: begitu satu pulau selesai diwarnai
semuanya, kita lanjut mencari pulau berikutnya yang belum diwarnai.