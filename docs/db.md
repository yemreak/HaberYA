# 🏗️ Veri Tabanı Yapısı

## 🐥 State

- 🆔 `nid` News ID
- ⭐ `type` State number (liked, saved, read)
- 💎 Her haber birden fazla durum alabilir
- 🦄 Ama aynı durumdan sadece 1 kere alabilir
- 👨‍💼 Alttaki koşulların kontrolü için `indices` ve `unique` kullanılmıştır

### 💠 Metotlar

| Metot | Açıklama |
|-|-|
| `insert` | Durum ekleme
| `delete`| Durum silme

> 💡 Durumlar: Beğenme, kaydetme, okunma

## 📰 News

- ❣️ Sadece haber bilgilerini barındırır
- 👮‍♂️ Tekrarklı haber kayıtları URL kontrolü ile engellenir
- 👨‍💼 Haberlerde çoklu kaydetme işlemleri görmezden gelinir

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
| `getNewsWithStateByState(type)` | Duruma göre haberleri duurmları ile alır
| `getAllNewsWithStateHasStates()` | Duruma sahip olan haberleri alır

> 💡 Durumlar: Beğenme, kaydetme, okunma

## 📝 DB Notları

### 📉 Feeds yapılmama sebebi

- 🎳 Veri sayısı arttıkça sorguların maaliyeti artacaç
- 📉 Veri tabanı sadeliğini kaybedecek

### ⚙️ Shared Preferences Yapılmama Sebebi

- 🔍 Sorgu işlemlerinin desteklenmemesi
- 🚄 İnt yerine, int list ile uğraşılması (key / value mantığında değil)
