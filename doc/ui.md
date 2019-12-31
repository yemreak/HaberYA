# ✨ Kullanıcı Arayüzü

- [ ] Opiton menu button ile
    - 💖 💾 🕐
- [ ] 🎨 Kalbin içini doldurma
- [ ] 🕐 Daha sonro oku işaretlendiğinde belirgin olmalı
- [ ] 👁️ Okunan haberler için alpha değeri ayarlanacak (soluk olma)



## 👨‍🔬 Test

- API'dan haberleri al
- IDleri olanları sil (ID shared preferences)
- insert(news) haberleri yaz
- Observe içerisinde: id'leri objeye al (üzerine new ile yaz)
- UI'da sadece idleri olanları listele (`getNewsByIDs(ids)`)
- Daha sonra oku: DB'ye state.later ve idlerden kaldır
- Haber açıldığında:
    - Read: okundu bilgisi state'e kaydedilecek
    - ID'Lerden çıkarılmayacak
    - Liked: eğer beğenildiyse kalp dolu olacak
- Okunan haberler için alpha değeri `0.6` olacak
- Uygulama internetsiz açılınca:
    - ID'si olanları göster

