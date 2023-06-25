import java.io.IOException;
import java.util.Scanner;

public class QuoteServer
{

    public static void main(String[] args) throws IOException
    {

        new QuoteServerThread().start();
        boolean commencer = true;

        while( commencer == true)
        {
            Scanner keyboard = new Scanner(System.in);
            int myint = keyboard.nextInt();

            if(myint == 'f')
            {
                new QuoteServerThread().stop();
                commencer = false;
            }
        }

    }
}