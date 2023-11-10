package worldwide.clm.clmwebsite.utils;

import worldwide.clm.clmwebsite.exception.ClmException;

import java.io.*;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.common.Message.FAILED_TO_GET_ACTIVATION_LINK;

public class HtmlFileUtility {
    public static String getFileTemplate(String filePath) throws ClmException {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(filePath))){
            return  reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new ClmException(FAILED_TO_GET_ACTIVATION_LINK);
        }
    }

    public static String getFileTemplateFromClasspath(String resourcePath) throws ClmException {
        try (InputStream inputStream = HtmlFileUtility.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new ClmException("Resource not found: " + resourcePath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.lines().collect(Collectors.joining());
            }
        } catch (IOException exception) {
            throw new ClmException("Failed to read resource: " + resourcePath + " - " + exception.getMessage());
        }
    }
}
