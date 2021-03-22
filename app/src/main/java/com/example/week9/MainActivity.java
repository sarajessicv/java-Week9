package com.example.week9;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    String teatteri;
    Context context = null;
    Hallitse hallitse = new Hallitse();
    String sdate;
    String leffadate;
    String ID;
    LocalTime aloitas;
    LocalTime lopetas;
    EditText aloita;
    EditText lopeta;
    LocalDateTime date;
    private ArrayList<String> leffat = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        aloita = (EditText) findViewById(R.id.Aloita);
        lopeta = (EditText) findViewById(R.id.Lopeta);
        Spinner spinner = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.spinner2);




        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        String teatteriURL = "https://www.finnkino.fi/xml/TheatreAreas/";
        Document doc = null;
        try {
            doc = builder.parse(teatteriURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < nList.getLength() ; i++){
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                hallitse.createTeatteri(element.getElementsByTagName("Name").item(0).getTextContent(), element.getElementsByTagName("ID").item(0).getTextContent());
                System.out.println(element.getElementsByTagName("Name").item(0).getTextContent());
                arrayList.add(element.getElementsByTagName("Name").item(0).getTextContent());
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teatteri = parent.getItemAtPosition(position).toString();
                //System.out.println(teatteri+"###############TÄSSÄ########");
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                System.out.println("onko tässä");
            }
        });


        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter format2 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DocumentBuilder builder2 = null;
        try {
            builder2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        String dateURL = "https://www.finnkino.fi/xml/ScheduleDates/";


        Document doc2 = null;
        try {
            doc2 = builder2.parse(dateURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        doc2.getDocumentElement().normalize();
        NodeList nList2 = doc2.getDocumentElement().getElementsByTagName("dateTime");
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (int i = 0; i < nList2.getLength() ; i++){
            Node node2 = nList2.item(i);
            if (node2.getNodeType() == Node.ELEMENT_NODE){
                Element element2 = (Element) node2;
                sdate = (element2.getTextContent());
                date = LocalDateTime.parse(sdate, format);
                sdate = date.format(format2);
                //System.out.println(sdate);
                arrayList2.add(sdate);
            }
        }
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leffadate = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showMovies() {
        //System.out.println("KATO TÄÄ EKA"+teatteri);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        try {
            aloitas = LocalTime.parse(aloita.getText().toString(), format);
        } catch (Exception e) {
            aloitas = null;
        }
        try {
            lopetas = LocalTime.parse(lopeta.getText().toString(), format);
        } catch (Exception e) {
            lopetas = null;
        }
        leffat = hallitse.findMovies(teatteri, leffadate, aloitas, lopetas);
        Intent intent = new Intent(this,list_items.class);
        intent.putStringArrayListExtra("leffat", leffat);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void press(View v){
        showMovies();
    }

}
