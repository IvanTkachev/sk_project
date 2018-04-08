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

/**
 *  Class that processes requests coming from the javafx interface.
 *
 *  @author Ivan Tkachev
 */
public class MainController {

    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html">TableColumn</a> of product's id
     */
    public TableColumn<Product, Integer> idColumn;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html">TableColumn</a> of product's name
     */
    public TableColumn<Product, String> nameColumn;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html">TableColumn</a> of product's count
     */
    public TableColumn<Product, Integer> countColumn;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html">TableView</a> of products
     */
    public TableView<Product> productTableView;
    /**
     * list of products
     */
    public static ObservableList<Product> products = FXCollections.observableArrayList();
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html">TableView</a> of stores
     */
    public TableView<Store> storeTableView;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableColumn.html">TableColumn</a> of store's name
     */
    public TableColumn<Store, String> storeColumn;
    /**
     * list of stores
     */
    public static ObservableList<Store> stores = FXCollections.observableArrayList();
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Label.html">label</a> of current store
     */
    public Label storeLabel;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Label.html">item</a> for delete product
     */
    public MenuItem deleteItem;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Label.html">item</a> for update v
     */
    public MenuItem updateItem;

    private ProductService productService;
    private StoreService storeService;

    /**
     * current store
     */
    public static String nameStore = "RootStore";
    /**
     * product for update
     */
    public static Product productForUpdate;


    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on add product in context menu.
     */
    public void showAddDialog() {
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

    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on update product in context menu.
     */
    public void showUpdateDialog() {
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

    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on delete product in context menu.
     */
    public void showDeleteDialog() {
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


    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on add store in context menu.
     */
    public void showAddStoreDialog() {
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

    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on update store in context menu.
     */
    public void showUpdateStoreDialog() {
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

    /**
     *  Method that show <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html">dialog</a> after user click on delete product in context menu.
     */
    public void showDeleteStoreDialog() {
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

    /**
     *  Method that change store after user click on choose store in context menu.
     */
    public void changeStore() {
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

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
            storeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            productTableView.setItems(products);
            storeTableView.setItems(stores);
            storeLabel.setText("Current store: " + nameStore);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}