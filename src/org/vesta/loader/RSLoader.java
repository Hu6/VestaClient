/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vesta.loader;

import java.applet.Applet;
import java.net.JarURLConnection;
import java.net.URL;

/**
 *
 * @author James
 */
public class RSLoader {

    private Applet applet;
    private Class<?> appletSub;
    private JarClassLoader classLoader;

    public RSLoader(int world, boolean signed) throws Exception {
        String prefix = "oldschool6";
        if(signed) {
            classLoader = new JarClassLoader(
                ((JarURLConnection)(new URL("jar:http://" + prefix + ".runescape.com/gamepack_5462530.jar!/")).openConnection()).getJarFile()
            );
        } else {
            classLoader = new JarClassLoader(
            	((JarURLConnection)(new URL("jar:http://" + prefix + ".runescape.com/gamepack_5462530.jar!/")).openConnection()).getJarFile()
            );
        }

      
           // appletSub = classLoader.loadClass("loader");
     
            appletSub = classLoader.loadClass("client");
        
        applet = (Applet) appletSub.newInstance();
        applet.setStub(new RSStub(prefix, applet));
        applet.init();
        applet.start();
    }

    public Applet getApplet() {
        return applet;
    }

    public JarClassLoader getClassLoader() {
    	return classLoader;
    }

}
