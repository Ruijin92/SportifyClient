package at.fhv.team2;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.factory.IControllerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class DataProvider {

    private static IControllerFactory controllerFactory = null;
    private static IPersonController personController = null;
    private static IDepartmentController departmentController = null;

    public DataProvider(Registry registry){
        if (controllerFactory == null) {
            try {
                IControllerFactory controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static IPersonController getPersonControllerInstance() {
        if (personController == null) {
            try {
                return controllerFactory.getPersonController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return personController;
    }

    public static IDepartmentController getDepartmentControllerInstance() {
        if (departmentController == null) {
            try {
                return controllerFactory.getDepartmentController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return departmentController;
    }
}
