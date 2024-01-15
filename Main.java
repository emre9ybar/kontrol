import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    final static int INDEX = 20;
    private static String[][] books = {
            {"Araba sevdası", " Recaizade mahmut ekrem"},
            {"Ateşten gömlek", "Halide Edib Adıvar"},
            {"Bir ömür nasıl yaşanır ", "İlber ortaylı",},
            {"İnsan geleceğini nasıl kurar", "İlber ortaylı"},
            {"Türkiyenin yakın tarihi", "İlber ortaylı"}};
    static String[][] patrons = new String[INDEX][4];
    static String[][] transactions = new String[INDEX][3];

    static int transcationQuantity = 0;
    //kullanıcı blsigisi gücelleme


    private static String updatePatronInfo(String updateSearchPatronTC,String fullName,String updateTC,String eMail,String password) {
       int userListİndex = -1;
        for (int i = 0; i < patrons.length; i++) {
            String searchPatrons = patrons[i][1];
            userListİndex = i;
            if (searchPatrons.equalsIgnoreCase(updateSearchPatronTC)) {
                System.out.println("Aranılan kişi bulundu.\n");
                break;

            }else {
                System.out.println("Aranılan kişi bulunamadı.");
            }
        }
        patrons[userListİndex][0] = fullName.toLowerCase();
        patrons[userListİndex][1] = updateTC;
        patrons[userListİndex][2] = eMail.replaceAll(" ", "");
        patrons[userListİndex][3] = password;

        return "güncellendi";
           }



    //kitap kontrol
    static int transcationQuantit = 0;
    static int patronQuantity = 0;

    public static String checkOutBook(String fullName, String tc, String eMail, String password, String bookName, String bookISBN) {
        if (patronQuantity < INDEX) {
            patrons[patronQuantity][0] = fullName.toLowerCase();
            patrons[patronQuantity][1] = tc;
            patrons[patronQuantity][2] = eMail.replaceAll(" ", "").toLowerCase();
            patrons[patronQuantity][3] = password;
            patronQuantity++;

            //aranılan obje bulma

            boolean bookkk = false;
            for (String[] book : books) {
                if (book[0].equalsIgnoreCase(bookName.trim())) {
                    System.out.println(book[0] + "  adında bir kitap var. Yazar :" + book[1]);
                    bookkk = true;

                    if (patrons.length > transcationQuantit ){
                        transactions[transcationQuantit][0] = tc;
                        transactions[transcationQuantit][1] = bookISBN;
                        transactions[transcationQuantit][2] = LocalDate.now().toString();
                        transcationQuantit++;
                        System.out.println("Kitap alımı başarılı oldu.");

                        int bookIndex = -1;
                        for (int i = 0; i < books.length; i++) {
                            if (books[i][0].equalsIgnoreCase(bookName)) {
                                bookIndex = i;
                                break;
                            }
                        }
                        if (bookIndex != -1) {
                            String[][] newBooks = new String[books.length - 1][2];
                            int newIndex = 0;
                            for (int i = 0; i < books.length; i++) {
                                if (i != bookIndex) {
                                    for (int k =0;k < books.length;k++){
                                    newBooks[newIndex] = books[i];
                                    }
                                    newIndex++;
                                }
                                books = newBooks;
                            }

                            System.out.println("Liste güncellendi. ");

                        } else {
                            System.out.println("Kişi eklenemdi.");
                        }
                    } else {
                        System.out.println("Dosya boyutu aşıldı.");
                    }
                }
            }
            if (!bookkk) {
                System.out.println("Kütüphanemizde böyle bir kitap bulunmamaktadır. ");
            }
        } else {
            String[][] newwpatrons = new String[INDEX + 1][4];
            for (int i = 0; i < newwpatrons.length; i++) {
                for (int j = 0; j < 4; j++) {
                    newwpatrons[i][j] = patrons[i][j];
                }
            }
            System.out.println("Name added :" + patrons[patronQuantity][0]);
            newwpatrons[patronQuantity][0] = fullName;
            newwpatrons[patronQuantity][1] = tc;
            newwpatrons[patronQuantity][2] = eMail;
            newwpatrons[patronQuantity][3] = password;

        patrons=newwpatrons;
        }
                return "The book purchase was successful.";
    }

    public void checkOutBookTransactions(String history) {

        if (patrons.length > transcationQuantit) {
            transactions[transcationQuantit][0] = patrons[transcationQuantit][0];
            transactions[transcationQuantit][1] = patrons[transcationQuantit][3];
            transactions[transcationQuantit][2] = history;
            transcationQuantit++;
            System.out.println("Book purchase has been successfully recorded");
        } else {
            System.out.println("Adding a book did not work. Try again.");
        }
    }
    //kullanıcı silme

    public static void userToDeleted(String customerToDelete) {
        int customerIndex = -1;
        for (int i = 0; i < patrons.length; i++) {
            String customerTc = patrons[i][1];
            customerIndex = i;
            if (customerTc.equals(customerToDelete)) {
                System.out.println("The customer has been deleted successfully. TR ID:" + customerToDelete);

                String[][] newPatrons = new String[patrons.length - 1][4];
                for (int h = 0; h < customerIndex; h++) {
                    for (int j = 0; j < newPatrons[h].length; j++) {
                        newPatrons[h][j] = patrons[i][j];
                    }
                }
                for (int h = customerIndex + 1; h < patrons.length; h++) {
                    for (int j = 0; j < newPatrons[h - 1].length; j++) {
                        newPatrons[h - 1][j] = patrons[i][j];
                    }
                }
                patrons = newPatrons;
            }
        }
    }

    // kitap dönüş tarihi
    private static String checkBookReturnDeadline(String bookISBN) {

        for (int i = 0; i < patrons.length;i++) {
            String ISBN = patrons[i][1];
            if (ISBN != null) {
                if (ISBN.equalsIgnoreCase(bookISBN)) {
                    System.out.println(bookISBN + " ISBN numarasına sahip kitap bulundu.");

                    LocalDate bugun = LocalDate.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                    System.out.println("Kitabın ödünç alındığı tarih      : " + format.format(bugun).toUpperCase());
                    LocalDate teslimTarihi = bugun.plusDays(10);
                    System.out.println("Kitabın getirilmesi gereken tarih : " + format.format(teslimTarihi));

                    Scanner scan = new Scanner(System.in);
                        System.out.print("Kitabın geldiği tarih (gg.aa.yyyy biçimini girin): ");
                        String kitapGelisTarihiStr = scan.next();
                        LocalDate kitapGelisTarihi = LocalDate.parse(kitapGelisTarihiStr, format);

                        if (kitapGelisTarihi.isAfter(teslimTarihi)) {
                            System.out.println("Kitap beklenenden geç geldi!");
                            System.out.println("Kullanıcının 1 ay süreyle kitap ödünç alması yasaktır.");

                            break;
                        } else {
                            System.out.println("Kitap son teslim tarihinden önce teslim edildi.");
                            System.out.println("Kullanıcı kitap ödünç alabilir.");
                        }
                } else {
                    System.out.println("Bu T.C'ye ait kayıt bulunamadı.");
                    break;
                }
            } else {
                break;
            }

        }
        return "Kullanıcı kitap ödünç alabilir. ";

    }












    private static String liste( ){
        for (int i = 0;i<patrons.length;i++){
            for (int j =0;j < patrons[i].length ;j++){
                if (patrons[i][j]!=null) {
                    System.out.println("isim soyisim :" + patrons[i][0]);
                    System.out.println("tc           :" + patrons[i][1]);
                    System.out.println("e-mail        :" + patrons[i][2]);
                    System.out.println("password       :" + patrons[i][3]);

                    return "isim soyisim :" + patrons[i][0]+
                            "\ntc           :" + patrons[i][1] +
                            "\ne-mail        :" + patrons[i][2]+
                            "\npassword       :" + patrons[i][3];




                }
            }
            }
       return " Sistem hatası ";
    }

    private static String userdeleteddd(String patronsTC) {
        int bookIndex = -1;

        for (int i = 0; i < patrons.length; i++) {
            if (patrons[i][1].equalsIgnoreCase(patronsTC)) {
                bookIndex = i;
                break;
            }
        }
        if (bookIndex != -1) {
            String[][] newpatrons = new String[patrons.length - 1][2];
            int newIndex = 0;
            for (int i = 0; i < patrons.length; i++) {
                if (bookIndex != i) {
                    for (int k = 0; k < 2; k++) {
                        if (patrons[i][k] != null) {
                            newpatrons[newIndex] = patrons[i];
                        ///
                        }
                    }
                    newIndex++;
                }
            }
            patrons = newpatrons;


            }

      return "Liste güncellendi kullanıcı silindi";

        }
        public static void genelBilgiler(){

            System.out.println("1-Kitap kaydı ve kontrolü    :Sizden istenilen bilgiler : isim soyisim,kitabın ismi,T.C kimlik numarası ve kitap ISBN'dir.  :");
            System.out.println("2-Kitap tarihi ve  kontrolü  :Sizden istenilen bilgiler: Kitap ISBN numarası. ");
            System.out.println("3-Kullanıcı silinmesi        :Sizden istenilen bilgiler: Kullanıcının TC numarsını girmeniz gerekiyor.");
            System.out.println("4-Kullanıcı güncelleme       :Sizden istenilen bilgiler :Kullanıcının TC numarasını girerek  kullanıcıyı bularak yeni isim ve soyisim güncellemesi yapılmaktadır.");
            System.out.println("6-Çıkış .\n");

        }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Kütüphanemize Hoş Geldiniz. ");
        System.out.println("Yapmak istediğinizi seçeneklerden seçebilirsiniz.\n");
        String a ="1-Kitap kaydı ve kontrolü.   :";
        String b ="2-Kitap tarihi ve  kontrolü  : ";
        String c ="3-Kullanıcı silinmesi        ::";
        String d ="4-Kullanıcı güncelleme       :";
        String e ="5-Genel bilgiler.";


        while (true) {
            System.out.println(a);
            System.out.println(b);
            System.out.println(c);
            System.out.println(d);
            System.out.println(e);
            System.out.print("       Seçim yapınız           :");

            int seçim = scan.nextInt();
            scan.nextLine();


            switch (seçim) {
                case 1:
                    System.out.print("Kitap ismini giriniz :");
                    String bookName = scan.nextLine();
                    System.out.print("İsim soyisim         :");
                    String isimsoy = scan.nextLine().toLowerCase();
                    System.out.print("T.C                  :");
                    String TC = scan.nextLine().toLowerCase();
                    System.out.print("e-MAİL giriniz       :");
                    String patronEmail = scan.nextLine().toLowerCase();
                    System.out.print("Password             :");
                    String patronPassword = scan.nextLine().toLowerCase();
                    System.out.print("Kitap ISBN'i giriniz :");
                    String bookISBN = scan.nextLine();
                    checkOutBook(isimsoy, TC, patronEmail, patronPassword, bookName, bookISBN);
                    break;
                case 2:
                    System.out.print("kitap ısbn            :");
                    String ısbn = scan.nextLine().toLowerCase();
                    checkBookReturnDeadline(ısbn);
                    break;
                case 3:
                    System.out.print("patrons TC             :");
                    String name = scan.nextLine();
                    userdeleteddd(name);
                    break;

                case 4:
                        System.out.print("Aranılan T.C               : ");
                        String TCC=scan.nextLine();
                        for (int i = 0;i<patrons.length;i++) {
                              for (int j = 0; j < patrons[i].length; j++) {
                                  if (patrons[i][j] != null) {
                                      System.out.println("isim soyisim :" + patrons[i][0]);
                                      System.out.println("tc           :" + patrons[i][1]);
                                      System.out.println("e-mail        :" + patrons[i][2]);
                                      System.out.println("password       :" + patrons[i][3]);
                                      break;
                                  }
                              }
                          }
                        System.out.print("Yeni isim soyisim         : ");
                        String yeniIsimSoyisim = scan.nextLine().toLowerCase();
                        System.out.print("Yeni T.C                  : ");
                        String yeniTC = scan.nextLine().toLowerCase();
                        System.out.print("Yeni e-MAİL               : ");
                        String yeniEmail = scan.nextLine().toLowerCase();
                        System.out.print("Yeni şifre                : ");
                        String yeniSifre = scan.nextLine().toLowerCase();
                        updatePatronInfo(TCC, yeniIsimSoyisim, yeniTC, yeniEmail,yeniSifre);
                        break;

                        case 5:
                            genelBilgiler();

                            break;
                            /* for (int i = 0;i<patrons.length;i++){
                                for (int j =0;j < patrons[i].length;j++){
                                    if (patrons[i][j]!=null){
                                        System.out.println("isim soyisim :"+patrons[i][0]);
                                        System.out.println("tc           :"+patrons[i][1]);
                                        System.out.println("e-mail        :"+patrons[i][2]);
                                        System.out.println("password       :"+patrons[i][3]);
                                        break;
                                    }
                                }*/


                            case 6:
                                System.out.println("Çıkış yapılıyor..");
                                System.exit(1);break;
                                }







            }
        }
    }


