package com.adhamenaya.example;

import retrofit.Call;
import retrofit.http.GET;

public interface WebServiceProxy {


	public static String ENDPOINT = "https://pixabay.com";

	@GET("/api/?key=YOU KEY&q=yellow+flower&image_type=photo")
	public Call<ResponseData> getImages();

}
