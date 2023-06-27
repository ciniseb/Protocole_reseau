package programmeServeur;

import java.io.IOException;
import java.util.Scanner;

public class Serveur
{

    public static void main(String[] args) throws IOException
    {
        new ServeurThread().start();
    }
}