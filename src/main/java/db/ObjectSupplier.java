package db;

import code.User;
import code.PdfContent;

/**
 * Created by Eric Blair on 10/8/2015.
 */
public class ObjectSupplier {

    public User getUser(long internalId){
        return User.buildFromId(internalId);
    }

    public PdfContent getPdfContent(){
        return null;
    }




}
