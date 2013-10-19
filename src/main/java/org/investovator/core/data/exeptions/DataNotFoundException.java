package org.investovator.core.data.exeptions;

/**
 * @author rajith
 * @version $Revision$
 *
 * This will only be thrown in case of unavailability of requested data
 */
public class DataNotFoundException extends Exception{

    public DataNotFoundException() {
        super("Requested data not found");         //TODO give more info
    }

    public DataNotFoundException(Exception e){
        super(e);
    }
}
