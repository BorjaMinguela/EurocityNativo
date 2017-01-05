package com.example.borja.eurocity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Idioma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);
        WebView web = (WebView)findViewById(R.id.idiomaWeb);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("https://translate.google.com/");
        //web.loadData("Hola mundo", "text/html", null);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
    }
}
