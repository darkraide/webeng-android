package http;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.BengModelItem;



public class getlistbengitems {
	public Listbeng getall() {
		Listbeng listbengs = new Listbeng();
		try {
			URL url = new URL("http://webenggg.apiary.io/bengs");
			// try this url =
			// "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
			HttpGet httpRequest = null;

			httpRequest = new HttpGet(url.toURI());

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient
					.execute(httpRequest);

			HttpEntity entity = response.getEntity();
			BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			InputStream input = b_entity.getContent();
			
			Gson gson = new Gson();
			 listbengs= gson.fromJson(new InputStreamReader(input), Listbeng.class);
			// Message msg = handler.obtainMessage(1, bitmap);
			// handler.sendMessage(msg);

		} catch (Exception ex) {

		}
		return listbengs;
	}

	public class Listbeng {
		private List<BengModelItem> listbengs;

		public Listbeng() {
			listbengs = new ArrayList<BengModelItem>();
		}

		public List<BengModelItem> getListbengs() {
			return listbengs;
		}

		public void setListbengs(List<BengModelItem> listbengs) {
			this.listbengs = listbengs;
		}

	}

}
