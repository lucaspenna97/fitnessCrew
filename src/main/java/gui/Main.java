package gui;


import view.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {

    private static TrayIcon trayIcon;
    private static Image image;

    public static void main(String[] args) {

        //Inicializa construtor da classe gui.Main
        new Main();

        RabbitMQ.receiveRabbitMQMessage("teste");
    }

    private Main() {

        try {

            if (!SystemTray.isSupported()) {
                System.err.println("SystemTray não suportado.");
                return;
            }

            SystemTray systemTray = SystemTray.getSystemTray();

            image = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("image.png"))).getImage();
            trayIcon = new TrayIcon(image);

            MenuItem sobreItem = new MenuItem("Sobre");
            sobreItem.addActionListener(actionEvent -> aboutApplication());

            MenuItem sairItem = new MenuItem("Sair");
            sairItem.addActionListener(actionEvent -> System.exit(0));

            PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(sobreItem);
            popupMenu.addSeparator();
            popupMenu.add(sairItem);

            trayIcon.setPopupMenu(popupMenu);
            trayIcon.setToolTip("Fitness Crew");
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionEvent -> getView());

            systemTray.add(trayIcon);

        } catch (Exception e){
            System.err.println("Erro ao montar SystemTray.");
            e.printStackTrace();
        }

    }


    private void aboutApplication(){
        JOptionPane.showMessageDialog(null, "Fitness Crew - Gerenciamento de Cadastro");
    }

    private void getView () {

        MainScreen.buildView(image);

    }

    private void setToopTip (String message) {
        trayIcon.setToolTip(message);
    }


}
