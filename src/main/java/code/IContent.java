package code; /**
 * code.IContent.java
 * Draft version with no functionality.
 * Created by wendyjan on 9/28/15 to test IDE and JVM setup.
 */
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;

public interface IContent {

    void testerMethod();

    LocalDateTime getModTime();

    LocalDateTime getCreateTime();

    long getCreatorId();

    long getInternalId();

    String getHeader();

    String getLocationOnWeb();

    JsonObject asJson();

    boolean empty();

    boolean delete();


}
