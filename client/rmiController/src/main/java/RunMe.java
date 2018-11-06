import factory.IControllerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Alex on 06.11.2018.
 */

public class RunMe {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        IControllerFactory controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
        System.out.println(controllerFactory.getCreateMemberController().createMember());
    }

}
