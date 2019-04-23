package com.course.example.sqlitedemopro;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// Tom Abowd
public class Map extends FragmentActivity implements OnMapReadyCallback {


    // Instance Variables
    private GoogleMap mMap;
    private static final float zoom = 17.0f;
    private EditText text;
    private WebView webView;
    private Button button;
    private TextView selection;
    private String URLBB = "https://blackboard.bentley.edu/webapps/login/";
    // Guide URLs
    private final String[] bentleyItems = {"","https://www.bentley.edu/centers/acelab/about-acelab",
            "https://twitter.com/cissandbox?lang=en","https://www.bentley.edu/centers/clic",
            "https://www.bentley.edu/academics/departments/economics/eco-fi-stat-learning-center",
            "https://www.bentley.edu/academics/departments/english-and-media-studies/esol-center",
            "https://www.bentley.edu/academics/departments/mathematical-sciences/mathematics-learning-center",
            "https://www.bentley.edu/academics/departments/english-and-media-studies/writing-center"
    };
    // Guide Names
    private final String[] items = { "--Choose a center for more info---","ACELAB \uD83D\uDC54", "CIS Sandbox \uD83D\uDCBB ", "CLIC \uD83C\uDF0E",
            "Eco-Fi-Stat Learning Center \uD83D\uDCB0",
            "ESOL Center \uD83D\uDDE8","Mathematics Learning Center ➗", "Writing Center ✍\uD83C\uDFFB"
    };


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Setting up TabHost
        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        // Initialize a TabSpec for tab1 and add it to the TabHost
        spec = tabs.newTabSpec("tag1");    //create new tab specification
        spec.setContent(R.id.tab1);    //add tab view content
        spec.setIndicator("Map");    //put text on tab
        tabs.addTab(spec);             //put tab in TabHost container

        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab2 and add it to the TabHost
        spec = tabs.newTabSpec("tag2");        //create new tab specification
        spec.setContent(R.id.tab2);            //add view tab content
        spec.setIndicator("BlackBoard");
        tabs.addTab(spec);                    //put tab in TabHost container

        button = (Button) findViewById(R.id.Button01);
        text = (EditText) findViewById(R.id.EditText01);
        webView = (WebView) findViewById(R.id.web);

        //intercept URL loading and load in widget
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                view.loadUrl(url);
                return true;
            }

        });

        //set listeners for web tab
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.loadUrl(text.getText().toString());

            }
        });

        text.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.loadUrl(text.getText().toString());
                    return true;
                }
                return false;
            }
        });


        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab3 and add it to the TabHost
        spec = tabs.newTabSpec("tag3");        //create new tab specification
        spec.setContent(R.id.tab3);            //add tab view content
        spec.setIndicator("Tutoring Centers");            //put text on tab
        tabs.addTab(spec);                    //put tab in TabHost container

        selection = (TextView) findViewById(R.id.selection);
        Spinner spin = (Spinner) findViewById(R.id.spinner);

        //Create an ArrayAdapter and a default spinner layout
        ArrayAdapter<String> aa = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items);

        //Specify the layout to use when the list of choices appears
        aa.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);  //connect ArrayAdapter to <Spinner>

        //listener methods for callbacks
        spin.setSelection(0,false);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection.setText(items[i]);
                // opening the url for the item selected in the web view
                webView.loadUrl(bentleyItems[i]);
                TabHost tabs = (TabHost) findViewById(R.id.tabhost);
                // setting the current tab to tab 2
                tabs.setCurrentTab(1);}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selection.setText("");
            }
        });


    }//class
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //center map and set zoom level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( 42.387856,  -71.219850), zoom));

        //set markers
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.387246,
                        -71.220548))
                .title("CIS Sandbox - Smith 234\n\n"
                +"Stuck on a difficult coding problem? Come to the CIS Sandbox for help with " +
                        "your CIS courses!")
                .snippet("https://twitter.com/cissandbox?lang=en")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.sandbox)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.387886,
                        -71.220024))
                .title("Writing Center -  Library Lower Level 023\n\n"+
                "Need help improving your paper? Come to the writing center!")
                .snippet("https://www.bentley.edu/academics/departments/english-and-media-studies/writing-center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pencil)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.387908,
                        -71.219717))
                .title("ESOL Center - Library Lower Level 026\n\n"+
                        " Is your first language a language other than English? Are you looking to improve your English skills?" +
                        " Come visit the ESOL Center!")
                .snippet("https://www.bentley.edu/academics/departments/english-and-media-studies/esol-center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.esol)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.386977,
                        -71.218895))
                .title("CLIC - Adamian 162\n\n"
                + "For help in modern languages come to the Center for Language & International Collaboration (CLIC)!")
                .snippet("https://www.bentley.edu/centers/clic")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.globe)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.387319,
                        -71.219746))
                .title("ACELAB - Lindsay 21\n\n" +
                "Need help in GB 112, 212, or an upper-level Accounting course? Come to the ACELAB!")
                .snippet("https://www.bentley.edu/centers/acelab/about-acelab")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.accounting)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.387942,
                        -71.220637))
                .title("Mathematics Learning Center - Jennison 218\n\n" +
                        "Struggling with math? Come to the Mathematics Learning Center!")
                .snippet("https://www.bentley.edu/academics/departments/mathematical-sciences/mathematics-learning-center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.math)));

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(42.3871777,
                        -71.218626))
                .title("Eco-Fi-Stat Learning Center -  Adamian 122\n\n" +
                "Looking for help in EC 111, EC 112, GB 210, GB 213, or an upper-level Economics or Finance course?" +
                        " Come Visit the Eco-Fi-Stat Learning Center!")
                .snippet("https://www.bentley.edu/academics/departments/economics/eco-fi-stat-learning-center")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ecofi)));

        //set listeners
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    public boolean onMarkerClick(Marker m) {
                        String title = m.getTitle();
                        String snip = m.getSnippet();
                        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
                       // webView.loadUrl(snip);
                        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
                        //tabs.setCurrentTab(1);
                        return true;
                    }
                }
        );

    }
    // allows the user to navigate to previous web pages in the web view
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        // allows the user to exit the app by pressing the back button
        finish();
    }
}