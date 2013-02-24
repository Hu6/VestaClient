/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.vesta.loader;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author James
 */
public class RSStub implements AppletStub, AppletContext {

    private URL codeBase;
    private Applet applet;
    private Properties params;
    private Map<String, InputStream> inputMap;

    public RSStub(String prefix, Applet applet) throws IOException {
        loadParams(prefix);
        this.applet = applet;
        this.inputMap = new HashMap<String, InputStream>();
    }

    private void loadParams(String prefix) throws IOException {
        params = new Properties();
        prefix="oldschool77";
        codeBase = new URL("http://" + prefix + ".runescape.com/j1");

        URLConnection connect = codeBase.openConnection();
        InputStream in = connect.getInputStream();
        Reader reader = new InputStreamReader(in);
        BufferedReader bufRead = new BufferedReader(reader);

        String line = "";
        while ((line = bufRead.readLine()) != null) {
            if (line.contains("param")) {
                String preName = line.substring(line.indexOf("<param name=") + 12);
                String name = preName.substring(0, preName.indexOf(" "));

                String preValue = line.substring(line.indexOf("value=") + 6);
                String value = preValue.substring(0, preValue.indexOf(">"));

                if (name.contains("\"") || value.contains("\"")) {
                    name = name.replaceAll("\"", "");
                    value = value.replaceAll("\"", "");
                }

                params.setProperty(name, value);
            }
        }
    }

    public void appletResize(int width, int height) {
    }

    public AppletContext getAppletContext() {
        return this;
    }

    public URL getCodeBase() {
        return codeBase;
    }

    public URL getDocumentBase() {
        return codeBase;
    }

    public String getParameter(String name) {
        return params.getProperty(name);
    }

    public boolean isActive() {
        return false;
    }

    public Applet getApplet(String name) {
        if(name.equals("runescape"))
            return applet;
        return null;
    }

    public Enumeration<Applet> getApplets() {
        Vector<Applet> applets = new Vector<Applet>(1);
        applets.add(applet);
        return applets.elements();
    }

    public AudioClip getAudioClip(URL url) {
        return Applet.newAudioClip(url);
    }

    public Image getImage(URL url) {
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public InputStream getStream(String key) {
        return inputMap.get(key);
    }

    public Iterator<String> getStreamKeys() {
        return inputMap.keySet().iterator();
    }

    public void setStream(String key, InputStream stream) throws IOException {
        inputMap.put(key, stream);
    }

    public void showDocument(URL url) {
        if(url != null)
            System.out.println(url.toString());
    }

    public void showDocument(URL url, String target) {
    	showDocument(url);
    }

    public void showStatus(String status) {
        System.out.println(status);
    }

}
