package com.ivantk.skproj.javaFX.view;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.entities.Store;
import com.ivantk.skproj.javaFX.product.ProductController;
import com.ivantk.skproj.javaFX.store.StoreController;
import com.ivantk.skproj.services.ProductService;
import com.ivantk.skproj.services.StoreService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.rmi.RemoteException;

public class MainController {

    public TableColumn<Product, Integer> idColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, Integer> countColumn;
    public TableView<Product> productTableView;
    public static ObservableList<Product> products = FXCollections.observableArrayList();

    public TableView<Store> storeTableView;
    public TableColumn<Store, String> storeColumn;
    public static ObservableList<Store> stores = FXCollections.observableArrayList();
    public Label storeLabel;
    public MenuItem deleteItem;
    public MenuItem updateItem;

    private ProductService productService;
    private StoreService storeService;

    public static String nameStore = "RootStore";
    public static Product productForUpdate;


    //PRODUCT ACTIONS
    public void showAddDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("newProduct.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showUpdateDialog(ActionEvent actionEvent) {
        productForUpdate = productTableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("updateProduct.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProductController controller = loader.getController();
            controller.setProductTableView(productTableView);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showDeleteDialog(ActionEvent actionEvent) {
        productForUpdate = productTableView.getSelectionModel().getSelectedItem();
        if(productForUpdate == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There is no product. Try again");
            alert.show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("deleteDialog.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Delete product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProductTableView(productTableView);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    //STORE ACTIONS
    public void showAddStoreDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("newStore.fxml"));
            AnchorPane page = (AnchorPane)loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add store");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            StoreController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showUpdateStoreDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("updateStore.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update store");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            StoreController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStoreTableView(storeTableView);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void showDeleteStoreDialog(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("deleteStoreDialog.fxml"));
            AnchorPane page = (AnchorPane)loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Delete store");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainFX.rootStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            StoreController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStoreTableView(storeTableView);
            dialogStage.showAndWait();
            products.clear();
            products.addAll(productService.getAllProducts(nameStore));

            storeLabel.setText("Current store: " + nameStore);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void changeStore(ActionEvent actionEvent) {
        Store store = storeTableView.getSelectionModel().getSelectedItem();
        if(store != null){
            nameStore = store.getName();
            products.clear();
            try {
                products.addAll(productService.getAllProducts(nameStore));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            storeLabel.setText("Current store: " + nameStore);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Something wrong");
            alert.show();
        }
    }

    @FXML
    private void initialize() {
        try {
            Context context = new InitialContext();
            productService = (ProductService) context.lookup("rmi://localhost/database");
            storeService = (StoreService) context.lookup("rmi://localhost/database1");

            products.addAll(productService.getAllProducts(nameStore));
            productTableView.setEditable(true);
            stores.addAll(storeService.getAllStores());

            updateItem.disableProperty().bind(Bindings.isEmpty(productTableView.getSelectionModel().getSelectedItems()));
            deleteItem.disableProperty().bind(Bindings.isEmpty(productTableView.getSelectionModel().getSelectedItems()));

            // устанавливаем тип и значение которое должно хранится в колонке
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
            storeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            // заполняем таблицу данными
            productTableView.setItems(products);
            storeTableView.setItems(stores);
            storeLabel.setText("Current store: " + nameStore);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}