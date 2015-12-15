import org.apache.zookeeper.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by goga on 14.12.15.
 */

public class zkLockTest implements Watcher, Runnable {

    private ZooKeeper zk;
    private String id;
    private String stat = "";
    private JTextArea l;

    public zkLockTest(JTextArea l) {
        this.l = l;
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 3000, this);
            c_status("connected");
            id = zk.create("/lock_", new byte[0],
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            id = id.replace("/", "");
            c_status("id: " + id);
        } catch (IOException e) {
            e.printStackTrace();
            c_status("not connected");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    public void trylock() {
        try {
            List<String> queue = zk.getChildren("/", false);
            Collections.sort(queue);
            c_status(queue.toString());
            if (id.equals(queue.get(0))) {
                c_status("lock get!");
            } else {
                c_status("waiting");
                zk.exists("/" + queue.get(0), true);
            }
            l.setText(getStat());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void c_status(String s) {
        this.stat += s + "\n";
    }

    public String getStat() {
        return this.stat;
    }

    public void process(WatchedEvent watchedEvent) {
        c_status("event !");
        trylock();
    }

    public void run() {
        trylock();
    }
}