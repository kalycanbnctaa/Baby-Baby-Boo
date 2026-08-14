# 98. Validate Binary Search Tree

**Topik:** Tree, Depth-First Search, Binary Search Tree, Binary Tree

**Difficulty:** Medium

**Link Soal:** https://leetcode.com/problems/validate-binary-search-tree/

**Algoritma Kuliah Terkait:** Tree Traversal (DFS rekursif) dengan teknik *range/boundary passing*

---

## Deskripsi Singkat

Diberikan `root` dari sebuah binary tree, tentukan apakah tree tersebut
merupakan **Binary Search Tree (BST) yang valid**. Sebuah BST valid harus
memenuhi:

- Subtree kiri dari sebuah node hanya berisi node dengan nilai **lebih
  kecil** dari nilai node itu sendiri.
- Subtree kanan dari sebuah node hanya berisi node dengan nilai **lebih
  besar** dari nilai node itu sendiri.
- Aturan ini berlaku **secara rekursif** untuk seluruh subtree, bukan hanya
  dibandingkan dengan induk langsungnya.

**Contoh:**
```
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: Nilai root adalah 5, tapi anak kanannya (4) lebih kecil dari 5.
```

---

## Status Pengerjaan

**Accepted**: seluruh test case lolos di LeetCode

| Metrik  | Hasil | Syarat | 
|---------|-------|--------|
| Runtime beats | **100.00%** | ≥ 30% |
| Memory beats  | **90.87%** | ≥ 30% | 

> Screenshot bukti Accepted ada di `screenshots/accepted4.jpg`.

---

## Video Penjelasan

- **Video 1 Penjelasan Teknis:** https://drive.google.com/file/d/1OmHiDMNhGqmWPdtmwTBSYwWEVmJmcSCH/view?usp=sharing
- **Video 2 Penjelasan Nonteknis:** https://drive.google.com/file/d/1T0M1SAqh6r0E2YFZzTHylwV42A305_SN/view?usp=sharing

---

## Pendekatan yang Digunakan

Solusi ini menggunakan **DFS rekursif dengan teknik *range/boundary
passing***, setiap node divalidasi terhadap sebuah **rentang nilai yang
diperbolehkan** (`lower`, `upper`), bukan hanya dibandingkan dengan nilai
induknya secara langsung.

### Mengapa Tidak Cukup Membandingkan dengan Induk Langsung

Kesalahan umum dalam menyelesaikan soal ini adalah hanya memeriksa apakah
anak kiri lebih kecil dari induknya dan anak kanan lebih besar dari
induknya, tanpa mempertimbangkan **leluhur yang lebih jauh**. Contoh kasus
yang gagal dengan pendekatan naif ini:

```
      5
     / \
    1   4
       / \
      3   6
```

Di sini, node `3` memang lebih kecil dari induk langsungnya (`4`), tapi
node `3` berada di subtree **kanan** dari root (`5`). Artinya, seharusnya
`3` harus lebih besar dari `5`. Karena itu, tree ini **tidak valid** meskipun perbandingan lokal terhadap induk langsung tampak benar.

### Ide Utama

1. Setiap node divalidasi terhadap dua batas: `lower` (batas bawah, tidak
   termasuk) dan `upper` (batas atas, tidak termasuk).
2. Node root divalidasi dengan rentang `(-∞, +∞)`, tidak ada batasan di
   awal.
3. Saat turun ke subtree **kiri**, batas atas (`upper`) diperbarui menjadi
   nilai node saat ini (karena semua node di subtree kiri harus lebih kecil
   dari node ini).
4. Saat turun ke subtree **kanan**, batas bawah (`lower`) diperbarui
   menjadi nilai node saat ini (karena semua node di subtree kanan harus
   lebih besar dari node ini).
5. Jika di titik mana pun nilai node berada di luar rentang yang
   diperbolehkan (`node.val <= lower || node.val >= upper`), tree
   dinyatakan **tidak valid**.
6. Node `null` (base case rekursi) dianggap valid karena tidak melanggar
   aturan apa pun.

### Mengapa Memakai `long` Alih-alih `int`

Constraint soal menyatakan nilai node bisa mencapai `Integer.MIN_VALUE`
(`-2^31`) atau `Integer.MAX_VALUE` (`2^31 - 1`). Jika batas awal (`lower`,
`upper`) memakai tipe `int` dan diberi nilai `Integer.MIN_VALUE` /
`Integer.MAX_VALUE`, node dengan nilai persis di batas ekstrem
tersebut berisiko menyebabkan **overflow** saat dibandingkan. Menggunakan
`long` untuk parameter `lower` dan `upper` menghindari masalah ini secara
aman karena rentang `long` jauh lebih besar dari rentang `int`.

### Kode Solusi

```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }

        if (node.val <= lower || node.val >= upper) {
            return false;
        }

        return validate(node.left, lower, node.val)
            && validate(node.right, node.val, upper);
    }
}
```

---

## Analisis Kompleksitas

| Aspek | Kompleksitas | Penjelasan |
|-------|--------------|------------|
| **Time** | `O(n)` | Setiap node pada tree dikunjungi tepat satu kali. |
| **Space** | `O(h)` | Dengan `h` adalah tinggi tree karena penggunaan memori didominasi oleh kedalaman call stack rekursi. Pada kasus terburuk (tree tidak seimbang, menyerupai linked list), `h = O(n)`. Pada tree seimbang, `h = O(log n)`. |

**Catatan:** Pendekatan alternatif yang juga umum digunakan adalah
**in-order traversal** karena in-order traversal pada BST yang valid
akan selalu menghasilkan urutan nilai yang **strictly increasing**. Dengan
memeriksa apakah hasil traversal tersebut benar-benar naik secara ketat
(tanpa perlu menyimpan seluruh hasil traversal ke dalam list, cukup
bandingkan dengan nilai node sebelumnya), validasi bisa dilakukan dengan
kompleksitas ruang tambahan yang sama, yaitu `O(h)`.

---

## Analogi untuk Penjelasan Nonteknis

Bayangkan sebuah **pohon keluarga berdasarkan umur**, di mana aturannya:
di setiap cabang, semua anggota keluarga di sisi **kiri** harus lebih
**muda** dan semua yang di sisi **kanan** harus lebih **tua**, bukan
cuma dibandingkan dengan orang tua langsungnya, tapi dengan **seluruh
leluhur** di atasnya sepanjang jalur itu.

Jadi, ketika kita mengecek satu per satu anggota keluarga dari atas ke
bawah, kita bawa serta "batas umur" yang berlaku untuk cabang itu, batas
ini bisa makin sempit setiap kali turun ke cabang berikutnya. Kalau ada
satu anggota keluarga yang umurnya keluar dari batas yang berlaku untuknya
(meskipun dia lebih muda dari orang tuanya sendiri, tapi ternyata lebih
tua dari salah satu kakek/neneknya yang seharusnya jadi batas), pohon keluarga itu dinyatakan **tidak valid**.