import java.io.*;
import java.net.*;
import java.util.*;


public class QuoteClient
{
    public static void main(String[] args) throws IOException
    {
        String premiereQuote = "true";
        while (true) {
            if (args.length != 1)
            {
                System.out.println("Usage: java QuoteClient <hostname>");
                return;
            }

            // get a datagram socket
            DatagramSocket socket = new DatagramSocket();

            // send request
            byte[] buf = new byte[1600];
            InetAddress address = InetAddress.getByName(args[0]);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 25001);
            socket.send(packet);

            // get response
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // display response
            String received = new String(packet.getData(), 0, packet.getLength());
            if (premiereQuote.equals("true"))
            {
                premiereQuote = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Titre: " + premiereQuote);
            }
            else
            {
                if(received.equals("End of file, Goodbye!"))
                {
                    System.out.println("Quote of the Moment: " + received);
                    return;
                }
                else
                {
                    System.out.println("Quote of the Moment: " + received);
                }

            }

            socket.close();
        }
    }
}
