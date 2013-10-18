package org.investovator.core.data.exeptions;

/**
 * @author rajith
 * @version $Revision$
 *
 * This will only be thrown in case of unavailability of requested data
 */
public class DataNotFoundException extends Exception{

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Exception e){
        super(e);
    }
}
