package removers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;

/**
 *
 * @author bismih
 */
public class SnapPackRemover {
    public static DefaultListModel<String> model = new DefaultListModel<>();

    public static void addNativeToList(String tex) {
        if (!model.contains(tex)) {
            model.addElement(tex);
        }
    }

    public static void getNativeAppList() {
        model.clear();
        // ?snap paketleri bulur
        String[] script = { "/bin/bash", "-c", "ls /snap/bin " };
        try {
            Process pb = Runtime.getRuntime().exec(script);

            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {

                addNativeToList(line);

            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeNativeApp(int index, String pass) {
        System.out.println(model.get(index));

        String[] script = { "/bin/bash", "-c", "echo " + pass + "|sudo -S snap remove " + model.get(index) };
        try {
            Process pb = Runtime.getRuntime().exec(script);

            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getNativeAppList();
    }
}
