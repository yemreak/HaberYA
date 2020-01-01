# 🌱 Uygulamanın Yaşam Döngüsü

## 🎈 Genel İşleyiş

- 📶 Interne varsa:
    -  API'den haber bilgileri alınır
    - 💾 Room'a kaydedilir
    - 🎳 Çok fazla veri oluşumunu engellemek adına veriler 400 ile sınırlıdır
    - 👨‍💼 400'ü geçmesi duurmunda beğenme, kayıt edilme veya daha sonra oku gibi etkilere uğramayan son 200 haber silinir
- 🗃️ Room içerisinden eski saklanan haberler çekilir
    - 💡 Internet olmazsa direkt bu aşamadan başlar
- 👁️ Observe yapısıyla veriler otomatik güncellenir
- 👀 RecyclerView ile ekrana sistemi yormayacak şekilde basılır
    - 📖 Okunmuş haberlerde soluk olma (alpha) efekti vardır
    - 🕐 İsteğe bağlı haberler "daha sonra oku" ile etiketlenebilir
- 📃 Haberlere tıklanması haline yeni Activity üzerinde işlemler yapılır
    - 🌍 Seçili haberin bilgileri Globals sınıfı üzerinden diğer activity'e aktarılır
        - 🎈 Bunun yapılmasındaki Intent'lere yüksek boyutlu veriler taşıtmamaktır
    - 🧐 Haber sayfasında beğenme ve paylaşma özellikleri vardır
        - 💖 Haberler açıldıklarında beğenme butonu ile beğenilebilir
        - 🔀 Haberleri sağ üst köşeden paylaşabilirsiniz
- 📌 Herhangi bir durumla işaretlenen haberler, saklanmaktadır
- ✨ Tüm bu işlemlerin her biri room üzerinde tablolarda saklanmaktadır

> ⚠️ Okundu bilgisi için id silin**me**yecek (yapılmadı)

# ✨ Kullanıcı Arayüzü

- 👁️ `Main Activity`'deki listelenen haberler okunma durumuna göre renklendirilmekte
  - 🌑 Okunan haberler için _alpha_ değerini ayarlayarak soluk yapılmakta
- 🕐 `Main Activity`'de Daha sonra oku ikonu blunmakta
  - 🔃 Create edilirken haber önceden kaydedildiyse saat ikonu dolu tik ✔️ içermekte, aksi halde artı içermekte ➕
  - 🚙 İkona basılınca yeni durum veri tabanına aktarılmakta
- 👇 `Main Activity`'de bir habere tıklandığı zaman haber içeriği `Globals`'a kaydedilip _Explicit Intent_ ile `News Activity`'ye yölendirilmekte
  - 👷‍♀️ Daha sonra `News Activity`'sinde `Globals`'dan çekilip gösterilmekte
- 💫 `Main Activity`'deki `Opitons menu` seçenekleriyle 💖 beğenilen haberler, 💾 kaydedilen haberler veya tüm etkileşim alan haberler listelenmekte
  - ➰ `Reacted Activity`'de `RecyclerView` yapısıyla `Main Activity`'deki fonksiyonelliğe sahip bir yapı gösterilmekte
- 🎈 `News Activity`'de _Implicit Intent_ ile haber paylaşılabilmekte
- 💖 `News Activity`'de beğenme butonu bulunmakta
  - 🔃 Create edilirken haber önceden beğenildiyse kalp ikonu dolu olmakta, aksi halde boş olmakta
  - 🚙 İkona basılınca yeni durum veri tabanına aktarılmakta

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
