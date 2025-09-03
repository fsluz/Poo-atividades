public class Desenvolvedor extends Funcionario {
    private String linguagemProgramacao;
    private int anosExperiencia;
    private boolean certificado;
    
    public Desenvolvedor(String nome, String cpf, double salario, String departamento,
                         String linguagemProgramacao, int anosExperiencia, boolean certificado) {
        super(nome, cpf, salario, departamento);
        this.linguagemProgramacao = linguagemProgramacao;
        this.anosExperiencia = anosExperiencia;
        this.certificado = certificado;
    }
    
    public String getLinguagemProgramacao() {
        return linguagemProgramacao;
    }
    
    public void setLinguagemProgramacao(String linguagemProgramacao) {
        this.linguagemProgramacao = linguagemProgramacao;
    }
    
    public int getAnosExperiencia() {
        return anosExperiencia;
    }
    
    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
    
    public boolean isCertificado() {
        return certificado;
    }
    
    public void setCertificado(boolean certificado) {
        this.certificado = certificado;
    }
    
    @Override
    public double calcularBonus() {
        // Bônus baseado em anos de experiência e certificação
        double bonusBase = salario * 0.10;
        bonusBase += anosExperiencia * 50.0; // R$ 50 por ano de experiência
        if (certificado) {
            bonusBase += 200.0; // Bônus fixo para certificados
        }
        return bonusBase;
    }
    
    @Override
    public double calcularSalarioTotal() {
        return salario + calcularBonus();
    }
    
    @Override
    public String obterInformacoes() {
        return String.format("DESENVOLVEDOR - Nome: %s, CPF: %s, Departamento: %s, " +
                           "Linguagem: %s, Experiência: %d anos, Certificado: %s, " +
                           "Salário: R$ %.2f, Bônus: R$ %.2f, Total: R$ %.2f",
                           nome, cpf, departamento, linguagemProgramacao, anosExperiencia,
                           certificado ? "Sim" : "Não", salario, calcularBonus(), 
                           calcularSalarioTotal());
    }
    
    @Override
    public String toString() {
        return "Desenvolvedor{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", salario=" + salario +
                ", departamento='" + departamento + '\'' +
                ", linguagemProgramacao='" + linguagemProgramacao + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                ", certificado=" + certificado +
                '}';
    }
}
