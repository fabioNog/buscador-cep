import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Classe para consulta de endereços usando o serviço ViaCEP.
 */
public class ConsultaCep {

    private final HttpClient client;
    private final Gson gson;

    public ConsultaCep() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    /**
     * Busca o endereço correspondente ao CEP informado.
     *
     * @param cep CEP no formato "00000-000" ou "00000000".
     * @return Objeto Endereco com os dados retornados pela API.
     * @throws IOException se houver um problema na comunicação com a API.
     * @throws InterruptedException se a solicitação for interrompida.
     * @throws IllegalArgumentException se o CEP estiver em formato inválido.
     */
    public Endereco buscaEndereco(String cep) throws IOException, InterruptedException {
        if (cep == null || !cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido! Certifique-se de usar o formato 00000-000 ou 00000000.");
        }

        URI endereco = URI.create("https://viacep.com.br/ws/" + cep + "/json/");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endereco)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se a API retornou um CEP inexistente
            if (response.body().contains("\"erro\"")) {
                throw new IllegalArgumentException("CEP não encontrado na base de dados do ViaCEP.");
            }

            return gson.fromJson(response.body(), Endereco.class);
        } catch (IOException | InterruptedException e) {
            throw new IOException("Erro ao consultar o serviço ViaCEP: " + e.getMessage(), e);
        }
    }
}
