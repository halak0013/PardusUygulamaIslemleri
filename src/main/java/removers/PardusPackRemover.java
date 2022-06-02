package removers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.DefaultListModel;

/**
 *
 * @author bismih
 */
public class PardusPackRemover {
    public static DefaultListModel<String> model = new DefaultListModel<>();

    public static void addNativeToList(String tex) {
        if (!model.contains(tex)) {
            model.addElement(tex);
        }
    }

    public static void getNativeAppList() {
        model.clear();
        // ? uygulamaların isimleri için kısayolların içeriklerini alınıyor
        String[] cmd = { "/bin/bash", "-c", "cat /usr/share/applications/*.desktop " };
        try {
            Process pb = Runtime.getRuntime().exec(cmd);

            String line;
            String result = "";
            String deneme = "";

            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                // ? uygulamaların başlatma komutları çekiliyor
                if (line.contains("Exec") && line.charAt(4) == '=') {
                    // ? eğer satırın 4. karakteri / olmayan(çünkü bazı komutlar kütüphüleneler için
                    // oluyor ) satırları seçiyor
                    if (line.charAt(5) != '/') {
                        if (line.contains(" ")) {
                            // result += line.substring(5, line.indexOf(' ')) + "\n";
                            // ? çalıştırma komutlarından uygulama ismileri çekiliyor
                            //*Exec=dolphin %u
                            addNativeToList(line.substring(5, line.indexOf(' ')));
                        } else {
                            // result += line.substring(5) + "\n";
                            // ? bazı uygulamaların çalıştırma komutları sadece ismilerinden oluştuğu için
                            //? isimleri böylece çekkiliyor
                            // *Exec=gnome-disks
                            addNativeToList(line.substring(5));
                        }
                    } else if (!line.contains("/lib/")) {
                        // ? kütüphane komutlarını hariç tutuarak
                        // * Exec=/usr/lib/x86_64-linux-gnu/libexec/polkit-kde-authentication-agent-1
                        if (line.contains(" ")) // deneme += line.substring(line.lastIndexOf('/')+1, line.indexOf("
                                                // "))+"\n";
                        {
                            // ? gerekli uygulama isimleri çekliyor
                            // *Exec=/usr/bin/zoom %U
                            addNativeToList(line.substring(line.lastIndexOf('/') + 1, line.indexOf(" ")));
                        } else // deneme += line.substring(line.lastIndexOf('/')+1)+"\n";
                        {
                            // *Exec=/usr/libexec/xdg-desktop-portal-gtk
                            addNativeToList(line.substring(line.lastIndexOf('/') + 1));
                        }
                    }
                }

            }
            input.close();
            // System.out.println(deneme + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeNativeApp(String app, String pass) {
        //? root olarak uygulmalyı silmek için gerekli komutları çalıştırıyor
        String[] cmd = { "/bin/bash", "-c", "echo " + pass + "|sudo -S apt remove " + app + " -y" };
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
