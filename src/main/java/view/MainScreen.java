package view;

import bean.User;
import dao.UserDAO;
import gui.ProcessResults;
import util.MailSender;
import util.MakeExcel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MainScreen extends JFrame {

    private JTabbedPane tabbedPanel;
    private JPanel mainPanel;
    private JTextField textFieldCpf;
    private JTextField textFieldNome;
    private JTextField textFieldIdade;
    private JTextField textFieldAltura;
    private JTextField textFieldPeso;
    private JTextField textFieldEmail;
    private JTextField textFieldCpfResultados;
    private JTextField textFieldImc;
    private JTextField textFieldTmb;
    private JTextField textFieldGrau;
    private JButton buttonSalvar;
    private JButton buttonExcluir;
    private JButton buttonPesquisar;
    private JButton buttonPesquisarResultados;
    private JButton buttonEnviarEmail;
    private JButton buttonExportarPlanilha;
    private JTextArea textAreaSituacao;
    private JComboBox comboBoxSexo;



    private MainScreen() {
        setContentPane(mainPanel);
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));

        textAreaSituacao.setMaximumSize(new Dimension(300,150));

        buttonPesquisar.addActionListener(e -> findUser());
        buttonSalvar.addActionListener(e -> saveUser());
        buttonExcluir.addActionListener(e -> deleteUser());
        buttonPesquisarResultados.addActionListener(e -> resultsUser());
        buttonEnviarEmail.addActionListener(e -> sendEmail());
        buttonExportarPlanilha.addActionListener(e -> exportExcel());

    }

    public void disposeView () { dispose(); }

    public static void buildView (Image image) {

        MainScreen mainScreen = new MainScreen();
        mainScreen.setIconImage(image);
        mainScreen.setTitle("Gerenciamento de Cadastro");
        mainScreen.setVisible(true);
        mainScreen.setResizable(false);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setMinimumSize(new Dimension(600,300));
        mainScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    private void findUser () {

        String cpf = textFieldCpf.getText();

        //Valida se o campo esta vazio ou nulo
        if (cpf == null || cpf.isEmpty()){
            JOptionPane.showMessageDialog(MainScreen.this,"Por favor, insira um CPF válido para pesquisa.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(cpf);

        //Valida se o user é nulo
        if (user == null) {
            JOptionPane.showMessageDialog(MainScreen.this,"Nenhum cadastro encontrado com o CPF inserido.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        textFieldNome.setText(user.getNome());
        textFieldIdade.setText(user.getIdade());
        textFieldEmail.setText(user.getEmail());
        textFieldAltura.setText(user.getAltura());
        textFieldPeso.setText(user.getPeso());

        if (user.getSexo().equalsIgnoreCase("Masculino")) comboBoxSexo.setSelectedIndex(1);
        if (user.getSexo().equalsIgnoreCase("Feminino")) comboBoxSexo.setSelectedIndex(2);

    }

    private void saveUser () {

        boolean hasProblems = false;
        StringBuilder stringBuilder = new StringBuilder();

        String cpf = textFieldCpf.getText();
        String nome = textFieldNome.getText();
        String idade = textFieldIdade.getText();
        String email = textFieldEmail.getText();
        String altura = textFieldAltura.getText();
        String peso = textFieldPeso.getText();
        String sexo;

        if (comboBoxSexo.getSelectedItem() != null) {
            sexo = comboBoxSexo.getSelectedItem().toString();
            if (sexo.equalsIgnoreCase("Selecione...")) sexo = "";
        } else {
            sexo = "";
        }

        if (cpf == null || cpf.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo CPF não inserido.");
            stringBuilder.append(System.lineSeparator());
        } else {

            if (cpf.length() != 11){
                hasProblems = true;
                stringBuilder.append("O campo CPF possui o número de dígitos incorreto.");
                stringBuilder.append(System.lineSeparator());
            }
        }

        if (nome == null || nome.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo Nome não inserido.");
            stringBuilder.append(System.lineSeparator());
        }

        if (idade == null || idade.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo Idade não inserido.");
            stringBuilder.append(System.lineSeparator());
        }

        if (email == null || email.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo E-mail não inserido.");
            stringBuilder.append(System.lineSeparator());
        }

        if (altura == null || altura.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo Altura não inserido.");
            stringBuilder.append(System.lineSeparator());
        }

        if (peso == null || peso.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Campo Peso não inserido.");
            stringBuilder.append(System.lineSeparator());
        }

        if (sexo == null || sexo.isEmpty()){
            hasProblems = true;
            stringBuilder.append("Nenhum sexo selecionado na lista.");
            stringBuilder.append(System.lineSeparator());
        }

        if (hasProblems){
            JOptionPane.showMessageDialog(MainScreen.this, "Os seguintes campos contém problemas: " + System.lineSeparator() + stringBuilder.toString() ,null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setCpf(cpf);
        user.setNome(nome);
        user.setIdade(idade);
        user.setPeso(peso);
        user.setAltura(altura);
        user.setEmail(email);
        user.setSexo(sexo);

        UserDAO userDAO = new UserDAO();
        User exists = userDAO.getUser(cpf);

        if (exists == null) {
            boolean retorno = userDAO.insertUser(user);
            if (retorno) JOptionPane.showMessageDialog(MainScreen.this, "Novo cadastro realizado com sucesso.",null, JOptionPane.INFORMATION_MESSAGE);
        } else {
            boolean retorno = userDAO.updateUser(user);
            if (retorno) JOptionPane.showMessageDialog(MainScreen.this, "Cadastro atualizado com sucesso.",null, JOptionPane.INFORMATION_MESSAGE);
        }

        cleanFields();

    }

    private void deleteUser () {

        String cpf = textFieldCpf.getText();

        //Valida se o campo esta vazio ou nulo
        if (cpf == null || cpf.isEmpty()){
            JOptionPane.showMessageDialog(MainScreen.this,"Por favor, insira um CPF válido para exclusão." ,null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(cpf);

        //Valida se o user é nulo
        if (user == null) {
            JOptionPane.showMessageDialog(MainScreen.this,"Nenhum cadastro encontrado com o CPF inserido.", null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        textFieldNome.setText(user.getNome());
        textFieldIdade.setText(user.getIdade());
        textFieldEmail.setText(user.getEmail());
        textFieldAltura.setText(user.getAltura());
        textFieldPeso.setText(user.getPeso());

        String[] options = {"Sim", "Não"};
        int retorno = JOptionPane.showOptionDialog(MainScreen.this, "Deseja realmente excluir este registro ?", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        if (retorno == 0) {
            userDAO.deleteUser(cpf);
            cleanFields();
        }

    }

    private void resultsUser () {

        String cpf = textFieldCpfResultados.getText();

        //Valida se o campo esta vazio ou nulo
        if (cpf == null || cpf.isEmpty()){
            JOptionPane.showMessageDialog(MainScreen.this,"Por favor, insira um CPF válido para pesquisa.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(cpf);

        //Valida se o user é nulo
        if (user == null) {
            JOptionPane.showMessageDialog(MainScreen.this,"Nenhum cadastro encontrado com o CPF inserido.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }


        String imc = ProcessResults.calculateImc(user.getPeso(), user.getAltura());
        textFieldImc.setText(imc);

        int imcInt = Integer.parseInt(imc);
        textFieldGrau.setText(ProcessResults.calculateGrauImc(imcInt));
        textAreaSituacao.setText(ProcessResults.resumoDescricao(imcInt));

        String tmb = ProcessResults.calculateTmb(Integer.parseInt(user.getPeso()), Integer.parseInt(user.getAltura()), Integer.parseInt(user.getIdade()), user.getSexo());
        textFieldTmb.setText(tmb);


    }

    private void sendEmail () {

        String cpf = textFieldCpfResultados.getText();

        //Valida se o campo esta vazio ou nulo
        if (cpf == null || cpf.isEmpty()){
            JOptionPane.showMessageDialog(MainScreen.this,"Por favor, insira um CPF válido para pesquisa.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(cpf);

        //Valida se o user é nulo
        if (user == null) {
            JOptionPane.showMessageDialog(MainScreen.this,"Nenhum cadastro encontrado com o CPF inserido.",null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (testConnecton()) {

            boolean retorno = new MailSender().makeEmail(user);
            if (retorno)
                JOptionPane.showMessageDialog(MainScreen.this, "E-mail enviado com sucesso.", null, JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(MainScreen.this, "Erro ao enviar e-mail para " + user.getEmail() + ".", null, JOptionPane.ERROR_MESSAGE);

        }

    }

    private void cleanFields () {

        textFieldCpf.setText("");
        textFieldNome.setText("");
        textFieldIdade.setText("");
        textFieldEmail.setText("");
        textFieldAltura.setText("");
        textFieldPeso.setText("");
        comboBoxSexo.setSelectedIndex(0);

    }

    private boolean testConnecton () {
        try {

            URL url = new URL("http://www.google.com.br");
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            return true;

        } catch (Exception e){

            System.err.println("Sem conexão com a internet: " + e.getMessage());
            return false;
        }
    }

    private void exportExcel () {

        UserDAO userDAO = new UserDAO();

        List<User> userList = userDAO.getUsers();

        if (userList == null || userList.size() == 0) {
            JOptionPane.showMessageDialog(MainScreen.this, "Não há casdastro para criação de planilha.", null, JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean retorno = MakeExcel.generateFile(userList);

        if (retorno)
            JOptionPane.showMessageDialog(MainScreen.this, "Planilha criada com sucesso.", null, JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(MainScreen.this, "Erro ao criar a planilha, por favor contate o suporte.", null, JOptionPane.ERROR_MESSAGE);

    }

}


