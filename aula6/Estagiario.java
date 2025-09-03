public class Estagiario extends Funcionario {
    private String instituicao;
    private int periodo;
    private boolean bolsaEstudo;
    
    public Estagiario(String nome, String cpf, double salario, String departamento,
                      String instituicao, int periodo, boolean bolsaEstudo) {
        super(nome, cpf, salario, departamento);
        this.instituicao = instituicao;
        this.periodo = periodo;
        this.bolsaEstudo = bolsaEstudo;
    }
    
    public String getInstituicao() {
        return instituicao;
    }
    
    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    
    public int getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
    public boolean isBolsaEstudo() {
        return bolsaEstudo;
    }
    
    public void setBolsaEstudo(boolean bolsaEstudo) {
        this.bolsaEstudo = bolsaEstudo;
    }
    
    @Override
    public double calcularBonus() {
        // Bônus menor para estagiários, baseado no período e bolsa de estudo
        double bonusBase = salario * 0.05;
        if (periodo >= 6) {
            bonusBase += salario * 0.02; // Bônus adicional para estagiários mais avançados
        }
        if (bolsaEstudo) {
            bonusBase += 100.0; // Bônus fixo para bolsistas
        }
        return bonusBase;
    }
    
    @Override
    public double calcularSalarioTotal() {
        return salario + calcularBonus();
    }
    
    @Override
    public String obterInformacoes() {
        return String.format("ESTAGIÁRIO - Nome: %s, CPF: %s, Departamento: %s, " +
                           "Instituição: %s, Período: %d, Bolsa: %s, Salário: R$ %.2f, " +
                           "Bônus: R$ %.2f, Total: R$ %.2f",
                           nome, cpf, departamento, instituicao, periodo, 
                           bolsaEstudo ? "Sim" : "Não", salario, calcularBonus(), 
                           calcularSalarioTotal());
    }
    
    @Override
    public String toString() {
        return "Estagiario{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", departamento='" + departamento + '\'' +
                ", instituicao='" + instituicao + '\'' +
                ", periodo=" + periodo +
                ", bolsaEstudo=" + bolsaEstudo +
                '}';
    }
}
