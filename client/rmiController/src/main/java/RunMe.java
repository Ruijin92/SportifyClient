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

        /*IAuthenticationController iAuthenticationController = controllerFactory.getAuthenticationController();

        IAuthenticationController authenticationController = controllerFactory.getAuthenticationController();
        String pw ="snoop@do.gg";
        SessionDTO sessionDTO = authenticationController.authenticate("snoop@do.gg",pw.toCharArray());

        for (PersonDTO dto : personController.getAllEntries(sessionDTO).getContents()) {
            System.out.println(dto.toString());
        }

        for (SportDTO sportDTO : departmentController.getAllSportEntries()) {
            System.out.println(sportDTO);
        }*/
    }

}
