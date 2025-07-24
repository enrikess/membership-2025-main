package com.promotick.lafabril.admin.service.impl;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.admin.util.Util;
import com.promotick.lafabril.admin.controller.excel.form.ResultCargaImagenes;
import com.promotick.lafabril.admin.service.CargaImagenesService;
import com.promotick.lafabril.common.CustomPair;
import com.promotick.lafabril.model.util.UtilEnum;
import com.promotick.lafabril.model.util.descarga.FormCargaImagen;
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
public class CargaImagenesServiceImpl implements CargaImagenesService {

    public static final List<String> FORMATOS_ACEPTADOS;
    private static final Logger LOGGER = LoggerFactory.getLogger(CargaImagenesServiceImpl.class);

    static {
        FORMATOS_ACEPTADOS = new ArrayList<>(Arrays.asList("JPG", "JPEG", "PNG", "GIF"));
    }

    private ApiS3Service apiS3Service;

    @Autowired
    public CargaImagenesServiceImpl(ApiS3Service apiS3Service) {
        this.apiS3Service = apiS3Service;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultCargaImagenes procesarCargaImagenes(File rar, String fullPath, String originalName, Integer tipoImagen) {

        ResultCargaImagenes resultCargaImagenes = new ResultCargaImagenes();
        try {
            Archive archive = new Archive(new NativeStorage(rar));
            Pair<CustomPair<Integer, Integer>, List<Pair<String, String>>> iterateResult = this.iterateFiles(archive, fullPath, tipoImagen);

            CustomPair<Integer, Integer> conteos = iterateResult.getLeft();
            LOGGER.info("Correctos" + conteos.getLeft());
            LOGGER.info("Errores" + conteos.getRight());

            FileUtils.deleteQuietly(rar);
            FileUtils.deleteDirectory(new File(fullPath));

            resultCargaImagenes.setRegistrosCorrectos(conteos.getLeft());
            resultCargaImagenes.setRegistrosError(conteos.getRight());
            resultCargaImagenes.setRegistrosTotal(conteos.getLeft() + conteos.getRight());

            if (conteos.getRight() > 0) {
                List<FormCargaImagen> list = new ArrayList<>();
                for (Pair<String, String> par : iterateResult.getRight()) {
                    list.add(new FormCargaImagen().setNombreArchivo(par.getLeft()).setError(par.getRight()));
                }
                resultCargaImagenes.setListaErrores(list);
            }
        } catch (Exception e) {
            log.error("Error procesarCargaImagenes", e);
            resultCargaImagenes.setException(e.getMessage());
        }
        return resultCargaImagenes;
    }

    private Pair<CustomPair<Integer, Integer>, List<Pair<String, String>>> iterateFiles(Archive archive, String path, Integer tipoImagen) throws IOException {
        int errores = 0;
        int correctos = 0;
        List<Pair<String, String>> listaErrores = new ArrayList<>();
        for (FileHeader fileHeader : archive.getFileHeaders()) {
            if (!fileHeader.isDirectory()) {
                Pair<Boolean, String> result = this.evalFile(archive, fileHeader, path, tipoImagen);
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

    private Pair<Boolean, String> evalFile(Archive archive, FileHeader fileHeader, String fullPath, Integer tipoImagen) {
        String mensaje = "";
        boolean estado = false;
        String fileClean = this.cleanFileName(fileHeader.getFileNameString());

        try {
            String extension = FilenameUtils.getExtension(fileClean);
            if (!FORMATOS_ACEPTADOS.contains(extension.toUpperCase())) {
                throw new Exception("El formato: " + extension + " no es una imagen");
            }

            File tempFile = new File(fullPath + "/" + fileClean);

            try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                archive.extractFile(fileHeader, fileOutputStream);
            }

            if (tempFile.exists()) {
                AbstractResponse response = this.sendFileS3(tempFile, fileClean, extension, tipoImagen);
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

    private AbstractResponse sendFileS3(File tempFile, String key, String extension, Integer tipoImagen) {
        S3UploadRequest s3UploadRequest = new S3UploadRequest();
        s3UploadRequest.setFile(tempFile);

        if(tipoImagen == 1){
            s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.getFolder());
        }else{
            s3UploadRequest.setFolderName(UtilEnum.TIPO_CARGA.CARGA_IMAGEN_APP.getFolder());
        }

        s3UploadRequest.setKey(key);
        s3UploadRequest.setPublic(true);
        s3UploadRequest.setType(UtilEnum.TIPO_CARGA.CARGA_IMAGEN.name());
        s3UploadRequest.setEntity(1);
        s3UploadRequest.setMediaType(Util.fromExtension(extension));
        AbstractResponse<FileMetadata> result = apiS3Service.uploadFileS3(s3UploadRequest);
        LOGGER.info("Result imagen: " + result.getMessage());
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
