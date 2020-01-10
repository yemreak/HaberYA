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


