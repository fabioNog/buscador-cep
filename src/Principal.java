import java.io.IOException;

public class Principal {
    public static void main(String[] args) {
        ConsultaCep consultaCep = new ConsultaCep();

        try {
            Endereco novoEndereco = consultaCep.buscaEndereco("13890000");
            System.out.println("Endereço encontrado: " + novoEndereco);
        } catch (IOException e) {
            System.err.println("Erro de comunicação com o serviço ViaCEP: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("A operação foi interrompida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na consulta: " + e.getMessage());
        }
    }
}
