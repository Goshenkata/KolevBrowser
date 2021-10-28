package com.example.javafx5;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    WebView webView;
    @FXML
    TextField textField;
    @FXML
    BorderPane historyPane;
    @FXML
    ListView<String> historyList = new ListView<>();
    WebEngine engine;
    String homePage;
    WebHistory history;
    boolean isHistoryOpen = false;
    Element rootElement;
    private double webZoom;
    //private List<HistoryEntry> historyEntries;

//    private static void writeXMLFile(Document doc)
//            throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
//        doc.getDocumentElement().normalize();
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File("history.xml"));
//        transformer.transform(source, result);
//        System.out.println("XML file updated successfully");
//    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webZoom = 1;
        engine = webView.getEngine();
        homePage = "www.google.com";
        textField.setText(homePage);
        engine.setJavaScriptEnabled(true);
        historyPane.setPrefWidth(0);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        //File xmlFile = new File("D:\\JavaProjects\\javafx5\\history.xml");
        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(new FileInputStream(xmlFile));
//            doc.getDocumentElement().normalize();
//            System.out.println("Root element child: " + doc.getDocumentElement().getChildNodes());
//            historyEntries = new ArrayList<>();
//            NodeList nodeList = doc.getElementsByTagName("History-entry");
//            System.out.println("elements by tag name:  " + rootElement.getChildNodes());
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                historyEntries.add(getHistoryEnty(nodeList.item(i)));
//                System.out.println(nodeList.item(i).toString());
//            }
//            for (HistoryEntry emp : historyEntries) {
//                System.out.println(emp.toString());
//            }

            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                    history = engine.getHistory();
                    ObservableList<WebHistory.Entry> entries = history.getEntries();
                    WebHistory.Entry lastEntry = entries.get(history.getCurrentIndex());
                    //rootElement.appendChild(HelloController.this.createHistoryEntry(doc, lastEntry.getTitle(), lastEntry.getUrl(), lastEntry.getLastVisitedDate()));
                    //historyEntries.add(new HistoryEntry(lastEntry.getTitle(), lastEntry.getUrl(), lastEntry.getLastVisitedDate().toString()));
//                    try {
//                        writeXMLFile(doc);
//                    } catch (TransformerException e) {
//                        e.printStackTrace();
//                    }
                    textField.setText(lastEntry.getUrl());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        engine.load("http://" + textField.getText());
    }

//    private HistoryEntry getHistoryEnty(Node node) {
//        HistoryEntry entry = new HistoryEntry();
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            Element element = (Element) node;
//            entry.setTitle(getTagValue("title", element));
//            entry.setURL(getTagValue("url", element));
//            entry.setDate(getTagValue("date", element));
//        }
//        return entry;
//    }

    public void loadPage() {
        engine.load(textField.getText());
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        WebHistory.Entry lastEntry = entries.get(history.getCurrentIndex());
        textField.setText(lastEntry.getUrl());
    }

    public void refreshPage() {

        engine.reload();
    }

    public void zoomReset() {
        System.out.println("reset");
        webZoom = 1;
        webView.setZoom(webZoom);
    }

    public void zoomIn() {
        System.out.println("in");

        webZoom += 0.1;
        webView.setZoom(webZoom);
    }

    public void zoomOut() {
        System.out.println("out");

        webZoom -= 0.1;
        webView.setZoom(webZoom);
    }

    public void home() {
        textField.setText(homePage);
        engine.load("http://" + textField.getText());
    }

    public void back() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(-1);

        textField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    public void forward() {

        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        history.go(1);

        textField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }

    public void history() {
        if (!isHistoryOpen) {
            historyPane.setPrefWidth(200);
            isHistoryOpen = true;
        } else {
            historyList.setMaxWidth(0);
            historyPane.setMaxWidth(0);
            isHistoryOpen = false;
        }
    }

//    public Node createHistoryEntry(Document doc, String title, String url, Date date) {
//        Element user = doc.createElement("History-entry");
//        user.appendChild(createHistoryElements(doc, user, "title", title));
//        user.appendChild(createHistoryElements(doc, user, "URL", url));
//        user.appendChild(createHistoryElements(doc, user, "date", date.toString()));
//        return user;
//    }

//    private Node createHistoryElements(Document doc, Element element, String name, String value) {
//        Element node = doc.createElement(name);
//        node.appendChild(doc.createTextNode(value));
//        return node;
//    }

//    public class HistoryEntry {
//        private String title;
//        private String URL;
//        private String date;
//
//        public HistoryEntry() {
//            this.title = title;
//            this.URL = URL;
//            this.date = date;
//        }
//
//        public HistoryEntry(String title, String URL, String date) {
//            this.title = title;
//            this.URL = URL;
//            this.date = date;
//        }
//
//        public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getURL() {
//            return URL;
//        }
//
//        public void setURL(String URL) {
//            this.URL = URL;
//        }
//    }
}