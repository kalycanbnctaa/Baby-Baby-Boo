# 704. Binary Search

**Topik:** Array, Binary Search
**Difficulty:** Easy
**Link Soal:** https://leetcode.com/problems/binary-search/
**Algoritma Kuliah Terkait:** Divide and Conquer — Binary Search (Searching)

---

## Deskripsi Singkat

Diberikan array `nums` yang sudah terurut secara ascending, dan sebuah
integer `target`. Cari `target` di dalam `nums`. Jika ditemukan,
kembalikan indexnya. Jika tidak, kembalikan `-1`. Algoritma yang dipakai
harus memiliki kompleksitas waktu `O(log n)`.

**Contoh:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 ada di nums pada index 4
```

---

## Status Pengerjaan

**Accepted**: seluruh test case lolos di LeetCode

| Metrik  | Hasil | Syarat | 
|---------|-------|--------|
| Runtime beats | **100.00%** | ≥ 30% |
| Memory beats  | **92.25%** | ≥ 30% | 

> Screenshot bukti Accepted ada di `screenshots/accepted5.jpg`.

---

## Video Penjelasan

- **Video 1 Penjelasan Teknis:** https://drive.google.com/file/d/150PHIgK22oM0d9kmf-3pRoVeHIf2IWTb/view?usp=sharing
- **Video 2 Penjelasan Nonteknis:** https://drive.google.com/file/d/1onDSGtT5L1TpY67UauRrV2-abUpEz7BS/view?usp=sharing

---

## Pendekatan yang Digunakan

Solusi ini menggunakan **Binary Search iteratif**, salah satu penerapan
klasik dari strategi **Divide and Conquer**.

### Ide Utama

1. Jaga dua pointer, `left` dan `right`, yang menandai batas awal dan
   akhir area pencarian di dalam array (awalnya seluruh array).
2. Selama `left <= right`, hitung posisi tengah, `mid`.
3. Bandingkan `nums[mid]` dengan `target`:
   - Jika sama, langsung kembalikan `mid` sebagai jawaban.
   - Jika `nums[mid]` lebih kecil dari `target`, berarti `target` (jika
     ada) pasti berada di **separuh kanan**, geser `left` menjadi
     `mid + 1`.
   - Jika `nums[mid]` lebih besar dari `target`, berarti `target`
     (jika ada) pasti berada di **separuh kiri**, geser `right` menjadi
     `mid - 1`.
4. Jika `left` sudah melebihi `right`, berarti seluruh area pencarian
   sudah habis tanpa ditemukan → kembalikan `-1`.

### Detail Implementasi Penting

Perhitungan posisi tengah memakai:

```java
int mid = left + (right - left) / 2;
```

alih-alih penulisan yang lebih umum:

```java
int mid = (left + right) / 2;
```

Ini adalah **best practice standar** untuk menghindari **integer
overflow**. Jika `left` dan `right` sama-sama bernilai sangat besar
(mendekati batas maksimum `int`), maka `left + right` berisiko melebihi
kapasitas tipe `int` sebelum sempat dibagi dua. Dengan menulis
`left + (right - left) / 2`, nilai yang dijumlahkan jauh lebih kecil sehingga aman dari overflow. Pada soal ini, dengan constraint
`nums.length <= 10^4`, overflow secara praktis tidak akan terjadi, tetapi
penulisan ini tetap dipertahankan sebagai kebiasaan penulisan kode binary
search yang aman dan idiomatis.

### Kode Solusi

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }
}
```

---

## Analisis Kompleksitas

| Aspek | Kompleksitas | Penjelasan |
|-------|--------------|------------|
| **Time** | `O(log n)` | Setiap iterasi membuang **setengah** dari sisa area pencarian sehingga jumlah iterasi maksimum adalah `log2(n)`. |
| **Space** | `O(1)` | Hanya menggunakan beberapa variabel (`left`, `right`, `mid`), tidak ada struktur data tambahan maupun rekursi yang menambah call stack. |

**Catatan:** Karena solusi ditulis secara **iteratif** (bukan rekursif),
tidak ada overhead call stack sama sekali sehingga space complexity murni
`O(1)`, berbeda dengan versi rekursif yang secara teknis memiliki space
complexity `O(log n)` akibat kedalaman call stack rekursi.

---

## Analogi untuk Penjelasan Nonteknis

Bayangkan permainan **tebak angka rahasia** dari 1 sampai 100. Daripada
menebak satu-satu dari angka 1, cara paling cepat adalah selalu **menebak
angka di tengah-tengah** dari kemungkinan yang tersisa. Jika ditebak
"lebih besar", buang semua kemungkinan yang lebih kecil dari tebakan itu.
Jika "lebih kecil", buang semua kemungkinan yang lebih besar. Dengan
strategi ini, angka rahasia bisa ditemukan hanya dalam beberapa kali
tebakan saja, jauh lebih cepat dibanding menebak satu per satu secara
berurutan.