package all.component.diplomna.converter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class MoodleFileArchiver {

    private static Logger logger = LogManager.getLogger(MoodleFileArchiver.class);

    public synchronized static void compressFilesToZip(List<File> fileList, OutputStream outputStream) {

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (File file : fileList) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    int length;
                    byte[] buffer = new byte[1024];
                    while ((length = inputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, length);
                    }
                    // closes a single zipEntry, not the entire stream, so it's necessary
                    // to close despite try-with-resources
                    zipOutputStream.closeEntry();
                } catch (Exception e) {
                    logger.error("Failed to compress file [1]", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to compress file [2]", e);
        }
    }

}
