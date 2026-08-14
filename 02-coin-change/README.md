# 322. Coin Change

**Topik:** Dynamic Programming, Breadth-First Search, Knapsack Problem (Complete Knapsack)

**Difficulty:** Medium

**Link Soal:** https://leetcode.com/problems/coin-change/

**Algoritma Kuliah Terkait:** Dynamic Programming (Bottom-Up Tabulation): unbounded knapsack

---

## Deskripsi Singkat

Diberikan array `coins` berisi denominasi koin yang tersedia (dengan asumsi
jumlah tiap koin tidak terbatas) dan sebuah `amount` yang merupakan target
jumlah uang. Tugasnya adalah mencari **jumlah koin paling sedikit** yang
dibutuhkan untuk mencapai `amount` tersebut. Jika tidak mungkin, kembalikan
`-1`.

**Contoh:**
```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

---

## Status Pengerjaan

**Accepted**: seluruh test case lolos di LeetCode

| Metrik  | Hasil | Syarat | 
|---------|-------|--------|
| Runtime beats | **69.14%** | ≥ 30% |
| Memory beats  | **93.20%** | ≥ 30% | 

> Screenshot bukti Accepted ada di `screenshots/accepted2.jpg`.

---

## Video Penjelasan

- **Video 1 Penjelasan Teknis:** https://drive.google.com/file/d/1T8Qq7L-SYv4VzQF5f_PoWxlHLIrS84Jm/view?usp=sharing
- **Video 2 Penjelasan Nonteknis:** https://drive.google.com/file/d/1xgI3cGaQUr93yOozj5nm9h1xzmw_u6DB/view?usp=sharing

---

## Pendekatan yang Digunakan

Solusi ini menggunakan **Dynamic Programming Bottom-Up (Tabulation)**.
Alih-alih mencoba semua kemungkinan kombinasi koin secara brute-force
(yang kompleksitasnya eksponensial), kita membangun solusi secara bertahap
dari sub-masalah yang lebih kecil ke sub-masalah yang lebih besar, sambil
menyimpan hasil setiap sub-masalah supaya tidak dihitung ulang.

### Ide Utama

1. Definisikan `dp[i]` sebagai **jumlah koin minimum** yang dibutuhkan untuk
   mencapai total `i`.
2. **Basis:** `dp[0] = 0`: untuk mencapai jumlah 0, dibutuhkan 0 koin.
3. Inisialisasi semua `dp[i]` lainnya dengan nilai "tak terhingga" (di sini
   dipakai `amount + 1` karena jumlah koin sebenarnya tidak mungkin
   melebihi `amount` jika memang bisa dibentuk, nilai ini efektif berperan
   sebagai penanda "belum ditemukan solusi").
4. Untuk setiap total `current` dari `1` sampai `amount`, coba semua
   denominasi koin yang tersedia. Jika koin tersebut nilainya lebih kecil
   atau sama dengan `current`, maka:
   ```
   dp[current] = min(dp[current], dp[current - coin] + 1)
   ```
   Artinya: "jika saya pakai koin ini sebagai koin terakhir, berapa total
   koin yang saya butuhkan?", yaitu jumlah koin untuk mencapai
   `current - coin`, ditambah 1 (koin yang baru saja dipakai).
5. Di akhir, `dp[amount]` menyimpan jawaban. Jika nilainya masih lebih besar
   dari `amount` (artinya tidak pernah ter-update dari nilai "tak
   terhingga" awal), berarti `amount` tidak bisa dibentuk → kembalikan `-1`.

### Mengapa Ini Termasuk "Complete/Unbounded Knapsack"

Karena tiap koin dianggap **tersedia tak terbatas** (boleh dipakai berkali-
kali), setiap posisi `dp[current]` boleh menggunakan koin yang sama lebih
dari sekali selama proses membangun solusi, ini yang membedakannya dari
*0/1 Knapsack* di mana tiap item hanya boleh dipakai maksimal sekali.

### Kode Solusi

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        java.util.Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int current = 1; current <= amount; current++) {
            for (int coin : coins) {
                if (coin <= current) {
                    dp[current] = Math.min(dp[current], dp[current - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }
}
```

---

## Analisis Kompleksitas

| Aspek | Kompleksitas | Penjelasan |
|-------|--------------|------------|
| **Time** | `O(amount × n)` | Dengan `n` adalah jumlah denominasi koin. Untuk setiap total dari `1` hingga `amount`, kita coba semua `n` koin sekali. |
| **Space** | `O(amount)` | Hanya butuh satu array `dp` berukuran `amount + 1`, tidak ada struktur data tambahan lain. |

**Catatan:** Pendekatan lain yang juga valid adalah **BFS** (setiap "level"
BFS merepresentasikan penambahan satu koin, dan level pertama yang mencapai
`amount` adalah jawabannya). Pendekatan ini biasa dipakai untuk
menunjukkan hubungan antara *shortest path* pada graf implisit dengan
masalah DP ini, sesuai topik BFS yang juga tercantum di soal LeetCode.

---

## Analogi untuk Penjelasan Nonteknis

Bayangkan kita jaga kasir dan harus **kasih kembalian** ke pembeli dengan
koin sesedikit mungkin. Kita punya beberapa jenis koin, misalnya koin
Rp1.000, Rp2.000, dan Rp5.000, dan stoknya dianggap tidak terbatas.

Daripada menebak-nebak kombinasi mana yang paling hemat, kita **catat dulu
cara paling hemat untuk tiap kemungkinan jumlah kembalian yang lebih kecil**, misalnya, cara paling hemat kasih kembalian Rp1, Rp2, Rp3, dan seterusnya, lalu pakai catatan itu untuk membangun jawaban jumlah yang lebih besar,
sedikit demi sedikit tanpa perlu mengulang perhitungan dari nol setiap
kali.

Ini seperti menyusun "tabel contekan" dari kembalian termurah ke termahal supaya begitu ketemu jumlah yang diminta pembeli, kita tinggal lihat
jawabannya di tabel, bukan hitung ulang dari awal.