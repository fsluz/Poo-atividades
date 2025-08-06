// Classe Pessoa
class Pessoa {
    // Atributos privados
    private String nome;
    private int idade;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade >= 0) {
            this.idade = idade;
        }
    }

    // Método para apresentar a pessoa
    public void apresentar() {
        System.out.println("Olá! Meu nome é " + nome + " e tenho " + idade + " anos.");
    }
}

// Classe principal com o método main
public class Main {
    public static void main(String[] args) {
        // Criando um objeto Pessoa
        Pessoa pessoa1 = new Pessoa();

        // Configurando atributos
        pessoa1.setNome("Carlos");
        pessoa1.setIdade(25);

        // Apresentando a pessoa
        pessoa1.apresentar(); // Saída: Olá! Meu nome é Carlos e tenho 25 anos.

        // Criando outro objeto Pessoa
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Ana");
        pessoa2.setIdade(30);

        // Apresentando a segunda pessoa
        pessoa2.apresentar(); // Saída: Olá! Meu nome é Ana e tenho 30 anos.
    }
}
