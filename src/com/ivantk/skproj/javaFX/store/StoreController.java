package com.ivantk.skproj.javaFX.store;

import com.ivantk.skproj.entities.Store;
import com.ivantk.skproj.javaFX.view.MainController;
import com.ivantk.skproj.services.StoreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.RemoteException;

public class StoreController {
    private TableView<Store> storeTableView;
    public TextField storeField;
    public TextField name;
    private StoreService storeService;
    private Stage dialogStage;

    @FXML
    private void initialize() {
        try {
            Context context = new InitialContext();
            storeService = (StoreService) context.lookup("rmi://localhost/database1");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createStore(ActionEvent actionEvent) {
        if(name.getText() != null){
            try {
                if(storeService.findStore(name.getText()) == null){
                    storeService.addStore(name.getText());
                    MainController.stores.add(storeService.findStore(name.getText()));
                    dialogStage.close();
                }
                else
                    errorMessage("This store already exist. Try again");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else errorMessage("Incorrect data. Try again");
    }

    public void deleteStore(ActionEvent actionEvent) {
        Store store = storeTableView.getSelectionModel().getSelectedItem();
        if (store != null && !"RootStore".equals(store.getName())) {
            try {
                storeService.deleteStore(store.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MainController.stores.remove(store);
            MainController.nameStore = "RootStore";
            dialogStage.close();
        } else {
            errorMessage("You can't delete this product");
        }
    }

    public void updateStore(ActionEvent actionEvent) {
        Store store = storeTableView.getSelectionModel().getSelectedItem();
        if(store != null && storeField.getText() != null && !"RootStore".equals(storeField.getText())){
            try {
                if(storeService.findStore(storeField.getText()) == null){
                    storeService.updateStore(store.getName(), storeField.getText());
                    MainController.stores.remove(store);
                    MainController.stores.add(storeService.findStore(storeField.getText()));
                    dialogStage.close();
                }
                else{
                    errorMessage("There is no " + storeField.getText() + " at available's stores");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            errorMessage("Incorrect data or RootStore updated. Try again");
        }

    }

    public void cancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setStoreTableView(TableView<Store> storeTableView) {
        this.storeTableView = storeTableView;
    }

    private void errorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

}
