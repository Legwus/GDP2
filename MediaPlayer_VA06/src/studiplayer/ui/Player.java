
package studiplayer.ui;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studiplayer.audio.NotPlayableException;
import studiplayer.audio.PlayList;
import studiplayer.audio.SortCriterion;

public class Player extends Application {
private static final String PLAYLIST_DIRECTORY = "/home/przemek/eclipse-workspace/MediaPlayer_VA06/playlists/";
private static final String INITIAL_PLAY_TIME_LABEL = "00:00";
private static final String NO_CURRENT_SONG = "-";
public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
private PlayList playList;
private SongTable songTable;
private boolean useCertPlayList = false;
private Button playButton;
private Button pauseButton;
private Button stopButton;
private Button nextButton;
private Label playListLabel;
private Label playTimeLabel;
private Label currentSongLabel;
private ChoiceBox<SortCriterion> sortChoiceBox;
private TextField searchTextField;
private Label sortLabel;
private Button filterButton;
private SortCriterion selectedCriterion;

	
public Player() {
	
}

public void start(Stage stage) throws Exception{
	
	BorderPane mainPane = new BorderPane();
	
	stage.setTitle("title");
	Scene scene = new Scene(mainPane, 500, 400);
	stage.setScene(scene);
	stage.show();
	
	Label searchLabel = new Label("Search");
	
    searchTextField = new TextField();
    searchTextField.setPrefWidth(200);
    
    sortLabel = new Label("Sort");
    
    sortChoiceBox = new ChoiceBox<>();
    sortChoiceBox.setPrefWidth(200);
    
    SortCriterion[] values = SortCriterion.values();
    for (SortCriterion value : values) {
    	//value.toString();
    	sortChoiceBox.getItems().addAll(value);
    }
    
    sortChoiceBox.setValue(SortCriterion.DEFAULT);
    
    filterButton = new Button("Submit");
    
   
	
    HBox searchHBox = new HBox(40); 
    searchHBox.getChildren().addAll(searchLabel, searchTextField);
    
    HBox sortHBox = new HBox(20);
    sortHBox.getChildren().addAll(sortLabel, sortChoiceBox, filterButton);
    
    VBox combinedVBox = new VBox(10);
    combinedVBox.getChildren().addAll(searchHBox, sortHBox);
    
    TitledPane titledPane = new TitledPane();
    titledPane.setText("Filter");
    titledPane.setContent(combinedVBox);
    
    HBox bottomHBox = new HBox(10);
    
    playButton = createButton("play.jpg");
    pauseButton = createButton("pause.jpg");
    stopButton = createButton("stop.jpg");
    nextButton = createButton("next.jpg");
    
    
    
    HBox secondToBottomHBox = new HBox();
    HBox thirdToBottomHBox = new HBox();
    HBox fourthToBottomHBox = new HBox();
    
    playListLabel = new Label("Current Playlist:    " + PLAYLIST_DIRECTORY + playList);
    currentSongLabel = new Label("Current Song:       " + NO_CURRENT_SONG);
    playTimeLabel = new Label("Time Elapsed:       " + INITIAL_PLAY_TIME_LABEL);
    
    secondToBottomHBox.getChildren().addAll(playListLabel);
    thirdToBottomHBox.getChildren().addAll(currentSongLabel);
    fourthToBottomHBox.getChildren().addAll(playTimeLabel);
    
    bottomHBox.getChildren().addAll(playButton, pauseButton, stopButton, nextButton);
    bottomHBox.setAlignment(javafx.geometry.Pos.CENTER);
    //PlayList playList = new PlayList("playlists/playList.cert.m3u");
    loadPlayList("playlists/playList.cert.m3u");
    songTable = new SongTable(playList);
    songTable.setMaxHeight(500);
    
    songTable.setRowSelectionHandler(event ->  {
    	if(event.getClickCount() == 2) {
    		System.out.println(songTable.getSelectionModel().getSelectedItem().getAudioFile());
    		var song = songTable.getSelectionModel().getSelectedItem().getAudioFile();    		
    		playList.jumpToAudioFile(song);
    		try {
				playCurrentSong();
			} catch (NotPlayableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    });
    
    
  //  songTable.getSelectionModel().getSelectedItem().getAudioFile();
    
    filterButton.setOnAction(event -> {
    	playList.setSearch(searchTextField.getText());
    	playList.setSortCriterion(sortChoiceBox.getValue());
        playList.sortPlayList();
        songTable.refreshSongs();
    	// PlayList.setSearch(searchTextField.commitValue().toString());
    	
    });
    
    playButton.setOnAction(e -> {
    	try {
			playCurrentSong();
		} catch (NotPlayableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	});
    
    
    
    
    VBox bottomContentVBox = new VBox(0);
    bottomContentVBox.getChildren().addAll(secondToBottomHBox, thirdToBottomHBox, fourthToBottomHBox, bottomHBox);
       
    mainPane.setTop(titledPane);	
    mainPane.setCenter(songTable);  
   // mainPane.setBottom(secondToBottomHBox);
    mainPane.setBottom(bottomContentVBox);
    
//  FileChooser fileChooser = new FileChooser();
//	fileChooser.setTitle("Open Resource File");
//	fileChooser.showOpenDialog(stage);
    
}

public void playCurrentSong() throws NotPlayableException{
	playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
	currentSongLabel.setText(playList.currentAudioFile().toString());
	playList.currentAudioFile().play();		
}

public void stopCurrentSong(){
	playList.currentAudioFile().stop();
	playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
}

public void pauseCurrentSong(){
	playList.currentAudioFile().togglePause();
}

public void skipCurrentSong() throws NotPlayableException{
	stopCurrentSong();
	playList.nextSong();
	playTimeLabel.setText(INITIAL_PLAY_TIME_LABEL);
	currentSongLabel.setText(playList.currentAudioFile().toString());
	playCurrentSong();
}

public static void main(String[] args) {
	launch();
}

public boolean isSetUseCertPlayList() {
	return useCertPlayList;
}

public void setPlayList(String pathname) {
	PlayList pl = new PlayList(pathname);
	this.playList = pl;
}

public void loadPlayList(String pathname) {
	 playList = new PlayList();
	 playList.loadFromM3U(pathname);
}

public void setUseCertPlayList(boolean setUseCertPlayList) {
	this.useCertPlayList = setUseCertPlayList;
}

private Button createButton(String iconfile) {
	Button button = null;
	try {
	URL url = getClass().getResource("/icons/" + iconfile);
	Image icon = new Image(url.toString());
	ImageView imageView = new ImageView(icon);
	imageView.setFitHeight(20);
	imageView.setFitWidth(20);
	button = new Button("", imageView);
	button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	button.setStyle("-fx-background-color: #fff;");
	} catch (Exception e) {
	System.out.println("Image " + "icons/"
	+ iconfile + " not found!");
	System.exit(-1);
	}
	return button;
}

}
