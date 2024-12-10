import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner leitura  = new Scanner(System.in);
        ConsultaCep consultaCep = new ConsultaCep();

        System.out.println("Digite o valor do cep a ser consultado");

        var cep  =  leitura.nextLine();

        try {
            Endereco novoEndereco = consultaCep.buscaEndereco(cep);
            System.out.println("Endereço encontrado: " + novoEndereco);
            GeradorArquivo gerador = new GeradorArquivo();
            gerador.salvaJson(novoEndereco);
        } catch (IOException e) {
            System.err.println("Erro de comunicação com o serviço ViaCEP: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("A operação foi interrompida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na consulta: " + e.getMessage());
        }
    }
}
