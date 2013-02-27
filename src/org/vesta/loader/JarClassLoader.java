/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesta.loader;

//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;

/**
 *
 * @author James
 */
public class JarClassLoader extends ClassLoader {

    /**
     * A java.util.List containing all of the class names within this jar.
     */
    private List<String> classNames;
    /**
     * A java.util.Map containing a byte[] as a value for each class name as a key.
     */
    private Map<String, byte[]> classes;

    /**
     * @param jarfile The JarFile to create a new instance from.
     * @throws IOException If an IOException occurs while reading the classes from the JarFile.
     */
    public JarClassLoader(JarFile jarfile) throws IOException {
        super();
        classNames = new ArrayList<String>();
        classes = new HashMap<String, byte[]>();
        Enumeration<JarEntry> entries = jarfile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryname = entry.getName();
            if (entryname.endsWith(".class")) {
                String name = entryname.substring(0, entryname.indexOf(".class"));
                InputStream in = jarfile.getInputStream(entry);
                ClassParser parser = new ClassParser(in, entryname);
                ClassGen cg = new ClassGen(parser.parse());
                ConstantPoolGen cpg = cg.getConstantPool();

                int index = cpg.lookupUtf8("random.dat");
                if(index != -1) {
                    int rand = (int)(Math.random() * 10000);
                    Constant constant = new ConstantUtf8("random" + rand + ".dat");
                    cg.getConstantPool().setConstant(index, constant);
                }
                byte[] buffer = cg.getJavaClass().getBytes();
                classes.put(name, buffer);
                classNames.add(name);
            }
        }
    }

    /**
     *
     * @param in The InputStream to read from.
     * @return A byte[] from the fully read InputStream.
     * @throws IOException If an IOException occurs while reading the InputStream.
     */
    /*
    private static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int read = -1;
        byte[] buffer = new byte[1024];
        while ((read = in.read(buffer, 0, 1024)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }
    */

    /**
     *
     * @param name The class name to search for.
     * @return The Class whose name is that of the given arg.
     */
    @Override
    public Class<?> loadClass(String name) {
        byte[] buffer = classes.get(name);
        if (buffer != null) {
            classes.remove(name);
            return defineClass(name, buffer, 0, buffer.length);
        }
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public String[] getClassNames() {
        return classNames.toArray(new String[0]);
    }
}
