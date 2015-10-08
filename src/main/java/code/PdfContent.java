package code; /**
 * code.PdfContent.java
 * Draft version with no functionality.
 * Created by wendyjan on 9/28/15 to test IDE and JVM setup.
 */
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PdfContent implements Content {

    long _internalId;
    LocalDateTime _creationDateTime;
    LocalDateTime _modificationDateTime;
    long _creatorId;
    long _groupId;
    String _header; // for a quick summary of the content, that might appear as a tool tip
    String _locationOnWeb; // for Ameen's blobs

    public void testerMethod() {
        System.out.println("PDFContent class and Content interface are working with IntelliJ and JVM 1.8.");
        LocalDateTime date = LocalDateTime.now();
        String text = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(text);
    }

    public LocalDateTime getModTime() {
        return _modificationDateTime;
    }

    public LocalDateTime getCreateTime() {
        return _creationDateTime;
    }

    public long getCreatorId() {
        return _creatorId;
    }

    public long getInternalId() {
        return _internalId;
    }

    public String getHeader() {
        return _header;
    }

    public String getLocationOnWeb() {
        return _locationOnWeb;
    }

    public String toString(){
        return asJson().toString();
    }

    public JsonObject asJson(){

        JsonObject json = new JsonObject();
        json.put("internalId", _internalId);
        json.put("crationTime",_creationDateTime);
        json.put("modificationTime",_modificationDateTime);
        json.put("creator",_creatorId);
        json.put("group",_groupId);
        json.put("header", _header);
        json.put("location", _locationOnWeb);

        return json;
         }


}
