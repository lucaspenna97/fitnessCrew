package bean;

public class User {

    public static String TABLE_NAME = "user";

    public static String CPF    = "cpf";
    public static String NOME   = "nome";
    public static String IDADE  = "idade";
    public static String SEXO   = "sexo";
    public static String ALTURA = "altura";
    public static String PESO   = "peso";
    public static String EMAIL  = "email";

    private String cpf;
    private String nome;
    private String idade;
    private String sexo;
    private String altura;
    private String peso;
    private String email;

    public User () {}

    public User (String cpf, String nome, String idade, String sexo, String altura, String peso, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.altura = altura;
        this.peso = peso;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User {"   +
                "cpf:"    + cpf    + ", " +
                "nome:"   + nome   + ", " +
                "idade:"  + idade  + ", " +
                "sexo:"   + sexo   + ", " +
                "altura:" + altura + ", " +
                "peso:"   + peso   + ", " +
                "email:"  + email  + "}";
    }

}
