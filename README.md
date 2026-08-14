# Tugas Algoritma: Baby Baby Boo

Repository ini berisi penyelesaian soal-soal LeetCode beserta video
penjelasan teknis dan nonteknis sesuai spesifikasi tugas.

### Author

| NIM | Nama |
|---|---|
| 13524071 | Kalyca Nathania Benedicta Manullang |

---

## Daftar Soal

| # | Soal | Topik | Difficulty | Status | Video Teknis dan Nonteknis | 
|---|------|-------|------------|--------|----------------|
| 1 | [Number of Islands](https://leetcode.com/problems/number-of-islands/) | Graph: DFS | Medium | Accepted | https://drive.google.com/drive/folders/1B3G9iPhd3gg69tyjBOpR01Ns34VEHWFq?usp=sharing | 
| 2 | [Coin Change](https://leetcode.com/problems/coin-change/) | Dynamic Programming | Medium | Accepted | https://drive.google.com/drive/folders/10b_yVg28cYcNYh2q4-3lfWyOWHvagSKx?usp=sharing |
| 3 | [Merge Intervals](https://leetcode.com/problems/merge-intervals/) | Sorting / Greedy | Medium | Accepted | https://drive.google.com/drive/folders/1CftqbhbBB3xBEJfocYN9zb3oPnXhK62c?usp=sharing | 
| 4 | [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) | Tree: DFS | Medium | Accepted | https://drive.google.com/drive/folders/1nIdkI5SYy-tzgCxqX6DkqqIr7xK53R6G?usp=sharing | 
| 5 | [Binary Search](https://leetcode.com/problems/binary-search/) | Divide and Conquer | Easy | Accepted | https://drive.google.com/drive/folders/1QeKA8A98MyeY6d372sAWpi3bH8J4HeH3?usp=sharing | 

**Keterangan:**
- Soal #1 dan #2 merupakan **soal wajib** (soal #1 memenuhi syarat topik
  Graph).
- Soal #3, #4, dan #5 merupakan **soal bonus** (total 5 soal, sesuai batas
  maksimal pada spesifikasi bonus).

---

## Struktur Repository

```
.
├── README.md                          (file ini)
├── 01-number-of-islands/
│   ├── Solution.java
│   ├── README.md
│   └── screenshots/
│       └── accepted1.png
├── 02-coin-change/
│   ├── Solution.java
│   ├── README.md
│   └── screenshots/
│       └── accepted2.png
├── 03-merge-intervals/
│   ├── Solution.java
│   ├── README.md
│   └── screenshots/
│       └── accepted3.png
├── 04-validate-bst/
│   ├── Solution.java
│   ├── README.md
│   └── screenshots/
│       └── accepted4.png
└── 05-binary-search/
    ├── Solution.java
    ├── README.md
    └── screenshots/
        └── accepted5.png
```

Setiap folder soal berisi:
- **`Solution.java`**: kode solusi final yang sudah Accepted di LeetCode.
- **`README.md`**: deskripsi soal, pendekatan, kode, analisis
  kompleksitas, dan status pengerjaan (beats runtime dan memory).
- **`screenshots/accepted.png`**: bukti submission Accepted dari
  LeetCode, termasuk persentase beats runtime dan memory.

---

## Ringkasan Pendekatan per Soal

| # | Soal | Pendekatan Utama | Kompleksitas Waktu | Kompleksitas Ruang |
|---|------|-------------------|----------------------|-----------------------|
| 1 | Number of Islands | DFS iteratif (stack primitif) + flood fill in-place | O(m × n) | O(m × n) |
| 2 | Coin Change | Dynamic Programming bottom-up (tabulation) | O(amount × n) | O(amount) |
| 3 | Merge Intervals | Sorting + Greedy (sort-and-sweep) | O(n log n) | O(n) |
| 4 | Validate BST | DFS rekursif dengan range/boundary passing | O(n) | O(h) |
| 5 | Binary Search | Binary search iteratif | O(log n) | O(1) |

---

## Catatan Pengerjaan

- Seluruh solusi ditulis dalam bahasa **Java**.
- Seluruh solusi telah diverifikasi **Accepted** di LeetCode dengan
  **runtime beats ≥ 30%** dan **memory beats ≥ 30%** dibandingkan seluruh
  pengguna LeetCode (lihat detail di README masing-masing folder soal
  dan screenshot terlampir).
- Video dibuat sendiri (voice over + slide),
  **tidak menggunakan NotebookLM atau video generator AI** lainnya, sesuai ketentuan spesifikasi tugas.