package com.shmundiak.currencyrates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private final static String KEY_CHAR_CODE = "char3";
    private final static String KEY_VALUE = "rate";
    private final static String KEY_NOMINAL = "size";
    private final static String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            populate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Data implements Runnable {
        ArrayList<Map<String, String>> data;
        @Override
        public void run() {
            data = getData();
        }

        public ArrayList<Map<String, String>> getValue() {
            return data;
        }
    }

    private void populate() throws InterruptedException {
        Data foo = new Data();
        Thread thread = new Thread(foo);
        thread.start();

        thread.join();

        ArrayList<Map<String, String>> data = foo.getValue();

        String[] from = {KEY_CHAR_CODE, KEY_VALUE, KEY_NOMINAL, KEY_NAME};
        int[] to = {R.id.charCodeView, R.id.valueView, R.id.nominalView, R.id.nameView};
        SimpleAdapter sa = new SimpleAdapter(this, data, R.layout.item_view, from, to);

        ListView theListView = findViewById(R.id.listView);
        theListView.setAdapter(sa);
    }

    private ArrayList<Map<String, String>> getData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> m;
        try {
            URL url = new URL(getString(R.string.rates_url));
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(in);
                Element docElement = dom.getDocumentElement();
                String date = docElement.getAttribute("Date");
//                setTitle(getTitle() + " на " + date);
                NodeList nodeList = docElement.getElementsByTagName("item");
                int count = nodeList.getLength();
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        Element entry = (Element) nodeList.item(i);
                        m = new HashMap<>();
                        String charCode = entry.getElementsByTagName(KEY_CHAR_CODE).item(0).getFirstChild().getNodeValue();
                        String value = entry.getElementsByTagName(KEY_VALUE).item(0).getFirstChild().getNodeValue();
                        String nominal = "за " + entry.getElementsByTagName(KEY_NOMINAL).item(0).getFirstChild().getNodeValue();
                        String name;
                        Node node = entry.getElementsByTagName(KEY_NAME).item(0).getFirstChild();
                        if(node != null) {
                            name = node.getNodeValue();
                        } else {
                            name = "";
                        }
                        m.put(KEY_CHAR_CODE, charCode);
                        m.put(KEY_VALUE, value);
                        m.put(KEY_NOMINAL, nominal);
                        m.put(KEY_NAME, name);
                        list.add(m);
                    }
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return list;
    }
}
