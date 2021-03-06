package player2;

import common.PlayerInterface;
import common.ServerInterface;
import common.SpaceCommand;
import controller.MainController;
import javafx.application.Platform;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Player2 extends UnicastRemoteObject implements PlayerInterface {
    private ServerInterface server;
    private MainController controller;

    public Player2(String name, MainController controller, String type, String commanderName) throws RemoteException {
        try {
            String url = "rmi://localhost/sserver";
            server = (ServerInterface) Naming.lookup(url);
            server.registerPlayer(this, type, name, commanderName);
            this.controller = controller;
        } catch (RemoteException ex) {
            System.out.println("Server RemoteException.");
            System.out.println(ex.getMessage());
        } catch (NotBoundException ex) {
            System.out.println("Server NotBoundException.");
            System.out.println(ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println("Server MalformedURLException.");
            System.out.println(ex.getMessage());
        }
    }

    public ServerInterface getServer() {
        return server;
    }

    //<editor-fold desc="rmi">


    public void confirmConnection(String s) throws RemoteException {

    }

    public void receiveCommand(SpaceCommand spaceCommand) throws RemoteException {
        System.out.println("Received command " + spaceCommand.getType() + " " + spaceCommand.getParameters() + ".");
        //String text = spaceCommand.getType().name() + " " + spaceCommand.getParameters().toString();
        //controller.textField.setText(text);
        //controller.CurrentCommand = spaceCommand;
    }

    @Override
    public void receiveCommand(String s) throws RemoteException {
        controller.getPlayerBean().setStringPropertyCaptainCommand(s);
        System.out.println(s);
    }

    @Override
    public void receivePoints(Integer integer) throws RemoteException {
        Platform.runLater(() -> {
            controller.getPlayerBean().setIntegerPropertyNumberOfPoints(integer);
        });
    }

    @Override
    public void becomeKickout(Boolean aBoolean) throws RemoteException {
        Platform.runLater(() -> {
            controller.getPlayerBean().setBooleanPropertyKickFromServer(aBoolean);

        });
    }

    @Override
    public void startRound(Integer integer) throws RemoteException {
        controller.startThread(integer);
    }

    @Override
    public void lossConnectionWithServer() throws RemoteException {
        controller.exitFromApplication();
    }

    @Override
    public void receiveNumberOfPlayers(Integer integer) throws RemoteException {
        Platform.runLater(() -> {
            controller.getPlayerBean().setIntegerPropertyNumberOfPlayers(integer);

        });
    }


}
