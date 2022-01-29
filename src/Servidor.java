import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static ServerSocket server;
    private static Socket socket;

    private static DataInputStream entrada;
    private static DataOutputStream saida;

    public static void main (String[] args) {

        try {
            // Criar porta de recepção
            server = new ServerSocket(54321);
            System.out.println("A porta 54321 foi aberta.");

            // Cria um canal de conexão
            socket = server.accept();

            // Criar fluxos (entrada-saída)
            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            // Recebimento do valor de cpf (String)
            String cpf = entrada.readUTF();

            //Processamento do valor
            boolean resultado = false;

            //Verificando o valor
            if (ValidaCpf.isCPF(cpf) == true) {
                resultado = true;
            }

            //Envio dos dados para o cliente
            saida.writeBoolean(resultado);

            socket.close();
        } catch (Exception e) {

        }
    }
}
