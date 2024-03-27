package framework.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpClientUtils {

	public static String doPost(String url, Object bodyValue, String content) {

		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		String resToSerivce = "";
		try {
			json = mapper.writeValueAsString(bodyValue);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.get(content + "; charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, json);
		Response response = null;
		Request request = new Request.Builder().url(url).method("POST", body).addHeader("Content-Type", content)
				.build();
		try {
			Call call = client.newCall(request);
			response = call.execute();

			resToSerivce = response.body().string();
			response.close();
//			System.out.println(resToSerivce);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return resToSerivce;
	}

	public static String doGet(String url)  {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(url).build();
		Response response  = null;
		String resToSerivce = "";
		try {
			Call call = client.newCall(request);
			response = call.execute();

			resToSerivce = response.body().string();
			response.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resToSerivce;
	}

	public static String doPost(String url, List<NameValuePair> nvps) {
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response;
		String responseString = "";
		try {
			response = httpClient.execute(post);
			response.getStatusLine();
			responseString = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseString;
	}

}
