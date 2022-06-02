package removers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

/**
 *
 * @author bismih
 */
public class FlatackPackRemover {
    public static DefaultListModel<String> model=new DefaultListModel<>();
    private static ArrayList<String> appPackage=new ArrayList<>();
    
    public static void addNativeToList(String tex) {
        if (!model.contains(tex)) {
            model.addElement(tex.substring(tex.lastIndexOf(".")+1));
            appPackage.add(tex);
        }
    }
        
    
    public static void getNativeAppList() {
        model.clear();
        //? flatpak paketlerini bulur
        String[] script = {"/bin/bash", "-c", "ls /var/lib/flatpak/app"};
        try {
            Process pb = Runtime.getRuntime().exec(script);

            String line;

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {

                if(!line.isEmpty()){
                    addNativeToList(line);
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
    public static void removeNativeApp(int index) {
        String[] script = {"/bin/bash", "-c", "flatpak uninstall "+ appPackage.get(index) +" -y"};
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
