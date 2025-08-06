public class Livro {
    // Atributos privados (encapsulamento)
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private double preco;
    private boolean disponivel;

    // Construtor
    public Livro(String titulo, String autor, int anoPublicacao, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.disponivel = true; // Livro começa disponível
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public double getPreco() { return preco; }
    public boolean isDisponivel() { return disponivel; }

    // Setters com validação
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        }
    }

    public void setPreco(double preco) {
        if (preco >= 0) {
            this.preco = preco;
        }
    }

    // Métodos de comportamento
    public void emprestar() {
        if (disponivel) {
            disponivel = false;
            System.out.println("Livro \"" + titulo + "\" emprestado com sucesso!");
        } else {
            System.out.println("Livro \"" + titulo + "\" não está disponível.");
        }
    }

    public void devolver() {
        disponivel = true;
        System.out.println("Livro \"" + titulo + "\" devolvido com sucesso!");
    }

    public int obterIdade(int anoAtual) {
        return anoAtual - anoPublicacao;
    }

    public void aplicarDesconto(double percentual) {
        if (percentual > 0 && percentual <= 100) {
            preco = preco - (preco * percentual / 100);
            System.out.println("Desconto aplicado! Novo preço: R$ " + preco);
        }
    }
}
