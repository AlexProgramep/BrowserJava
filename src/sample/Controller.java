import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane tabpane;

    @FXML
    private Tab tabs;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private WebView webview;

    @FXML
    private ProgressBar progress;

    @FXML
    private Button prewpage;

    @FXML
    private Button nextpage;

    @FXML
    private Button search;

    @FXML
    private Button reload;

    @FXML
    private Button home;

    @FXML
    private Button delete;

    @FXML
    private TextField Textsearch;

    @FXML
    void initialize() {
        final WebEngine webEngine = webview.getEngine();
        final Worker<Void> worker = webEngine.getLoadWorker();
        final WebHistory history = webEngine.getHistory();
        webEngine.getDocument();

        webEngine.setJavaScriptEnabled(true);
        Document doc = webEngine.getDocument();
        progress.progressProperty().bind(worker.progressProperty());
        history.getEntries().addListener(new ListChangeListener<WebHistory.Entry>() {
            @Override
            public void onChanged(Change<? extends WebHistory.Entry> c) {
                c.next();
                for (WebHistory.Entry e : c.getRemoved()) {
                    Textsearch.setText(e.getUrl());
                }
                for (WebHistory.Entry e : c.getAddedSubList()) {
                    Textsearch.setText(e.getUrl());
                }
            }
        });
        search.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent event) {
                                   String url = Textsearch.getText();
                                   boolean urlhttp = url.startsWith("http");
                                   boolean gwvignette = url.endsWith("google_vignette");
                                   if (urlhttp) {
                                       webEngine.load(url);

                                   }else if (gvignette){
                                       webEngine.executeScript("history.go(-1);");
                                   } else {
                                   webEngine.load("https://www.google.com/search?q=" + url);
                                   }
                               }

                           }
        );
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String home = "https://www.google.com";
                webEngine.load(home);
            }
        });
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("location.href=location.href;");
            }
        });
        nextpage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("history.go(+1);");
            }
        });
        prewpage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                webEngine.executeScript("history.go(-1);");
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Textsearch.setText("");
            }
        });
    }
}
