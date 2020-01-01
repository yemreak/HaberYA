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

| Metot | Açıklama |
|-|-|
| `insert` | Durum ekleme
| `delete`| Durum silme

> 💡 Durumlar: Beğenme, kaydetme, okunma

## 📰 News

❣️ Sadece haber bilgilerini barındırır.

### 💠 Metotlar

| Metot | Açıklama |
|-|-|
| `deleteRow` | Belirli sayıdaki ilk kaydedilen haber kayıtlarını siler
| `deleteByIDs`| Haberleri ID'sine göre silme
| `insert` | Haberleri ekleme
| `getByIDs` | Haberleri ID'sine göre alma


## 🐣 NewsWithState

- 🔗 News ve State tablolarını `@Relation` yapısı ile bağlamaktadır
- 🚧 Tüm metotları `@Transaction` özelliğine sahiptir
- ⭐ Haber ve haberin durum verilerini almak için tanımlanmıştır

### 💠 Metotlar

| Metot | Açıklama |
| - | - |
| `getALlNewsWithState()` | Tüm haberleri durumları ile alır
| `getNewsWithStateByState(stateType)` | Duruma göre haberleri duurmları ile alır
| `getAllNewsWithStateHasStates()` | Duruma sahip olan haberleri alır

> 💡 Durumlar: Beğenme, kaydetme, okunma

## 📝 DB Notları

### 📉 Feeds yapılmama sebebi

- 🎳 Veri sayısı arttıkça sorguların maaliyeti artacaç
- 📉 Veri tabanı sadeliğini kaybedecek

### ⚙️ Shared Preferences Yapılmama Sebebi

- 🔍 Sorgu işlemlerinin desteklenmemesi
- 🚄 İnt yerine, int list ile uğraşılması (key / value mantığında değil)
