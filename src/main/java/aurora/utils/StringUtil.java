package aurora.utils;

public class StringUtil {

    /**
     * 从文件名中获取文件后缀
     * @param fullFilename
     * @return
     */
    public static String getFileExtension(String fullFilename){
        String type = "unknown";

        int pointIndex = fullFilename.length()-1;

        for(int i = fullFilename.length() - 1; i > 0; i--){
            if(fullFilename.charAt(i) == '.'){
                pointIndex = i;
                break;
            }
        }
        if(pointIndex != fullFilename.length()-1){
            type = fullFilename.substring(pointIndex+1,fullFilename.length());
        }
        return type;
    }

}
