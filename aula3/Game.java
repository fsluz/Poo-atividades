import java.util.ArrayList;
import java.util.List;

// Classe Game
class Game {
    private String nome;
    private double preco;
    private String categoria;
    private int classificacaoEtaria;

    // Construtor com parâmetros
    public Game(String nome, double preco, String categoria, int classificacaoEtaria) {
        this.setNome(nome);
        this.setPreco(preco);
        this.setCategoria(categoria);
        this.setClassificacaoEtaria(classificacaoEtaria);
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    // Setters com validação
    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty()) {
            this.nome = nome;
        }
    }

    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.isEmpty()) {
            this.categoria = categoria;
        }
    }

    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    public void setClassificacaoEtaria(int idade) {
        if (idade >= 0 && idade <= 18) {
            this.classificacaoEtaria = idade;
        }
    }

    // Método para exibir detalhes do jogo
    public void exibirDetalhes() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Categoria: " + this.categoria);
        System.out.println("Preço: R$" + this.preco);
        System.out.println("Classificação Etária: " + this.classificacaoEtaria);
    }
}

// Classe Conta
class Conta {
    private double saldo;
    private List<Game> jogos;

    // Construtor da conta
    public Conta(double saldoInicial) {
        this.saldo = saldoInicial;
        this.jogos = new ArrayList<>();
    }

    // Getter de saldo
    public double getSaldo() {
        return saldo;
    }

    // Método para adicionar saldo
    public void adicionarSaldo(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        } else {
            System.out.println("Valor inválido para adicionar saldo.");
        }
    }

    // Método para comprar jogo
    public void comprarJogo(Game jogo) throws Exception {
        if (this.saldo >= jogo.getPreco()) {
            this.saldo -= jogo.getPreco();
            this.jogos.add(jogo);
            System.out.println("Jogo comprado com sucesso: " + jogo.getNome());
        } else {
            throw new Exception("Saldo insuficiente para comprar o jogo " + jogo.getNome());
        }
    }

    // Método para exibir todos os jogos comprados
    public void exibirJogos() {
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo comprado.");
        } else {
            System.out.println("Jogos comprados:");
            for (Game jogo : jogos) {
                jogo.exibirDetalhes();
            }
        }
    }

    // Filtrar jogos por categoria
    public void filtrarJogosPorCategoria(String categoria) {
        System.out.println("Filtrando jogos por categoria: " + categoria);
        for (Game jogo : jogos) {
            if (jogo.getCategoria().equalsIgnoreCase(categoria)) {
                jogo.exibirDetalhes();
            }
        }
    }

    // Filtrar jogos por preço
    public void filtrarJogosPorPreco(double precoMaximo) {
        System.out.println("Filtrando jogos por preço máximo: R$" + precoMaximo);
        for (Game jogo : jogos) {
            if (jogo.getPreco() <= precoMaximo) {
                jogo.exibirDetalhes();
            }
        }
    }
}

// Classe Principal (Main)
public class Main {
    public static void main(String[] args) {
        try {
            // Criando uma conta com saldo inicial
            Conta conta = new Conta(100.00);

            // Criando jogos
            Game minecraft = new Game("Minecraft", 59.99, "Aventura", 10);
            Game fifa = new Game("FIFA 22", 199.90, "Esportes", 14);
            Game gta = new Game("GTA V", 89.90, "Ação", 18);

            // Adicionando saldo
            conta.adicionarSaldo(50.00); // Adiciona R$50 à conta
            System.out.println("Saldo disponível: R$" + conta.getSaldo());

            // Comprando jogos
            conta.comprarJogo(minecraft);
            conta.comprarJogo(fifa);

            // Exibindo todos os jogos comprados
            conta.exibirJogos();

            // Filtrando jogos por categoria
            conta.filtrarJogosPorCategoria("Aventura");

            // Filtrando jogos por preço
            conta.filtrarJogosPorPreco(100.00);

            // Tentativa de comprar um jogo com saldo insuficiente
            conta.comprarJogo(gta); // Vai lançar exceção

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
