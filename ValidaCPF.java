import java.util.Scanner;

public class ValidaDocumento {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // CPF
        System.out.print("Digite o CPF (com ou sem formatação): ");
        String cpf = scanner.nextLine().trim();
        String cpfNumerico = extrairNumeros(cpf);

        if (!validarCPF(cpf, cpfNumerico)) return;

        // RG
        System.out.print("Digite o RG (com ou sem formatação): ");
        String rg = scanner.nextLine().trim();

        if (rg.length() == 12 && !verificarFormatacaoRG(rg)) {
            System.out.println("RG formatado incorretamente. Ex: 12.345.678-9");
            return;
        }

        rg = rg.toUpperCase();

        if (!validarRG(rg)) return;

        System.out.println("RG válido!");
    }

    private static boolean validarCPF(String cpf, String cpfNumerico) {
        if (cpfNumerico.length() != 11) {
            System.out.println("CPF inválido: deve conter 11 dígitos numéricos.");
            return false;
        }

        boolean todosIguais = true;
        char primeiroDigito = cpfNumerico.charAt(0);
        for (int i = 1; i < cpfNumerico.length(); i++) {
            if (cpfNumerico.charAt(i) != primeiroDigito) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) {
            System.out.println("CPF inválido: todos os dígitos são iguais.");
            return false;
        }

        if (!validarDigitosCPF(cpfNumerico)) {
            System.out.println("CPF inválido: dígitos verificadores incorretos.");
            return false;
        }

        System.out.println("CPF válido!");
        return true;
    }

    private static boolean validarRG(String rg) {
        rg = rg.toUpperCase();
        if (rg.length() != 9) {
            System.out.println("RG inválido: deve conter 9 caracteres.");
            return false;
        }

        int[] numeros = new int[8];
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(rg.charAt(i))) {
                System.out.println("RG inválido: os 8 primeiros caracteres devem ser dígitos.");
                return false;
            }
            numeros[i] = rg.charAt(i) - '0';
        }

        char verificador = rg.charAt(8);
        int digitoInformado = (verificador == 'X') ? 10 :
                              (Character.isDigit(verificador) ? verificador - '0' : -1);

        if (digitoInformado == -1) {
            System.out.println("RG inválido: último dígito deve ser número ou 'X'.");
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 8; i++) {
            soma += numeros[i] * (i + 2); // multiplicadores de 2 a 9
        }
        int resto = soma % 11;
        int digitoCalculado = 11 - resto;
        if (digitoCalculado == 10) digitoCalculado = 10;
        else if (digitoCalculado == 11) digitoCalculado = 0;

        if (digitoCalculado != digitoInformado) {
            System.out.println("RG inválido: dígito verificador incorreto.");
            return false;
        }

        return true;
    }

    private static boolean validarDigitosCPF(String cpf) {
        int[] numeros = new int[11];
        for (int i = 0; i < 11; i++) {
            numeros[i] = cpf.charAt(i) - '0';
        }

        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += numeros[i] * (10 - i);
        }
        int digito1 = (soma1 * 10) % 11;
        if (digito1 == 10) digito1 = 0;

        int soma2 = 0;
        for (int i = 0; i < 10; i++) {
            soma2 += numeros[i] * (11 - i);
        }
        int digito2 = (soma2 * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        return (digito1 == numeros[9]) && (digito2 == numeros[10]);
    }

    private static boolean verificarFormatacaoRG(String rg) {
        return rg.charAt(2) == '.' && rg.charAt(6) == '.' && rg.charAt(10) == '-';
    }

    private static String extrairNumeros(String texto) {
        StringBuilder sb = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) sb.append(c);
        }
        return sb.toString();
    }
}
