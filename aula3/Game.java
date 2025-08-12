public class Game {
    // Atributos privados (encapsulados)
    private String nome;
    private double preco;
    private String categoria;
    private int classificacaoEtaria;

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

    // Método principal para testar a classe
    public static void main(String[] args) {
        // Criar um objeto Game
        Game jogo = new Game();

        // Testar nome e categoria
        jogo.setNome("Minecraft");
        jogo.setCategoria("Aventura");

        // Testar preço válido
        jogo.setPreco(59.99);
        System.out.println("Nome do jogo: " + jogo.getNome());
        System.out.println("Categoria: " + jogo.getCategoria());
        System.out.println("Preço do jogo: R$" + jogo.getPreco());

        // Testar preço inválido
        jogo.setPreco(-10.00); // Não deve alterar
        System.out.println("Tentativa de preço inválido: R$" + jogo.getPreco());

        // Testar classificação etária válida
        jogo.setClassificacaoEtaria(12);
        System.out.println("Classificação etária: " + jogo.getClassificacaoEtaria());

        // Testar classificação etária inválida
        jogo.setClassificacaoEtaria(25); // Não deve alterar
        System.out.println("Tentativa de classificação inválida: " + jogo.getClassificacaoEtaria());
    }
}