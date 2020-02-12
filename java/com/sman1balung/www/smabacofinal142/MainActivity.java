package com.sman1balung.www.smabacofinal142;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    ImageView imgJadwal, imgGuru, imgSiswa, imgTentang, imgInfo, imgOffline;
    WebView webView1, webView2;
    ProgressBar progressBar1;
    ScrollView scrollView1;
    String rootUrl = "";

    String rootUrl2 = "https://sman1balung.sch.id/webapp/";
    String urlJadwal = rootUrl2+"jadwal.php";
    String urlGuru = rootUrl2+"list_guru.php";
    String urlSiswa = rootUrl2+"siswa.php";
    String urlInfo = rootUrl2+"info.php";
    String urlTentang = "file:///android_asset/www/tentang.html";
    Boolean hideScroll = false;
    public static Integer sekarang;
    public static Integer waktu;
    public static String versi_jadwal;
    public static String versi_jadwal_device;
    public static String dump_text="";

    public static MainActivity ma;

    public static final String SHARED_PREFS ="sharedPrefs";
    public static final String TEXT ="text";
    public static SharedPreferences msP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //versi_jadwal_device="a01";

        ma = this;

        fetchhari fhari = new fetchhari();
        fhari.execute();

        loadData();

        imgJadwal = (ImageView) findViewById(R.id.imgJadwal);
        imgGuru = (ImageView) findViewById(R.id.imgGuru);
        imgSiswa = (ImageView) findViewById(R.id.imgSiswa);
        imgTentang = (ImageView) findViewById(R.id.imgTentang);
        imgInfo = (ImageView) findViewById(R.id.imgInfo);
        imgOffline = (ImageView) findViewById(R.id.imgOffline);

        webView1 = (WebView) findViewById(R.id.webView1);
        webView2 = (WebView) findViewById(R.id.webView2);

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.setWebChromeClient(new WebChromeClient());
        webView1.setWebViewClient(new myWebClient());
        webView1.loadUrl("file:///android_asset/www/loading.html");

        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.setWebChromeClient(new WebChromeClient());
        webView2.loadUrl("file:///android_asset/www/loading.html");

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);

        imgJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScroll();
                webView1.loadUrl(urlJadwal);
            }
        });
        imgGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScroll();
                webView1.loadUrl(urlGuru);
            }
        });
        imgSiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScroll();
                webView1.loadUrl(urlSiswa);
            }
        });
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideScroll = true;
                scrollView1.setVisibility(View.GONE);
                webView1.setVisibility(View.GONE);
                webView2.loadUrl(urlInfo);
            }
        });
        imgTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideScroll = true;
                scrollView1.setVisibility(View.GONE);
                webView1.setVisibility(View.GONE);
                webView2.loadUrl(urlTentang);
            }
        });
        imgOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(),Jadwal2Activity.class);
                startActivity(intent);
            }
        });


    }

    private void loadData() {
        msP = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        versi_jadwal_device = msP.getString(TEXT,"");
    }

    private void hideScroll() {
        hideScroll = true;
        scrollView1.setVisibility(View.GONE);
    }
//stert
    public class myWebClient extends WebViewClient{
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        progressBar1.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        view.loadUrl("file:///android_asset/www/error.html");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar1.setVisibility(View.GONE);
    }
};
    //end

    @Override
    public void onBackPressed() {
        if (hideScroll == false) {
            super.onBackPressed();
        } else {
            scrollView1.setVisibility(View.VISIBLE);
            webView1.setVisibility(View.VISIBLE);
            hideScroll = false;
            webView1.loadUrl("file:///android_asset/www/loading.html");
            webView2.loadUrl("file:///android_asset/www/loading.html");
        }
        ;
    }

}
