package com.devfactory.codeserver.api.util;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Class to test Zip functionalities.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ZipUtilsTest extends BaseUnitTest {

    private static final String ZIP_FOLDER1 = "zipFolderTest";
    private static final String ZIP_FOLDER2 = "innerFolder1";
    private static final String ZIP_FOLDER3 = "innerFolder2";
    private static final String FILE_NAME = "file";
    private static final String FILE_CONTENT = "Any data inside a file.";

    @Test
    public void test_100_Zip() throws Exception {
        
        try {
            FileUtils.deleteDirectory(new File(ZIP_FOLDER1));
        } catch (Throwable ex) {
        }
        try {
            FileUtils.forceDelete(new File(ZIP_FOLDER1.concat(".zip")));
        } catch (Throwable ex) {
        }
        
        FileUtils.forceMkdir(new File(ZIP_FOLDER1));
        FileUtils.forceMkdir(new File(ZIP_FOLDER1.concat("/").concat(ZIP_FOLDER2)));
        FileUtils.forceMkdir(new File(ZIP_FOLDER1.concat("/").concat(ZIP_FOLDER2).concat("/").concat(ZIP_FOLDER3)));

        File file = new File(ZIP_FOLDER1.concat("/")
                .concat(ZIP_FOLDER2).concat("/")
                .concat(ZIP_FOLDER3).concat("/")
                .concat(FILE_NAME));
        FileUtils.writeStringToFile(file, FILE_CONTENT);

        File zip = new File(ZIP_FOLDER1.concat(".zip"));
        ZipUtils.zip(ZIP_FOLDER1, zip);

        assertTrue("Zip file created", zip.exists());
    }

    @Test
    public void test_200_Unzip() throws Exception {

        try {
            FileUtils.deleteDirectory(new File(ZIP_FOLDER1));
        } catch (Throwable ex) {
        }

        File zip = new File(ZIP_FOLDER1.concat(".zip"));
        ZipUtils.unzip(zip, ZIP_FOLDER1);

        File file = new File(ZIP_FOLDER1.concat("/")
                .concat(ZIP_FOLDER2).concat("/")
                .concat(ZIP_FOLDER3).concat("/")
                .concat(FILE_NAME));

        assertTrue("Unzip zip into folder", file.exists());
        assertEquals("Data inside file is the same stored before zip", FILE_CONTENT, FileUtils.readFileToString(new File(file.getAbsolutePath())));

        try {
            FileUtils.deleteDirectory(new File(ZIP_FOLDER1));
        } catch (Throwable ex) {
        }
        try {
            FileUtils.forceDelete(new File(FILE_NAME));
        } catch (Throwable ex) {
        }
        try {
            FileUtils.forceDelete(new File(ZIP_FOLDER1.concat(".zip")));
        } catch (Throwable ex) {
        }
    }

}
