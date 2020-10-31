import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LerArquivo {

    public static void main(String[] args) {

        String linhas[] = new String[100000];
        int numLinhas = 0;

        Path filePath = Paths.get("acidentes.csv");

        // Ler o arquivo
        try (BufferedReader reader = Files.newBufferedReader(filePath, Charset.defaultCharset())) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                linhas[numLinhas] = line;
                numLinhas++;
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        // Aceita os seguintes formatos de data/horario
        // 20200101         (somente data)
        // 20200101 08:00   (data e horario)
        // 20200101 8 :00   (horario sem zero à esquerda da hora)
        Pattern datePattern = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})(?: (?:(\\d{2})|(\\d) ):(\\d{2}))?", Pattern.CASE_INSENSITIVE);


        // Mude numLinhas para algum numero pequeno para executar testes mais rapidamente.
        // Ex:
        // for (int i = 0; i < 50; i++) {
        for (int i = 0; i < numLinhas; i++) {

            String[] campos = linhas[i].split(";"); // divide a string pelo espaco em branco

            // Ignorar esta linha caso o campo de endereco nao
            // puder ser separado em 2
            if (campos[0].split(" ").length < 2) {
                continue;
            }

            // Determinar data e horario.        
            Matcher dateMatcher = datePattern.matcher(campos[2]);
            dateMatcher.matches();

            int ano = Integer.parseInt(dateMatcher.group(1));
            int mes = Integer.parseInt(dateMatcher.group(2));
            int dia = Integer.parseInt(dateMatcher.group(3));

            int hora;
            if (dateMatcher.group(4) != null) {
                hora = Integer.parseInt(dateMatcher.group(4));
            }
            else if (dateMatcher.group(5) != null) {
                hora = Integer.parseInt(dateMatcher.group(5));
            }
            else {
                hora = 0;
            }

            int minuto;
            if (dateMatcher.group(6) != null) {
                minuto = Integer.parseInt(dateMatcher.group(6));
            }
            else {
                minuto = 0;
            }

            String logradouro = campos[0].split(" ", 2)[0];
            String nomeLog = campos[0].split(" ", 2)[1];
            String tipoAcidente = campos[1];
            LocalDateTime data = LocalDateTime.of(ano, mes, dia, hora, minuto);
            String diaSemana = campos[3];
            int feridos = Integer.parseInt(campos[4]);
            int fatais = Integer.parseInt(campos[5]);
            int auto = Integer.parseInt(campos[6]);
            int taxis = Integer.parseInt(campos[7]);
            int lotacao = Integer.parseInt(campos[8]);
            int onibusUrb = Integer.parseInt(campos[9]);
            int onibusInt = Integer.parseInt(campos[10]);
            int caminhao = Integer.parseInt(campos[11]);
            int moto = Integer.parseInt(campos[12]);
            int carroca = Integer.parseInt(campos[13]);
            int bicicleta = Integer.parseInt(campos[14]);
            String tempo = campos[15];
            String turno = campos[16];
            String regiao = campos[17];

            System.out.println(logradouro + " " + nomeLog + "; " + tipoAcidente + "; " + data.toString() + "; " + tempo + "; " + turno + "; " + regiao);
        }
    }
}