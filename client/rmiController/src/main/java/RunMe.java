import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.factory.IControllerFactory;

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

        IPersonController personController = controllerFactory.getPersonController();
        IDepartmentController departmentController = controllerFactory.getDepartmentController();
    }

}
