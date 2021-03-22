package com.example.week9;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Hallitse {
    ArrayList <Teatteri> teatterilista = new ArrayList<>();
    ArrayList <String> movies = new ArrayList<>();
    String movieURL;
    String ID;
    String aika1;
    LocalDateTime time1;
    LocalTime time2;



    public Hallitse(){
    }

    public void createTeatteri(String nimi, String id){
        Teatteri tt = new Teatteri(nimi, id);
        teatterilista.add(tt);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList findMovies(String teatteri, String pvm, LocalTime ennen, LocalTime jalkeen){
        ID = findTeatteri(teatteri);
        this.movieURL = "https://www.finnkino.fi/xml/Schedule/?area="+ID+"&dt="+pvm;
        //System.out.println(movieURL);
        DocumentBuilder builder = null;
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(movieURL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");;
        for (int i = 0; i < nList.getLength() ; i++){
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm");
                Element element = (Element) node;
                //System.out.println(element.getElementsByTagName("Title").item(0).getTextContent());
                aika1 = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                time1 = LocalDateTime.parse(aika1, format);
                aika1 = time1.format(format2);
                //System.out.println(aika1);
                //System.out.println(ennen);
                //System.out.println(jalkeen);
                time2 = LocalTime.parse(aika1, format2);
                if (ennen == null && jalkeen == null){
                    movies.add(element.getElementsByTagName("Title").item(0).getTextContent());
                }
                else if (time2.isBefore(ennen) && time2.isAfter(jalkeen)){
                    movies.add(element.getElementsByTagName("Title").item(0).getTextContent());
                }
                else if (time2.isBefore(ennen) && jalkeen == null){
                    movies.add(element.getElementsByTagName("Title").item(0).getTextContent());
                }
                else if (ennen == null && time2.isAfter(jalkeen)){
                    movies.add(element.getElementsByTagName("Title").item(0).getTextContent());
                }

            }
        }
        return movies;
    }

    public String findTeatteri(String teatteri){
        if (movies.size() != 0){
            for (int i = 0; i < movies.size(); i++){
                movies.remove(0); // tyhjennetään lista jos on
            }
        }
        for (int i = 0; i < teatterilista.size(); i++){
            //System.out.println("#############"+teatteri);
            if (teatterilista.get(i).getName().equals(teatteri)){
                this.ID = teatterilista.get(i).getID();
            }
        } return this.ID;
    }



}
