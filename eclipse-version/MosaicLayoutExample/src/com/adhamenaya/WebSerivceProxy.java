package com.adhamenaya;

import retrofit.http.GET;

public interface WebSerivceProxy {

	public static String ENDPOINT = "https://ajax.googleapis.com";
	public static String V = "v=1.0";
	public static String Q = "q=mountain";
	public static String START = "start=8";
	public static String RSZ = "rsz=8";
	public static String IMGSZ = "imgz=medium";

	public static String IMGTYPE = "imgtype=photo";

	@GET("/ajax/services/search/images?" + V + "&" + Q + "&" + START + "&" + RSZ + "&" + IMGSZ + "&" + IMGTYPE)
	public Result getImages();

}
