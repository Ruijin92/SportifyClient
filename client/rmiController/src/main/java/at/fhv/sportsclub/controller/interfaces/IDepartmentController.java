package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.DepartmentDTO;
import at.fhv.sportsclub.model.dept.SportDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IDepartmentController extends Remote {

    ArrayList<DepartmentDTO> getAllEntries() throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(DepartmentDTO departmentDTO) throws RemoteException;

    ArrayList<SportDTO> getAllSportEntries() throws RemoteException;

}

