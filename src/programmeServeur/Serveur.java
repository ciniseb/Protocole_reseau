package programmeServeur;

import java.io.IOException;
import java.util.Scanner;

public class Serveur
{

    public static void main(String[] args) throws IOException
    {
        new ServeurThread().start();


        /*boolean commencer = true;

        while( commencer == true)
        {
            Scanner keyboard = new Scanner(System.in);
            int myint = keyboard.nextInt();

            if(myint == 'f')
            {
                new ServeurThread().stop();
                commencer = false;
            }
        }*/
    }
}