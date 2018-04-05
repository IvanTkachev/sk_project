//package com.ivantk.skproj.menu;
//
//import com.ivantk.skproj.entities.Product;
//import com.ivantk.skproj.entities.Store;
//import com.ivantk.skproj.services.ProductService;
//import com.ivantk.skproj.services.StoreService;
//import com.ivantk.skproj.services.impl.ProductServiceImpl;
//import com.ivantk.skproj.services.impl.StoreServiceImpl;
//
//import java.util.Scanner;
//
//public class UserMenu {
//
//    private ProductService productService = new ProductServiceImpl();
//    private StoreService storeService = new StoreServiceImpl();
//    private static final String ANSI_RESET = "\u001B[0m";
//    private static final String ANSI_RED = "\u001B[31m";
//    private static final String ANSI_GREEN = "\u001B[32m";
//    private static final String ANSI_BLUE = "\u001B[34m";
//
//    private String storeName = "RootStore";
//
//    public void startMenu() {
//
//        System.out.println(ANSI_BLUE + "IMPORTANT INFORMATION:\n" +
//                "If you want to exit from current menu, " +
//                "write 'exit' for String value or '-1' for Integer value.\n" +
//                "Good luck!" + ANSI_RESET);
//        Scanner in = new Scanner(System.in);
//        while (true) {
//            System.out.println("\nCurrent store: " + storeName);
//            System.out.println("Choose operation:\n" +
//                    "1 - add product\n" +
//                    "2 - delete product\n" +
//                    "3 - update product\n" +
//                    "4 - find product\n" +
//                    "5 - view all products\n" +
//                    "6 - add store\n" +
//                    "7 - delete store\n" +
//                    "8 - update store\n" +
//                    "9 - find store\n" +
//                    "10 - view all stores\n" +
//                    "11 - change store");
//            int operation = 0;
//            while (true) {
//                try {
//                    operation = Integer.parseInt(in.next());
//                } catch (NumberFormatException e) {
//                    System.out.print("Incorrect value. " + e.getMessage() + ".\nInput number: ");
//                    continue;
//                }
//                if (operation > 11 || operation < 1) {
//                    System.out.print("Incorrect value. Input number from 1 to 11: ");
//                    continue;
//                }
//                break;
//            }
//            userOperations(operation);
//        }
//    }
//
//    private void userOperations(int operation) {
//        Scanner in = new Scanner(System.in);
//        switch (operation) {
//            case 1: {
//                System.out.print("Name of product: ");
//                String name = "";
//                while (true) {
//                    name = in.next();
//                    if (name.isEmpty())
//                        continue;
//                    break;
//                }
//                if (name.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                System.out.print("Count of product: ");
//                int count = 0;
//                count = checkCount(in, count);
//                if (count == -1) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                productService.addProduct(new Product(name, count), storeName);
//                break;
//            }
//            case 2: {
//                System.out.print("Name of product: ");
//                String productName = "";
//                productName = checkName(in);
//                if (productName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                productService.deleteProduct(productName, storeName);
//                break;
//            }
//            case 3: {
//                System.out.print("Name of old product: ");
//                String oldName = "";
//                oldName = checkName(in);
//                if (oldName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                System.out.print("Name of new product: ");
//                String newName = "";
//                while (true) {
//                    newName = in.next();
//                    if (newName.isEmpty())
//                        continue;
//                    break;
//                }
//                if (newName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                int newCount = 0;
//                System.out.print("Count of new product: ");
//                newCount = checkCount(in, newCount);
//                if (newCount == -1) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                System.out.print("Name of new store or 'current': ");
//                String newStoreName = "";
//                while (true) {
//                    newStoreName = in.next();
//                    if (newStoreName.isEmpty())
//                        continue;
//                    break;
//                }
//                if (newStoreName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                if (newStoreName.equals("current"))
//                    newStoreName = storeName;
//                productService.updateProduct(new Product(oldName, 1), new Product(newName, newCount), storeName, newStoreName);
//                break;
//            }
//            case 4: {
//                System.out.print("Name of product: ");
//                String name = "";
//                name = checkName(in);
//                if (name.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                Product resultProduct = productService.findProduct(name, storeName);
//                if (resultProduct != null)
//                    System.out.println(ANSI_GREEN + resultProduct.getName() + " - " + resultProduct.getCount() + ANSI_RESET);
//                else System.out.println(ANSI_RED + name + " there is no such product." + ANSI_RESET);
//                break;
//            }
//            case 5: {
//                for (Product product : productService.getAllProducts(storeName)) {
//                    System.out.println(ANSI_BLUE + product.getName() + " - " + product.getCount() + ANSI_RESET);
//                }
//                break;
//            }
//            case 6:
//                System.out.print("Name of store: ");
//                String addName = "";
//                while (true) {
//                    addName = in.next();
//                    if (addName.isEmpty())
//                        continue;
//                    break;
//                }
//                if (addName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                storeService.addStore(addName);
//                break;
//            case 7:
//                System.out.println(ANSI_BLUE + "You can not delete RootStore" + ANSI_RESET);
//                System.out.print("Name of store: ");
//                String deleteStoreName = "";
//                deleteStoreName = checkName(in);
//                if (deleteStoreName.equals("exit") || deleteStoreName.equals("RootStore")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                if (deleteStoreName.equals(storeName))
//                    storeName = "RootStore";
//                storeService.deleteStore(deleteStoreName);
//                break;
//            case 8:
//                System.out.println(ANSI_BLUE + "You can not update RootStore" + ANSI_RESET);
//                System.out.print("Name of old store: ");
//                String oldName = "";
//                oldName = checkName(in);
//                if (oldName.equals("exit") || oldName.equals("RootStore")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                System.out.print("Name of new store: ");
//                String newName = "";
//                while (true) {
//                    newName = in.next();
//                    if (newName.isEmpty())
//                        continue;
//                    break;
//                }
//                if (newName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                if (oldName.equals(storeName))
//                    storeName = newName;
//                storeService.updateStore(oldName, newName);
//                break;
//            case 9:
//                System.out.print("Name of store: ");
//                String findName = "";
//                findName = checkName(in);
//                if (findName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                Store store = storeService.findStore(findName);
//                if (store != null)
//                    System.out.println(ANSI_GREEN + findName + " - exist" + ANSI_RESET);
//                else System.out.println(ANSI_RED + findName + " there is no such store." + ANSI_RESET);
//                break;
//            case 10:
//                for (Store stores : storeService.getAllStores()) {
//                    System.out.println(ANSI_BLUE + stores.getName() + ANSI_RESET);
//                }
//                break;
//            case 11:
//                System.out.print("Name of store: ");
//                String newStoreName = "";
//                newStoreName = checkName(in);
//                if (newStoreName.equals("exit")) {
//                    System.out.println("Exit success");
//                    break;
//                }
//                if (storeService.findStore(newStoreName) != null) {
//                    storeName = newStoreName;
//                    System.out.println(ANSI_GREEN + newStoreName + " - new current store" + ANSI_RESET);
//                } else
//                    System.out.println(ANSI_RED + newStoreName + " - there is no such store" + ANSI_RESET);
//
//                break;
//            default: {
//                break;
//            }
//        }
//    }
//
//    private int checkCount(Scanner in, int count) {
//        while (true) {
//            try {
//                count = Integer.parseInt(in.next());
//            } catch (NumberFormatException e) {
//                System.out.println("Incorrect count. Try again.");
//            }
//            if (count == -1)
//                break;
//            if (count <= 0)
//                continue;
//            break;
//        }
//        return count;
//    }
//
//    private String checkName(Scanner in) {
//        String name;
//        while (true) {
//            name = in.next();
//            if (name.isEmpty())
//                continue;
//            if (name.equals("exit"))
//                break;
//            break;
//        }
//        return name;
//    }
//}
