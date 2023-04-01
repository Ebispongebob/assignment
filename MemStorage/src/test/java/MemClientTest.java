import org.example.memstore.client.MemClient;
import org.junit.Test;

import java.io.IOException;

public class MemClientTest {
    @Test
    public void autoSend() throws IOException {
        MemClient.autoSend();
    }

    @Test
    public void manualSend() {
        MemClient.manualSend();
    }
}
