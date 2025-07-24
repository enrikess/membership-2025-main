package com.promotick.lafabril.admin.controller.excel.form;


import com.monitorjbl.xlsx.StreamingReader;
import com.promotick.apiclient.utils.FileException;
import com.promotick.apiclient.utils.file.excel.IExcelFileValidator;
import com.promotick.apiclient.utils.file.validator.FileValidator;
import com.promotick.apiclient.utils.file.validator.FileValidatorResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

public abstract class MyExcelFileValidator<T, E> extends FileValidator<T, E> implements IExcelFileValidator<T, E> {
    private static final Integer KEEP_ROWS_MEMORY = 10;
    private static final Integer BUFFER_SIZE_INPUTSTREAM = 1024;
    private Cell temporalCell;

    public MyExcelFileValidator() {
    }

    public FileValidatorResult<T, E> build(MultipartFile excelfile) throws Exception {
        return this.build(excelfile, 0);
    }

    public FileValidatorResult<T, E> build(MultipartFile excelfile, Integer sheetPosition) throws Exception {
        if (excelfile == null) {
            throw new FileException("File is null");
        } else if (sheetPosition != null && sheetPosition >= 0) {
            Workbook workbook = new XSSFWorkbook(excelfile.getInputStream());
            return this.build((Workbook)workbook);
        } else {
            throw new FileException("Sheet position incorrect");
        }
    }

    public FileValidatorResult<T, E> build(File file) throws Exception {
        return this.build(file, 0);
    }

    public FileValidatorResult<T, E> build(File file, Integer sheetPosition) throws Exception {
        if (file == null) {
            throw new FileException("File is null");
        } else if (sheetPosition != null && sheetPosition >= 0) {
            try {
                InputStream is = new FileInputStream(file);
                Throwable var4 = null;

                Object var7;
                try {
                    Workbook workbook = StreamingReader.builder().rowCacheSize(KEEP_ROWS_MEMORY).bufferSize(BUFFER_SIZE_INPUTSTREAM).open(is);
                    Throwable var6 = null;

                    try {
                        var7 = this.build(workbook, sheetPosition);
                    } catch (Throwable var32) {
                        var7 = var32;
                        var6 = var32;
                        throw var32;
                    } finally {
                        if (workbook != null) {
                            if (var6 != null) {
                                try {
                                    workbook.close();
                                } catch (Throwable var31) {
                                    var6.addSuppressed(var31);
                                }
                            } else {
                                workbook.close();
                            }
                        }

                    }
                } catch (Throwable var34) {
                    var4 = var34;
                    throw var34;
                } finally {
                    if (is != null) {
                        if (var4 != null) {
                            try {
                                is.close();
                            } catch (Throwable var30) {
                                var4.addSuppressed(var30);
                            }
                        } else {
                            is.close();
                        }
                    }

                }

                return (FileValidatorResult)var7;
            } catch (Exception var36) {
                return super.fileValidatorResult;
            }
        } else {
            throw new FileException("Sheet position incorrect");
        }
    }

    private FileValidatorResult<T, E> build(Workbook workbook) throws Exception {
        return this.build(workbook, 0);
    }

    private FileValidatorResult<T, E> build(Workbook workbook, Integer sheetPosition) throws Exception {
        if (workbook == null) {
            throw new Exception("Error read local file");
        } else if (sheetPosition != null && sheetPosition >= 0) {
            Sheet worksheet = workbook.getSheetAt(sheetPosition);
            int rowCount = 0;
            Iterator var5 = worksheet.iterator();

            while(var5.hasNext()) {
                Row row = (Row)var5.next();
                ++rowCount;
                if (rowCount != 1) {
                    this.finallyValidate(rowCount, row);
                }
            }

            workbook.close();
            return super.fileValidatorResult;
        } else {
            throw new FileException("Sheet position incorrect");
        }
    }

    private void finallyValidate(int rowCount, Row row) {
        if (!isRowEmpty(row)) {
            T object = this.iterator(rowCount, row);
            super.resultValidate(object);
        }

    }

    private static boolean isRowEmpty(Row row) {
        try {
            for(int c = row.getFirstCellNum(); c < row.getLastCellNum(); ++c) {
                Cell cell = row.getCell(c);
                if (cell != null && !cell.getCellTypeEnum().equals(CellType.BLANK)) {
                    return false;
                }
            }

            return true;
        } catch (Exception var3) {
            return true;
        }
    }

    protected String parseString(Row row, int index) {
        this.temporalCell = row.getCell(index);
        if(this.temporalCell == null){return null;}
        this.temporalCell.setCellType(CellType.STRING);
        return this.temporalCell != null && !StringUtils.isEmpty(this.temporalCell.getStringCellValue()) ? this.temporalCell.getStringCellValue() : null;

    }
}
