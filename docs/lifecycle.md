# 🌱 Uygulamanın Yaşam Döngüsü

## 🎈 Genel İşleyiş

- 📶 Internet varsa:
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
