package com.yemreak.haberya.api.newsapi;


import androidx.annotation.NonNull;

/**
 * Top Headlines işlemleri için gerekli ayarlar
 *
 * @see <a href="https://newsapi.org/docs/endpoints/top-headlines">Top Headlines ~ NewsAPI</a>
 */
public class THOptions extends Options {

	public static final String HEAD = "top-headlines";

	private Country country;
	private Category category;

	private String sources;
	private String query;

	private int pageSize;
	private int page;

	THOptions(Builder builder) {
		this.country = builder.country;
		this.category = builder.category;
		this.sources = builder.sources;
		this.query = builder.query;
		this.pageSize = builder.pageSize;
		this.page = builder.page;
	}

	public static Builder Builder() {
		return new Builder();
	}

	public String buildUrl(String apiKey) {
		return super.buildUrl(HEAD, buildQueries(), apiKey);
	}

	private String buildQueries() {
		return trimQuery("" // 🦅
				+ generateQuery("country", country)
				+ generateQuery("category", category)
				+ generateQuery("sources", sources)
				+ generateQuery("q", query)
				+ generateQuery("pageSize", pageSize)
				+ generateQuery("page", page)
		);
	}

	public Country getCountry() {
		return country;
	}

	public Category getCategory() {
		return category;
	}

	public String getSources() {
		return sources;
	}

	public String getQuery() {
		return query;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPage() {
		return page;
	}

	public static final class Builder {

		private static Country defaultCountry;

		private Country country = defaultCountry;
		private Category category;

		private String sources;
		private String query;

		private int pageSize;
		private int page;

		public static void setDefaultCountry(@NonNull Country country) {
			defaultCountry = country;
		}

		public Builder setCountry(Country country) {
			this.country = country;
			return this;
		}

		public Builder setCategory(Category category) {
			this.category = category;
			return this;
		}

		public Builder setSources(String sources) {
			this.sources = sources;
			return this;
		}

		public Builder setQuery(String query) {
			this.query = query;
			return this;
		}

		public Builder setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder setPage(int page) {
			this.page = page;
			return this;
		}

		public THOptions build() {
			return new THOptions(this);
		}
	}
}
