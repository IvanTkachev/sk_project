package com.ivantk.skproj.javaFX.product;

import com.ivantk.skproj.entities.Product;
import com.ivantk.skproj.javaFX.view.MainController;
import com.ivantk.skproj.services.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.RemoteException;

/**
 *  Class that processes requests from <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html">TableView</a> of {@link Product}'s coming from the javafx interface.
 *
 *  @author Ivan Tkachev
 */
public class ProductController {
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TableView.html">TableView</a> of products
     */
    private TableView<Product> productTableView;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html">TextField</a> for update product name
     */
    public TextField updateName;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html">TextField</a> for update product count
     */
    public TextField updateCount;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html">TextField</a> for product name
     */
    public TextField name;
    /**
     * <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html">TextField</a> for product count
     */
    public TextField count;

    private Stage dialogStage;
    private ProductService productService;

    /**
     *  Method that add {@link Product} to database using data that the user entered into the form: name and count.
     */
    @FXML
    public void createProduct() {
        if (isInputValid()) {
            Product product = new Product(name.getText(), Integer.parseInt(count.getText()));
            try {
                if (productService.findProduct(product.getName(), MainController.nameStore) == null) {
                    productService.addProduct(product, MainController.nameStore);
                    MainController.products.add(productService.findProduct(product.getName(), MainController.nameStore));
                    dialogStage.close();
                } else {
                    errorMessage("Product " + product.getName() + " already exist");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     *  Method that update {@link Product} from database using data that the user entered into the form: name and count.
     */
    @FXML
    public void updateProduct() {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (isInputValid()) {
            Product createdProduct = new Product(updateName.getText(), Integer.parseInt(updateCount.getText()));
            try {
                if ((productService.findProduct(product.getName(), MainController.nameStore) != null)) {
                    productService.updateProduct(product, createdProduct, MainController.nameStore, MainController.nameStore);
                    MainController.products.remove(product);
                    MainController.products.add(productService.findProduct(createdProduct.getName(), MainController.nameStore));
                    dialogStage.close();
                }
                else{
                    errorMessage("Something wrong. Try again");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *  Method that delete {@link Product} from database the client has selected.
     */
    public void deleteProduct() {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            try {
                productService.deleteProduct(product.getName(), MainController.nameStore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            MainController.products.remove(product);
            dialogStage.close();
        } else {
            errorMessage("You can't delete this product");
        }
    }

    public void cancelAction() {
        dialogStage.close();
    }

    @FXML
    private void initialize() {
        try {
            Context context = new InitialContext();
            productService = (ProductService) context.lookup("rmi://localhost/database");
        }catch (Exception e){
            e.printStackTrace();
        }
        if (updateName != null) {
                updateName.setText(MainController.productForUpdate.getName());
                updateCount.setText(String.valueOf(MainController.productForUpdate.getCount()));

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
