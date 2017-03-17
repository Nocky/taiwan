/*
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkloving.taiwan.http.basic;

import android.content.Context;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.Request;
import com.yolanda.nohttp.RequestQueue;
import com.yolanda.nohttp.download.DownloadQueue;

/**
 * Created in Oct 23, 2015 1:00:56 PM
 * 
 * @author YOLANDA
 */
public class CallServer {

	private static CallServer callServer;

	private RequestQueue requestQueue;

	private static DownloadQueue downloadQueue;

	private CallServer() {
		requestQueue = NoHttp.newRequestQueue();
	}

	/**
	 * 创建请求对象，管理请求队列
	 */
	public static CallServer getRequestInstance() {
		if (callServer == null)
			callServer = new CallServer();
		return callServer;
	}

	public static DownloadQueue getDownloadInstance() {
		if (downloadQueue == null)
			downloadQueue = NoHttp.newDownloadQueue();
		return downloadQueue;
	}

	/**
	 * 添加一个请求到请求队列
	 */
	public <T> void add(Context context,boolean showDialog,int what, Request<T> request, HttpCallback<T> callback) {
		// what: 用来区分请求，当多个请求使用同一个OnResponseListener时，在回调方法中会返回这个what
		// request: 请求对象，包涵Cookie、Head、请求参数、URL、请求方法
		// responseListener 请求结果监听，回调时把what原样返回
		requestQueue.add(what, request, new HttpResponseListener<T>(context, callback,showDialog));
	}

	/**
	 * 添加一个请求到请求队列(带dialog)
	 */
	public <T> void add(Context context,String message,int what, Request<T> request, HttpCallback<T> callback) {
		// what: 用来区分请求，当多个请求使用同一个OnResponseListener时，在回调方法中会返回这个what
		// request: 请求对象，包涵Cookie、Head、请求参数、URL、请求方法
		// responseListener 请求结果监听，回调时把what原样返回
		requestQueue.add(what, request, new HttpResponseListener<T>(context, callback,message));
	}

	/**
	 * 取消这个sign标记的所有请求
	 */
	public void cancelBySign(Object sign) {
		requestQueue.cancelBySign(sign);
	}

	/**
	 * 取消队列中所有请求
	 */
	public void cancelAll() {
		requestQueue.cancelAll();
	}

	/**
	 * 退出app时停止所有请求
	 */
	public void stopAll() {
		requestQueue.stop();
	}

}
