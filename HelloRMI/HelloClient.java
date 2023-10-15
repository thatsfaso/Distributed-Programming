import java.rmi.*;
import java.util.logging.Logger;
public class HelloClient {
    static Logger logger= Logger.getLogger("global");
    public static void main(String args[]) {
        try{
        logger.info("Sto cercando l’oggetto remoto...");
        Hello obj = (Hello)
                Naming.lookup("rmi://localhost/HelloServer");
        logger.info("...Trovato! Invoco metodo...");
        String risultato = obj.dimmiQualcosa("Iliano");
        System.out.println("Ricevuto:"+ risultato);
    }catch(Exception e) {
        e.printStackTrace();
    }
    }//fine main
}//fine classe HelloClient
