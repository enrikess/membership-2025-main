package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaPdf;
import com.promotick.lafabril.admin.service.CargaPdfService;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.common.CustomPair;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaImagen;
import com.promotick.lafabril.model.util.descarga.FormCargaPdf;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.NativeStorage;
import de.innosystec.unrar.rarfile.FileHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class CargaPdfServiceImpl implements CargaPdfService {

    public static final List<String> FORMATOS_ACEPTADOS;
    private static final Logger LOGGER = LoggerFactory.getLogger(CargaPdfServiceImpl.class);

    static {
        FORMATOS_ACEPTADOS = new ArrayList<>(Arrays.asList("PDF"));
    }

    private ApiS3Service apiS3Service;

    @Autowired
    public CargaPdfServiceImpl(ApiS3Service apiS3Service) {
        this.apiS3Service = apiS3Service;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultCargaPdf procesarCargaPdf(File rar, String fullPath, String originalName) {

        ResultCargaPdf resultCargaPdf = new ResultCargaPdf();
        try {
            Archive archive = new Archive(new NativeStorage(rar));
            Pair<CustomPair<Integer, Integer>, List<Pair<String, String>>> iterateResult = this.iterateFiles(archive, fullPath);

            CustomPair<Integer, Integer> conteos = iterateResult.getLeft();
            LOGGER.info("Correctos" + conteos.getLeft());
            LOGGER.info("Errores" + conteos.getRight());

            FileUtils.deleteDirectory(new File(fullPath));

            resultCargaPdf.setRegistrosCorrectos(conteos.getLeft());
            resultCargaPdf.setRegistrosError(conteos.getRight());
            resultCargaPdf.setRegistrosTotal(conteos.getLeft() + conteos.getRight());

            if (conteos.getRight() > 0) {
                List<FormCargaPdf> list = new ArrayList<>();
                for (Pair<String, String> par : iterateResult.getRight()) {
                    list.add(new FormCargaPdf().setNombreArchivo(par.getLeft()).setError(par.getRight()));
                }
                resultCargaPdf.setListaErrores(list);
            }
        } catch (Exception e) {
            log.error("Error procesarCargaPdf", e);
            resultCargaPdf.setException(e.getMessage());
        }
        return resultCargaPdf;
    }

    private Pair<CustomPair<Integer, Integer>, List<Pair<String, String>>> iterateFiles(Archive archive, String path) throws IOException {
        int errores = 0;
        int correctos = 0;
        List<Pair<String, String>> listaErrores = new ArrayList<>();
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            if (!fileHeader.isDirectory()) {
                Pair<Boolean, String> result = this.evalFile(archive, fileHeader, path);
                if (!result.getLeft()) {
                    errores++;
                    listaErrores.add(new CustomPair<>(this.cleanFileName(fileHeader.getFileNameString()), result.getRight()));
                } else {
                    correctos++;
                }
            }
        }

        archive.close();

        return new CustomPair<>(new CustomPair<>(correctos, errores), listaErrores);
    }

    private Pair<Boolean, String> evalFile(Archive archive, FileHeader fileHeader, String fullPath) {
        String mensaje = "";
        boolean estado = false;
        String fileClean = this.cleanFileName(fileHeader.getFileNameString());

        try {
            String extension = FilenameUtils.getExtension(fileClean);
            if (!FORMATOS_ACEPTADOS.contains(extension.toUpperCase())) {
                throw new Exception("El formato: " + extension + " no es un pdf");
            }

            File tempFile = new File(fullPath + "/" + fileClean);

            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                archive.extractFile(fileHeader, fileOutputStream);
            }

            if (tempFile.exists()) {
                AbstractResponse response = this.sendFileS3(tempFile, fileClean, extension);
                if (!response.isStatus()) {
                    throw new Exception(response.getMessage());
                }
            } else {
                throw new Exception("No se pudo crear el archivo '" + fileClean + "'");
            }
            estado = true;
        } catch (Exception e) {
            mensaje = e.getMessage();
        }

        return new CustomPair<>(estado, mensaje);
    }

    private AbstractResponse sendFileS3(File tempFile, String key, String extension) {
        S3UploadRequest s3UploadRequest = new S3UploadRequest();
        s3UploadRequest.setFile(tempFile);

        s3UploadRequest.setKey(key);
        s3UploadRequest.setPublic(true);
        s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_PDF.getFolder());
        s3UploadRequest.setEntity(1);
        s3UploadRequest.setMediaType(Util.fromExtension(extension));
        AbstractResponse<FileMetadata> result = apiS3Service.uploadFileS3(s3UploadRequest);
        LOGGER.info("Result pdf: " + result.getMessage());
        return result;
    }

    private String cleanFileName(String filename) {
        if (filename.contains("/")) {
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        }
        if (filename.contains("\\")) {
            filename = filename.substring(filename.lastIndexOf("\\") + 1);
        }
        return filename;
    }
}
