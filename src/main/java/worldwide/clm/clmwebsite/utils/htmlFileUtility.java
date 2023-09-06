package worldwide.clm.clmwebsite.utils;

import worldwide.clm.clmwebsite.exception.ClmException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.common.Message.FAILED_TO_GET_ACTIVATION_LINK;

public class htmlFileUtility {
    public static String getFileTemplate(String filePath) throws ClmException {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(filePath))){
            return  reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new ClmException(FAILED_TO_GET_ACTIVATION_LINK);
        }
    }
}
