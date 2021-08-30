package all.component.diplomna.converter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class MoodleFileArchiver {

    private static Logger logger = Logger.getLogger(MoodleFileArchiver.class);

    public static File compressFilesToZip (List<File> fileList, OutputStream outputStream) {

        for (File file : fileList) {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream); FileInputStream inputStream = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                int length;
                byte[] buffer = new byte[1024];
                while ((length = inputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, length);
                }
                zipOutputStream.closeEntry();
            } catch (Exception e) {
                logger.error("Failed to compress file", e);
            }
        }
        return null;
    }

}