import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;

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

            //Verificando o valor e enviando os dados para o cliente
            if (isCPF(cpf) == true) {
                saida.writeUTF("\nEste CPF é válido: " + imprimeCPF(cpf));
            } else {
                saida.writeUTF("Este CPF é inválido");
            }

            //Fechando o servidor
            socket.close();
            System.out.println("A porta 54321 foi fechada.");
        } catch (Exception e) {

        }
    }

    private static boolean isCPF(String CPF) {
        // Se o CPF for formado por numeros iguais, então é incorreto/falso
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") ||
                CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11)) {
            return(false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo caso ocorra erro de conversao de tipo (int)
        try {

            // Calculo do primeiro digito verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 é a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do segundo digito verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            // Verifica se os digitos calculados estão iguais aos digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    private static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
}
