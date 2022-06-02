package removers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;

/**
 *
 * @author bismih
 */
public class PardusPackRemover {
    public static DefaultListModel<String> model=new DefaultListModel<>();
    
    public static void addNativeToList(String tex) {
        if (!model.contains(tex)) {
            model.addElement(tex);
        }
    }
        
    
    public static void getNativeAppList() {
        model.clear();
        String[] cmd = {"/bin/bash", "-c", "cat /usr/share/applications/*.desktop "};
        try {
            Process pb = Runtime.getRuntime().exec(cmd);

            String line;
            String result = "";
            String deneme = "";

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {

                if (line.contains("Exec") && line.charAt(4) == '=') {
                    if (line.charAt(5) != '/') {
                        if (line.contains(" ")) {
                            //result += line.substring(5, line.indexOf(' ')) + "\n";
                            addNativeToList(line.substring(5, line.indexOf(' ')));
                        } else {
                            //result += line.substring(5) + "\n";
                            addNativeToList(line.substring(5));
                        }
                    } else if (!line.contains("/lib/")) {
                        if (line.contains(" ")) //deneme += line.substring(line.lastIndexOf('/')+1, line.indexOf(" "))+"\n";
                        {
                            addNativeToList(line.substring(line.lastIndexOf('/') + 1, line.indexOf(" ")));
                        } else //deneme += line.substring(line.lastIndexOf('/')+1)+"\n";
                        {
                            addNativeToList(line.substring(line.lastIndexOf('/') + 1));
                        }
                    }
                }

            }
            input.close();
            System.out.println(deneme + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    
    public static void removeNativeApp(String app,String pass) {
        String[] cmd = {"/bin/bash", "-c", "echo "+pass+"|sudo -S apt remove " + app+" -y"};
        try {
            Process pb = Runtime.getRuntime().exec(cmd);

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
