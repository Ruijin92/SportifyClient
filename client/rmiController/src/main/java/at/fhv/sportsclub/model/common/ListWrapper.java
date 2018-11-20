package at.fhv.sportsclub.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class ListWrapper<E> implements Serializable {

    private static final long serialVersionUID = 9111185098267757690L;

    private ArrayList<E> contents;
    private ResponseMessageDTO response;

    public ListWrapper() {

    }
}
