import java.io.*;
import java.net.Socket;

public class Cliente {

    private static Socket socket;
    private static DataInputStream entrada;
    private static DataOutputStream saida;

    public static void main(String[] args) throws IOException {

        try {

            socket = new Socket("127.0.0.1", 54321);

            entrada = new DataInputStream(socket.getInputStream());
            saida = new DataOutputStream(socket.getOutputStream());

            //Recebe alguns dados do usuário
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Informe o número do CPF:");
            String cpf = buff.readLine();

            //Envia estes dados ao server
            saida.writeUTF(cpf);

            //Recebe a resposta do server
            boolean resultado = entrada.readBoolean();

            socket.close();

            //Imprime a resposta do server
            if (resultado) {
                System.out.println("O CPF digitado é válido");
                System.out.printf("%s", ValidaCpf.imprimeCPF(cpf));
            } else {
                System.out.println("O CPF digitado é inválido");
            }

        } catch (Exception e) {

        }
    }
}
