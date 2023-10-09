import java.io.*;
import java.net.*;
import java.util.logging.Logger;
public class Client {
    static Logger logger = Logger.getLogger("global");
    public static void main(String[] args) {
        String host = "172.19.130.142";     //indirizzo IP del client
        String cmd;   //stringa che sarva uno dei comandi (inserisci, cerca)

        //prendo lo stream di input collegato alla tastiera
        in = new BufferedReader(new InputStreamReader(System.in));

        //lista di comandi:
        //quit -> esci
        //inserisci -> inserire un record (nome, indirizzo)
        //cerca -> cerca un record tramite il nome

        try{
            while(!(cmd = ask(">>>")).equals("quit")) {    //finchè il comando non è quit...

                //se il comando è inserisci...
                if(cmd.equals ("inserisci")) {
                    System.out.println ("Inserire i dati.");
                    String nome = ask("Nome:");         //sul prompt inserisco il nome e lo salvo
                    String indirizzo = ask ("Indirizzo:");    //stessa cosa per l'indirizzo

                    //istanzio la classe registro
                    RecordRegistro r = new RecordRegistro(nome, indirizzo);

                    //creo la socket lato client
                    Socket socket = new Socket(host, 7000);

                    //prelevo lostream di output
                    ObjectOutputStream sock_out = new ObjectOutputStream(socket.getOutputStream());

                    sock_out.writeObject(r); //scrivo il registro sullo stream che viene prelevato dal server
                    sock_out.flush();   //pulisco lo stream
                    socket.close();     // chiudo socket

                 //se il comando è cerca...
                }else if(cmd.equals("cerca")){

                    System.out.println ("Inserire il nome per la ricerca.");
                    String nome = ask("Nome:");   //inserimento e prelevamento del nome da ricercare

                    RecordRegistro r = new RecordRegistro(nome,null);   //istanzio il record
                    Socket socket = new Socket (host, 7000);    //apro il socket

                    //prelevo gli stream di I/O
                    ObjectOutputStream sock_out = new ObjectOutputStream(socket.getOutputStream());
                    sock_out.writeObject(r);    //scrittura del record su stream di output
                    sock_out.flush();
                    ObjectInputStream sock_in = new
                            ObjectInputStream(socket.getInputStream());

                    //prelevo registro dallo stream di input
                    RecordRegistro result = (RecordRegistro)
                            sock_in.readObject();

                    if(result !=null)
                        System.out.println ("Indirizzo:"+ result.getIndirizzo());   //stampa indirizzo
                    else
                        System.out.println ("Record assente");
                    socket.close();
                }else
                    System.out.println (ERRORMSG);     //se il comando non è chiaro si scirve "Cosa?
            }}catch(Throwable t) {
               logger.severe("Lanciata Throwable:"+ t.getMessage());
               t.printStackTrace();
         } System.out.println ("Bye bye");
    }
    //funzione di lettura dalla tastiera
    private static String ask(String prompt) throws IOException {
        System.out.print(prompt+"");
        return(in.readLine());
    }
    static final String ERRORMSG ="Cosa?";
    static BufferedReader in =null;
}
