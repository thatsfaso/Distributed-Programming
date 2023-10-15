import java.rmi. *;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;
public class HelloImpl extends UnicastRemoteObject implements Hello {
    private static final long serialVersionUID = -4469091140865645865L;
    static Logger logger= Logger.getLogger("global");
    public HelloImpl()throws RemoteException { //vuoto
    }
    public String dimmiQualcosa(String who)
            throws RemoteException {
        logger.info("Sto salutando"+who);
        return "Ciao!"; }

    public static void main(String args[]) { System.setSecurityManager(new RMISecurityManager());
        try{
            logger.info("Creo l’oggetto remoto...");
            HelloImpl obj = new HelloImpl();
            logger.info("...ne effettuo il rebind...");
            Naming.rebind("HelloServer", obj);
            logger.info("...Pronto!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }//end main
}//end classe HelloImpl
