package ca.dyamen;

import Ale46.megajava.MegaHandler;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {

    private static final String GAME_DIRECTORY;
    private static final String SEPARATOR;
    private static final String OS_NAME;

    private static final MegaHandler megaHandler;

    static {
        String os_name = System.getProperty("os.name").toLowerCase();
        String game_directory;

        if (os_name.contains("win")) {
            SEPARATOR = "\\";
            game_directory = System.getenv("APPDATA");
            OS_NAME = "windows";
        } else {
            SEPARATOR = "/";
            game_directory = System.getProperty("user.home");
            OS_NAME = "linux";
        }

        GAME_DIRECTORY = game_directory + SEPARATOR + ".RayCaster" + SEPARATOR;

        megaHandler = new MegaHandler("","");
    }

    private static boolean isGameDirPresent() {
        return new File(GAME_DIRECTORY + "CHECK").exists();
    }

    private static boolean installLWJGL() {
        /* LINUX DOWNLOADS

            https://mega.nz/file/E0UgTT4L#wNMurS87bLh1UDBb853qI2z48hGB1a2lY5ZRN_Letto
            https://mega.nz/file/AtdgkbLa#yA13Cc7b-QeBcx44UE6eoBohePfjNTWiFGZn7l_dbIg
            https://mega.nz/file/ktNU2JjI#dOzK6bMEhJLO4J7XKOJ0ZkEGBBN71dvNsODkeqMEaMo
            https://mega.nz/file/slFSiZzA#KXNYTAgwBi3DtMFr8cO8dOjDz2bdFw2JHNAT4g3JEh0
            https://mega.nz/file/pxEi3DAK#LAo_jZ7duvhRIng7zFH97AcuRZ0A6TJ-mQ9FwRBZRnE
            https://mega.nz/file/hhV02RwA#aEsN14xPxLI03qRN9rfYLRJo5ShzE1aBBfSE8TbCXwY

         */

        String[] linux_files = {
                "https://mega.nz/file/#E0UgTT4L#wNMurS87bLh1UDBb853qI2z48hGB1a2lY5ZRN_Letto",
                "https://mega.nz/file/#AtdgkbLa#yA13Cc7b-QeBcx44UE6eoBohePfjNTWiFGZn7l_dbIg",
                "https://mega.nz/file/#ktNU2JjI#dOzK6bMEhJLO4J7XKOJ0ZkEGBBN71dvNsODkeqMEaMo",
                "https://mega.nz/file/#slFSiZzA#KXNYTAgwBi3DtMFr8cO8dOjDz2bdFw2JHNAT4g3JEh0",
                "https://mega.nz/file/#pxEi3DAK#LAo_jZ7duvhRIng7zFH97AcuRZ0A6TJ-mQ9FwRBZRnE",
                "https://mega.nz/file/#hhV02RwA#aEsN14xPxLI03qRN9rfYLRJo5ShzE1aBBfSE8TbCXwY",
        };

        /* WINDOWS DOWNLOADS

            https://mega.nz/file/8sFSFbhI#_BRgb2cofLVtOTbPbBmYdnDB1_Rlkd_vRZXpXFUGUEg
            https://mega.nz/file/QpEChL7Z#oB513nFemIOFs5ZsbSEV4MihGRl158XlUOq2vltfWZ4
            https://mega.nz/file/doFkBDRQ#1yT1VT0IYoxucSzwHKrTs9pYyY2SbMPARh3kCL_J8Hk
            https://mega.nz/file/40d0GBYa#aSJvs0wNFNkJfDjt5YtF95u6z1jpzPKBk7S69j_jqv8
            https://mega.nz/file/55UCAZLL#tSIv6rJzRoSa-AhKOe2F-jNz2XbPbz2mNMB4pmbGF04
            https://mega.nz/file/V9EyjLqR#Fbkcxwv5qyjDJttiJGoru_RXbLIA4qredaIyrEJYMM0
            https://mega.nz/file/U4NmXZjK#no31_ubF5NoucnECc029SzgANYM_gIeVnpKtB0s4WHw
            https://mega.nz/file/ApcUwJbJ#8ifqxFi2wv7VZa6NLmW0057VpGzW55SSye9ZDdkU2F8

         */

        String[] windows_files = {
            "https://mega.nz/file/#8sFSFbhI#_BRgb2cofLVtOTbPbBmYdnDB1_Rlkd_vRZXpXFUGUEg",
            "https://mega.nz/file/#QpEChL7Z#oB513nFemIOFs5ZsbSEV4MihGRl158XlUOq2vltfWZ4",
            "https://mega.nz/file/#doFkBDRQ#1yT1VT0IYoxucSzwHKrTs9pYyY2SbMPARh3kCL_J8Hk",
            "https://mega.nz/file/#40d0GBYa#aSJvs0wNFNkJfDjt5YtF95u6z1jpzPKBk7S69j_jqv8",
            "https://mega.nz/file/#55UCAZLL#tSIv6rJzRoSa-AhKOe2F-jNz2XbPbz2mNMB4pmbGF04",
            "https://mega.nz/file/#V9EyjLqR#Fbkcxwv5qyjDJttiJGoru_RXbLIA4qredaIyrEJYMM0",
            "https://mega.nz/file/#U4NmXZjK#no31_ubF5NoucnECc029SzgANYM_gIeVnpKtB0s4WHw",
            "https://mega.nz/file/#ApcUwJbJ#8ifqxFi2wv7VZa6NLmW0057VpGzW55SSye9ZDdkU2F8",
        };

        String path = GAME_DIRECTORY + "lwjgl" + SEPARATOR + "native" + SEPARATOR + OS_NAME + SEPARATOR;
        new File(path).mkdirs();

        for (String file : (OS_NAME.equals("windows") ? windows_files : linux_files)) {
            try {
                megaHandler.download(file, path);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    private static boolean installAssets() {
        String path = GAME_DIRECTORY + "assets" + SEPARATOR;

        try {
            new File(path + "shaders").mkdirs();
            new File(path + "computeShaders").mkdirs();

            megaHandler.download("https://mega.nz/file/#F5FgzJTA#kgRv6PaddD8uCq3TepEsz_QyJInsV0aXJRxZATxh650", path + "shaders" + SEPARATOR);
            megaHandler.download("https://mega.nz/file/#I4NCHLAb#XVo0M43sMMZBNKqWheANK5K-DC9GZwNSLYTVNOtN3Zw", path + "shaders" + SEPARATOR);
            megaHandler.download("https://mega.nz/file/#kgdUQJrK#d3XnVLGf7cUwAc-B0ts3Uvv85TO1S1eS5SSlIfbiapo", path + "computeShaders" + SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean installBinaries() {
        try {
            megaHandler.download("https://mega.nz/file/#51ECFRKS#bPCojWSVher9aiFYxzTia6Kx4G5OscxTjmrGu_TdQ_Y", GAME_DIRECTORY);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean installGame() {
        boolean success = true;

        success &= installLWJGL();
        success &= installAssets();
        success &= installBinaries();

        if (success) {
            try {
                new File(GAME_DIRECTORY + "CHECK").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    public static void main(String[] args)  {
        if (!isGameDirPresent()) {
            if (installGame()) {

            } else {
                System.err.println("Could not install game!");
                return;
            }
        }

        String[] commands = {
                "java",
                "-Djava.library.path=" + GAME_DIRECTORY + "lwjgl" + SEPARATOR + "native" + SEPARATOR + OS_NAME + SEPARATOR + "",
                "-jar",
                GAME_DIRECTORY + "RayCaster.jar",
        };

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        processBuilder.redirectErrorStream(true);

        try {
            Process p = processBuilder.start();
            new Thread(new Runnable() {
                public void run() {
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;

                    try {
                        while ((line = input.readLine()) != null)
                            System.out.println(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
