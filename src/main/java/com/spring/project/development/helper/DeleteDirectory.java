package com.spring.project.development.helper;

/**
 * Created By zepaG on 5/6/2022.
 */

import java.io.File;
import java.util.Objects;
class DeleteDirectory {
     public static void deleteJunkJasperReports(File file) {
         for (File subFile : Objects.requireNonNull(file.listFiles())) {
             if (subFile.isDirectory()) {
                 deleteJunkJasperReports(subFile);
            }
             subFile.delete();
        }
        file.delete();
    }
}