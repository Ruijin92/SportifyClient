package at.fhv.team2;

import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.factory.IControllerFactory;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.security.authentication.IAuthenticationController;
import com.sun.xml.internal.bind.v2.model.core.ID;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashMap;

public class DataProviderFactory {

    private static IDataProvider currentDataProvider;
    private static Registry registry;
    private static InitialContext initialContext;
    private static HashMap<Class, IDataProvider> types = new HashMap<>();
    private static SessionDTO session;

    public static IDataProvider get(Class<?> typeOfDataProvider) {
        return types.get(typeOfDataProvider);
    }

    public static IDataProvider getCurrentDataProvider() {
        return currentDataProvider;
    }

    public static void setCurrentDataProvider(Class<?> currentDataProvider) {
        DataProviderFactory.currentDataProvider = DataProviderFactory.get(currentDataProvider);
    }

    public static void fillTypes() {
        if (types.size() != 2) {
            types.put(DataProviderRMI.class, new DataProviderRMI());
            types.put(DataProviderEJB.class, new DataProviderEJB());
        }
    }

    public static void setRegistry(Registry registryToSet) {
        registry = registryToSet;
    }

    public static Registry getRegistry() {
        return registry;
    }

    public static InitialContext getInitialContext() {
        return initialContext;
    }

    public static void setInitialContext(InitialContext initialContext) {
        DataProviderFactory.initialContext = initialContext;
    }
}
