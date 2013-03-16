package example;

import com.noveogroup.android.log.LoggerManager;

// todo delete class _Log_
// todo implement slf4j adapter
public class Main {

    public static void main(String[] args) {
        LoggerManager.getLogger("com.noveo.OLoLo").i("com.noveo.OLoLo");
        LoggerManager.getLogger("com.noveo.BlaBlaBla").i("com.noveo.BlaBlaBla");
        LoggerManager.getLogger("fr.customer.MainActivity").i("fr.customer.MainActivity");
        LoggerManager.getLogger("unknown-tag").i("unknown-tag");
        LoggerManager.getLogger().i("simple call");
    }

}
