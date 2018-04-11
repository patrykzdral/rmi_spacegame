package controller;

import common.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Main;
import player3.Player3;
import player_fx_bean.PlayerBean;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    PlayerBean playerBean;
    private Player3 player;
    private ServerInterface server;


    public void initialize(URL location, ResourceBundle resources) {

        playerBean.initPlayerAndCaptainNicknames(Main.login, Main.commander, "Działko bojowe");

        try
        {
            player = new Player3(Main.login, this, "Działko bojowe",Main.commander);
            server = player.getServer();
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }


    public PlayerBean getPlayerBean() {
        return playerBean;
    }

}