package removers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

/**
 *
 * @author bismih
 */
public class AppimagePackRemover {
    public static DefaultListModel<String> model=new DefaultListModel<>();
    private static ArrayList<String> appPackage=new ArrayList<>();
    
    public static void addNativeToList(String tex) {
        if (!model.contains(tex)) {
            model.addElement(tex.substring(0,tex.lastIndexOf("-")));
            appPackage.add(tex);
        }
    }
        
    
    public static void getNativeAppList() {
        model.clear();
        String[] script = {"/bin/bash", "-c", "ls ~/Applications/"};
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
        String[] script = {"/bin/bash", "-c", "rm ~/Applications/ "+ appPackage.get(index)};
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
