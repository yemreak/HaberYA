# 🏗️ Veri Tabanı Yapısı

👮‍♂️ Verilerin tekrarlı kaydedilmesini engellemek adına:

- [ ] 🗃️ `News` ve `Status` adlı tablolar oluşturulacak
- [ ] 🔗 Tablolar [@Relation ve POJO yapısı](https://developer.android.com/reference/androidx/room/Relation.html) ile bağlanacak
- [ ] 🐣 Asıl işlem yaptığımız obje `NewsWithState` olacak

## 👨‍💼 API Yönetimi

- [ ] 📂 API üzerinden gelen her haber için *local variable* üzerinde idler saklananacak
- [ ] 🧹 Beğenme, kaydetme gibi işlemlerde id silinecek,
  - [ ] ➕ `State` tablosuna konulacak
  - [ ] ⚠️ Okundu bilgisi için id silin**me**yecek

## 🐥 State

- 🆔 `nid` News ID
- ⭐ `type` State number (liked, saved, read)

### 💠 Metotlar

- insertState(nid, type)`
- `getStates(nid)`

## 📰 News

❣️ Sadece haber bilgilerini barındırır.

### 💠 Metotlar

- `deleteByID(ids)`
- `insertNews(news)`
-  `getNewsByIDs(ids)`

## 🐣 NewsWithState

- `getNewsByState(stype)`

> 💡 `SavedNews` ismi `NewsWithState` olarak ele alınabilir.

## 📝 DB Notları

### 📉 Feeds yapılmama sebebi

- 🎳 Veri sayısı arttıkça sorguların maaliyeti artacaç
- 📉 Veri tabanı sadeliğini kaybedecek
