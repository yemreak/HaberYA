# 🐣 API Kullanımı

## 📰 News API

### 📝 Resmi Dokümanlar

- [📃 API dokümanı](https://newsapi.org/docs)
- [🔥 Top Headlines dokümanı](https://newsapi.org/docs/endpoints/top-headlines)

### 🧱 Temel Kullanım

- 👮‍♂️ Her istek için özel `options` objesi tanımlanmalıdır
- 🕊️ Tüm metotların kullanılması gerekmez
- ❌ Varsayılan ülke kaldırıldı

```java
// ❌ Artık null ile kullanılamaz
// NewsAPI.requestTopHeadlines(this, this::saveToDB, null);
// NewsAPI.requestEverything(this, this::saveToDB, null);
// NewsAPI.requestSources(this, this::saveToDB, null);
```

### 🌟 Favori başlıkları alma

```java
THOptions thOptions = THOptions.thOptions()
    .setCountry(Options.Country.US)
    .setCategory(Options.Category.BUSINESS)
    .build();

NewsAPI.requestTopHeadlines(this, this::saveToDB, thOptions);
```

> ‍🧙‍♂ Detaylı bilgi için [Top Headlines ~ NewsAPI](https://newsapi.org/docs/endpoints/top-headlines) alanına bakın

### 🔍 Her şeyde arama

```java
EOptions eOptions = EOptions.Builder()
    .setQuery("Aranan metin")
    .setLanguage(Options.Language.EN,)
    .setCategory(Options.Category.BUSINESS)
    .setFrom(Options.getTheDayBefore(10)) // 10 gün öncesi
    .build();

NewsAPI.requestEverything(this, this::saveToDB, eOptions);
```

> ‍🧙‍♂ Detaylı bilgi için [Everything ~ NewsAPI](https://newsapi.org/docs/endpoints/everything) alanına bakın

### 📋 Kaynaklarda Arama

```java
SOptions sOptions = SOptions.Builder()
    .setCountry(Options.Language.EN,)
    .setCountry(Options.Country.US)
    .setCategory(Options.Category.BUSINESS)
    .build();

NewsAPI.requestEverything(this, this::saveToDB, sOptions);
```

> ‍🧙‍♂ Detaylı bilgi için [Sources ~ NewsAPI](https://newsapi.org/docs/endpoints/sources) alanına bakın
