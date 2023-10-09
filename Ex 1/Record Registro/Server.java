import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;
public class Server {
    static Logger logger = Logger.getLogger("global");
    public static void main(String[] args) {

        //si salva il registro sulle hashmap istanziate
        HashMap<String, RecordRegistro> hash = new HashMap<String, RecordRegistro>();


        Socket socket = null;
        System.out.println ("In attesa...");
        try{
            //istanzio socket di connessione
            ServerSocket serverSocket = new ServerSocket(7000);

            while(true) {
                socket = serverSocket.accept();   //attesa di connessione
                ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());

                //prendo il registro dallo stream
                RecordRegistro record = (RecordRegistro) inStream.readObject();
                if(record.getIndirizzo()!=null) {//scrittura
                    hash.put(record.getNome(), record);
                }else{//ricerca
                    RecordRegistro res = hash.get(record.getNome());
                    ObjectOutputStream outStream =new ObjectOutputStream (socket.getOutputStream());
                    outStream.writeObject(res);
                    outStream.flush();
                }//fine else
                socket.close();
            }//fine while
        }catch(EOFException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }catch(Throwable t) {
            logger.severe(t.getMessage());
            t.printStackTrace();
        }
        finally{//chiusura del socket
            try{ socket.close();
            }catch(IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}