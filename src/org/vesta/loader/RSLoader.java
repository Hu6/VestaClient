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

	public RSLoader(int world, int game, boolean signed) throws Exception {
		String prefix="";
		if(game==0)
		{
			prefix = "classic";
			classLoader = new JarClassLoader(
					((JarURLConnection)(new URL("jar:http://" + prefix + world + "a.runescape.com/loader1726866179.jar!/")).openConnection()).getJarFile()
					);
			//Runescape Classic Class to Load from Jar
			appletSub = classLoader.loadClass("loader");
		}
		else if(game==1)
		{
			prefix="oldschool";
			classLoader = new JarClassLoader(
					((JarURLConnection)(new URL("jar:http://" + prefix + world + ".runescape.com/gamepack_5462530.jar!/")).openConnection()).getJarFile()
					);
			//Runescape 2007 Class to Load from Jar
			appletSub = classLoader.loadClass("client");
		}
		else
		{
			prefix="world";
			classLoader = new JarClassLoader(
					((JarURLConnection)(new URL("jar:http://" + prefix + world + ".runescape.com/gamepack8GkJ13TpYEY1cPbVYa5/Ru7mnQT+BM0B_9629289.jar!/")).openConnection()).getJarFile()
					);
			//Runescape 2007 Class to Load from Jar
			appletSub = classLoader.loadClass("Rs2Applet");
		}

		applet = (Applet) appletSub.newInstance();
		applet.setStub(new RSStub(prefix, applet, world, game));
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
