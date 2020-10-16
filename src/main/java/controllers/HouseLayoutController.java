package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;

import com.google.gson.Gson;
import javafx.scene.control.Label;
import models.RoomModel;
import models.HouseRoomsModel;

import java.util.Scanner;
public class HouseLayoutController {

    @FXML private JFXButton continueButton;
    private String pathToFile = null;
    @FXML private Label pathToFileLabel;
    private File chosenFile;
    private Main mainController;

    public void setMainController(Main mainController) {
        this.mainController = mainController;
    }



    public void onUploadClick(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload House Layout File");
        chosenFile = fileChooser.showOpenDialog(null);
        if(chosenFile != null) {
            pathToFile = chosenFile.getAbsolutePath();
            pathToFileLabel.setText(pathToFile);
            continueButton.setDisable(false);
        }
    }

    public String readFromFile(String url){
        String jsonString="";
        try{
            File file = new File(url);
            Scanner readFile = new Scanner(file);
            while(readFile.hasNextLine()){
                jsonString +=readFile.nextLine()+"\r\n";
            }
            readFile.close();
            return jsonString;
        } catch (Exception e) {
            System.out.println("The file can not be found.");
        }
        return null;
    }

    public RoomModel[] extractFromJson(String jsonText){
        RoomModel[] arrayRoomModel = new Gson().fromJson(jsonText, RoomModel[].class);
        return arrayRoomModel;
    }

    public void onContinueClick(MouseEvent mouseEvent) {
        String jsonString = readFromFile(pathToFile);
        RoomModel[] allRoomModels = extractFromJson(jsonString);
        HouseRoomsModel.setAllRooms(allRoomModels);
        mainController.CloseWindow();
        try{
            mainController.setSimulatorParameterWindow();
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(allRoomModels.length);
    }


}