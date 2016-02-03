package com.adhamenaya.example;

import java.util.List;

public class ResponseData {
	public List<Image> getHits() {
		return hits;
	}

	public void setHits(List<Image> hits) {
		this.hits = hits;
	}

	private List<Image> hits;

}
