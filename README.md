# ETS Kecerdasan Buatan F
Goal Maze with Coins Using Breadth-First Search and Genetic Algorithm

[Fiqey Indriati Eka Sari](https://github.com/finesaaa) (05111940000015)
[Muchamad Maroqi Abdul Jalil](https://github.com/maroqijalil) (05111940000143)

Kelompok 4 KB F - Dini Adni Navastara, S.Kom, M.Sc.

---

## Deskripsi
- Shopie ingin mengumpulkan semua koin dalam labirin (maze) kemudian menuju bendera. 
- Terdapat k koin yang tersebar di seluruh labirin. 
- Shopie mungkin bergerak ke kiri, kanan, atas, atau bawah dalam labirin. Dia harus mengumpulkan semua koin sebelum mencapai bendera.
- Labirin juga mungkin memiliki dinding yang tidak dapat dilalui Shopie. 0 mewakili kotak yang dapat dilalui di labirin, 1 mewakili dinding, dan 3 mewakili koin. 
- Agar tidak kehabisan energi, Sophie harus menemukan jarak shortest path. Jika Sophie tidak dapat mencapai bendera atau tidak dapat mengumpulkan semua koin, kembalikan -1 (labirin tidak dapat dilewati). 
- Labirin adalah matriks n kali n int [] []. Ada 0 <= k <= 10 koin. Lokasi Sophie dan bendera diubah fungsinya sebagai objek titik.

## Penjelasan Algoritma
- PATH : Membuat sebuah matriks yang merepresentasikan jalur yang dapat dilalui pada pola yang diberikan dalam game.
- TWO POINTS SHORTESTH PATH : Mencari jalur terpendek antar koin, koin dengan Shopie, dan koin dengan bendera.
- MINIMUN STEPS : Mencari step paling minimal yang dibutuhkan untuk memenuhi jalur terpendek dari Shopie ke koin-koin, dan terakhir ke bendera. 

## Algoritma yang Digunakan
- BFS untuk mencari **Two Pooints Shortest Path**
- Genetic Algorithm untuk mencari **Minimum Steps**

![image](https://user-images.githubusercontent.com/57583780/121291299-3b837f80-c912-11eb-8894-93f5a9697298.png)
![image](https://user-images.githubusercontent.com/57583780/121291278-30c8ea80-c912-11eb-96a8-1d49b896ee9a.png)
![image](https://user-images.githubusercontent.com/57583780/121291310-44745100-c912-11eb-8dde-5824400b5ba0.png)

