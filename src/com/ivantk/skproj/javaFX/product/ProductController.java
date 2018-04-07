package com.ivantk.skproj.javaFX.product;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.javaFX.view.MainController;
import com.ivantk.skproj.stax.XMLService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.RemoteException;

public class ProductController {

    private TableView<Product> productTableView;
    public TextField updateName;
    public TextField updateCount;
    private XMLService xmlService;
    public TextField name;
    public TextField count;
    private Stage dialogStage;

    @FXML
    public void createProduct() {
        if (isInputValid()) {

            int id = 0;
            for(int i = 0; i < MainController.products.size(); i++){
                if(MainController.products.get(i).getId() > id)
                    id = MainController.products.get(i).getId();
            }
            id++;
            Product product = new Product(id, name.getText(), Integer.parseInt(count.getText()));
            try {
                if (xmlService.findProduct(product.getName(), MainController.nameStore) == null) {
                    xmlService.addProduct(product, MainController.nameStore);
                    MainController.products.add(product);
                    dialogStage.close();
                } else {
                    errorMessage("Product " + product.getName() + " already exist");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    }

    public void deleteProduct(ActionEvent actionEvent) {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            try {
                xmlService.deleteProduct(product.getName(), MainController.nameStore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MainController.products.remove(product);
            dialogStage.close();
        } else {
            errorMessage("You can't delete this product");
        }
    }

    public void cancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    private void initialize() {
        try {
            Context context = new InitialContext();
            xmlService = (XMLService) context.lookup("rmi://localhost/stax");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProductTableView(TableView<Product> productTableView) {
        this.productTableView = productTableView;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (updateName == null) {
            if (name.getText() == null || name.getText().length() == 0) {
                errorMessage += "No valid name!\n";
            }
            errorMessage = validate(errorMessage, count.getText());
        } else {
            if (updateName.getText() == null || updateName.getText().length() == 0) {
                errorMessage += "No valid name!\n";
            }
            errorMessage = validate(errorMessage, updateCount.getText());
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            errorMessage(errorMessage);
            return false;
        }
    }

    private void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private String validate(String errorMessage, String text) {
        try {
            int countValue = Integer.parseInt(text);
            if (countValue <= 0) {
                errorMessage += "No valid count!\n";
            }
        } catch (NumberFormatException e) {
            errorMessage += "No valid count!\n";
        }
        return errorMessage;
    }

}
