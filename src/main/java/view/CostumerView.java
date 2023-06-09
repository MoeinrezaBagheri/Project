package view;

import controller.AdminController;
import controller.CostumerController;
import model.DiscountCode;
import model.exception.WrongDiscountCode;
import model.product.Product;
import model.user.PurchaseList;
import model.user.Request;
import model.user.RequestType;
import model.user.user_types.Admin;
import model.user.user_types.Costumer;

import java.util.Scanner;

public class CostumerView {
    static Scanner sc = new Scanner(System.in);

    public static void costumerView(Costumer costumer) {
        if (costumer == null) {
            System.out.println("admin rejected your signup!\n");
        } else {
            Boolean bool = true;
            while (bool) {
                System.out.println("welcome to online shop " + "\n1-Edit your profile\n2-view products\n3-view purchase list\n4-raise credit\n" +
                        "5-view purchase history\n6-view discount Codes\n7-exit");
                int answer = sc.nextInt();
                sc.nextLine();
                switch (answer) {
                    case 1:
                        Boolean editStatus = true;
                        String result;
                        while (editStatus) {
                            System.out.println("please enter your email, phone and pass :\n");
                            String email = sc.nextLine();
                            String phone = sc.nextLine();
                            String pass = sc.nextLine();
                            result = CostumerController.edit(phone, email, pass, costumer);
                            System.out.println(result);
                            if (result.equals("edit done!")) {
                                editStatus = false;
                            }
                        }
                        break;
                    case 2:
                        ProductsView.productsView(costumer);
                        break;
                    case 3:
                        Boolean bool1 = true;
                        while (bool1) {
                            for (Product a : costumer.getToBuyList()) {
                                System.out.println(a.toString() + "\n");
                            }
                            System.out.println("1-remove product\n2-buy\n3-exit");
                            int answer5 = sc.nextInt();
                            sc.nextLine();
                            switch (answer5) {
                                case 1:
                                    System.out.println("enter the Id of product that you want to remove :\n");
                                    String answer6 = sc.nextLine();
                                    CostumerController.removeProduct(costumer, answer6);
                                    break;
                                case 2:
                                    System.out.println("your bill is : "+CostumerController.calculatePay(costumer));
                                    long price = 0;
                                        System.out.println("do yo have any discount code ?\nif you dont have any" +
                                                " discount code please enter 0 and if you have" +
                                                " enter 1");
                                        int a = sc.nextInt();
                                        if (a == 0){
                                            price = CostumerController.calculatePay(costumer);
                                        }
                                        else if (a == 1){
                                            price = CostumerController.calculatePay(costumer);
                                            System.out.println("how many codes you want to use?");
                                            int b = sc.nextInt();
                                            sc.nextLine();
                                            System.out.println("enter the codes!");
                                            for (int i = 0;i<b;i++){
                                                String code = sc.nextLine();
                                                try {
                                                    price = CostumerController.addDiscount(code, price, costumer);
                                                    System.out.println("price : "+price);
                                                }catch (WrongDiscountCode discountCode ){
                                                    System.out.println("Wrong Discount Code!");
                                                }
                                            }
                                        }
                                        else
                                            System.out.println("error!");
                                    String l = CostumerController.buy(costumer,price);
                                    System.out.println(l);
                                    break;
                                case 3:
                                    bool1 = false;
                                    break;
                            }
                        }
                        break;
                    case 4:
                        System.out.println("your credit now : " + costumer.getCredit() + "\n");
                        String creditNumber;
                        String cvv2;
                        String pass;
                        Boolean bool4 = true;
                        while (bool4) {
                            System.out.println("please enter creditNumber , cvv2 and pass :\n");
                            creditNumber = sc.nextLine();
                            cvv2 = sc.nextLine();
                            pass = sc.nextLine();
                            System.out.println(CostumerController.credit(creditNumber, cvv2, pass));
                            if (CostumerController.credit(creditNumber, cvv2, pass).equals("valid data!")) {
                                bool4 = false;
                            }
                        }
                        System.out.println("please enter the credit that you want to raise :\n");
                        long credit = sc.nextLong();
                        sc.nextLine();
                        Request request = new Request(RequestType.RaiseCredit, costumer);
                        request.setCredit(credit);
                        Admin.getRequests().add(request);
                        break;
                    case 5:
                        for (PurchaseList a : costumer.getHistoryBuyList()) {
                            System.out.println(a.toString());
                            for (Product b : a.getPurchasedProducts()) {
                                System.out.println(b.toString() + "\n");
                            }
                        }
                        Boolean bool2 = true;
                        while (bool2) {
                            System.out.println("1-score\n2-exit");
                            int answer7 = sc.nextInt();
                            sc.nextLine();
                            switch (answer7) {
                                case 1:
                                    System.out.println("enter the ID of product that you want to score :\n");
                                    String answer9 = sc.nextLine();
                                    System.out.println("enter the score :\n");
                                    int answer10 = sc.nextInt();
                                    sc.nextLine();
                                    AdminController.score(answer10, answer9);
                                    break;
                                case 2:
                                    bool2 = false;
                                    break;
                            }

                        }
                        break;
                    case 6:
                        if (costumer.getDiscountCodes().size() == 0){
                            System.out.println("No Discount Codes!");
                        }
                        else {
                            for (DiscountCode a : costumer.getDiscountCodes()) {
                                System.out.println(a.getDiscountCode());
                            }
                        }
                        break;
                    case 7:
                        bool = false;
                        break;
                }
            }
        }
    }
}