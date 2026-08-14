# 56. Merge Intervals

**Topik:** Array, Sorting, Quicksort

**Difficulty:** Medium

**Link Soal:** https://leetcode.com/problems/merge-intervals/

**Algoritma Kuliah Terkait:** Sorting + Greedy Algorithm

---

## Deskripsi Singkat

Diberikan array `intervals`, di mana setiap elemen `intervals[i] = [start_i, end_i]`
merepresentasikan sebuah rentang. Tugasnya adalah **menggabungkan seluruh
rentang yang saling tumpang tindih (overlap)**, lalu mengembalikan array
rentang baru yang sudah tidak saling tumpang tindih dan tetap mencakup
seluruh rentang input.

**Contoh:**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: [1,3] dan [2,6] tumpang tindih, digabung jadi [1,6]
```

---

## Status Pengerjaan

**Accepted**: seluruh test case lolos di LeetCode (172/172 testcases)

| Metrik  | Hasil | Syarat | 
|---------|-------|--------|
| Runtime beats | **91.73%** | ≥ 30% |
| Memory beats  | **92.66%** | ≥ 30% | 

> Screenshot bukti Accepted ada di `screenshots/accepted3.jpg`.

---

## Video Penjelasan

- **Video 1 Penjelasan Teknis:** https://drive.google.com/file/d/1TEBx-Mnk8o6Gtdo8DTNKaOGmcvLirvJ-/view?usp=sharing
- **Video 2 Penjelasan Nonteknis:** https://drive.google.com/file/d/1TTsMFscfIgBluoLk3lmizyf0y7fwAE6i/view?usp=sharing

---

## Pendekatan yang Digunakan

Solusi ini menggunakan strategi **Sorting + Greedy (Sort-and-Sweep)**.

### Ide Utama

1. **Urutkan** seluruh interval berdasarkan nilai `start`-nya secara
   menaik. Langkah ini krusial: setelah diurutkan, dua interval yang saling
   tumpang tindih **dijamin akan bersebelahan** dalam array sehingga kita
   tidak perlu membandingkan setiap interval dengan seluruh interval
   lainnya.
2. Mulai dengan interval pertama (hasil sorting) sebagai interval "aktif"
   yang sedang dibangun di dalam array `result`.
3. Iterasi sisa interval satu per satu:
   - Jika `start` interval saat ini **kurang dari atau sama dengan**
     `end` dari interval terakhir di `result` → berarti tumpang tindih →
     perluas `end` interval terakhir menjadi nilai maksimum antara
     keduanya (**greedy choice**: selalu ambil `end` sejauh mungkin).
   - Jika tidak tumpang tindih → interval saat ini menjadi interval baru
     dan terpisah, dimasukkan sebagai entri baru di `result`.
4. Kembalikan `result` yang sudah dipotong sesuai jumlah interval final
   (memakai `Arrays.copyOfRange`).

### Mengapa Pendekatan Greedy Ini Optimal

Karena interval sudah terurut berdasarkan `start`, begitu kita menemukan
interval yang **tidak** tumpang tindih dengan interval aktif saat ini,
interval aktif tersebut sudah pasti final, tidak ada interval berikutnya
yang bisa memperluasnya lagi (karena semua interval berikutnya punya
`start` yang sama besar atau lebih besar). Sifat inilah yang membuat
pendekatan greedy (mengambil keputusan lokal tanpa perlu backtrack)
menghasilkan solusi optimal secara global.

### Kode Solusi

```java
class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        int[][] result = new int[intervals.length][2];
        int index = 0;
        result[0] = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] lastMerged = result[index];

            if (current[0] <= lastMerged[1]) {
                lastMerged[1] = Math.max(lastMerged[1], current[1]);
            } else {
                index++;
                result[index] = current;
            }
        }

        return Arrays.copyOfRange(result, 0, index + 1);
    }
}
```

---

## Analisis Kompleksitas

| Aspek | Kompleksitas | Penjelasan |
|-------|--------------|------------|
| **Time** | `O(n log n)` | Didominasi oleh proses `Arrays.sort` (menggunakan Dual-Pivot Quicksort untuk tipe primitif di Java). Proses penggabungan setelah sorting hanya membutuhkan satu kali scan linear, yaitu `O(n)`. |
| **Space** | `O(n)` | Array `result` berukuran sama dengan jumlah interval input di kasus terburuk (tidak ada yang tumpang tindih sama sekali). Di luar itu, kompleksitas ruang tambahan bergantung pada implementasi sorting Java (`O(log n)` untuk quicksort in-place pada array primitif). |

---

## Analogi untuk Penjelasan Nonteknis

Bayangkan kita punya daftar **janji temu** dengan beberapa teman, masing-
masing punya jam mulai dan jam selesai. Beberapa janji ternyata jamnya
saling tumpang tindih, misalnya janji pertama jam 1-3, dan janji kedua
jam 2-6, itu berarti sebenarnya kita sedang sibuk terus dari jam 1 sampai
jam 6, bukan dua janji terpisah.

Cara paling gampang untuk merapikan jadwal ini:
1. **Urutkan dulu** semua janji dari yang paling pagi.
2. Lihat satu-satu: kalau janji berikutnya mulai **sebelum** janji
   sebelumnya selesai, gabungkan jadi satu blok waktu sibuk yang lebih
   panjang.
3. Kalau tidak nyambung, itu jadi blok waktu sibuk yang baru dan terpisah.

Hasil akhirnya: daftar blok waktu sibuk yang sudah rapi, tidak ada lagi
yang tumpang tindih.