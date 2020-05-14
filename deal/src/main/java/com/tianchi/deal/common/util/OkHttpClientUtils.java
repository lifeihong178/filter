package com.tianchi.deal.common.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OkHttpClientUtils {

    public static String httpGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string(); // 返回的是string 类型
    }

    /**
     * get请求获取response
     * @param url
     * @return
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response; // 返回的是string 类型
    }


    public static Headers SetHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();

        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));

            }
        }
        headers = headersbuilder.build();

        return headers;
    }


    public static String httpGet(String url, Map<String, String> headers) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url)
                .headers(OkHttpClientUtils.SetHeaders(headers))
                .build();


        Response response = httpClient.newCall(request).execute();
        return response.body().string(); // 返回的是string 类型
    }

    public static String httpPost(String url, Map<String, String> params) throws IOException {
        //创建OKHttpClient
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map集合，将参数放入body即可    遍历的核心思想就是。根据键取值
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            //放入body
            builder.add(key, value);
        }
        FormBody body = builder.build();
        //用request 请求，将url，和参数惊醒封装。
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public static String httpPost(String url, String str) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string(); // 返回的是string 类型
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * http json 请求
     *
     * @param url
     * @param jsonStr
     * @return
     * @throws IOException
     */
    public static String postHttpJsonStr(String url, String jsonStr) throws IOException {
        /**
         * 返回的仍然是json格式
         */

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


}
