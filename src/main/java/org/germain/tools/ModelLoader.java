package org.germain.tools;

import java.io.InputStream;

public class ModelLoader {
    private final String titre;

    public ModelLoader(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    public InputStream getModelStream(){
        
        InputStream retour;
        
        switch (titre){
            case "cda" -> retour = getFileFromResourceAsStream("org/germain/docs/modelcda.xlsx");
            case "dwwm" -> retour = getFileFromResourceAsStream("org/germain/docs/modeldwwm.xlsx");
            default -> throw new IllegalStateException("Unexpected value: " + titre);
        }
        
        return retour;
    }
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
