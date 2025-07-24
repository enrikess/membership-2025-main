package com.promotick.lafabril.model.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Util {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = (requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public static HttpSession getSession() {
        return Util.getRequest().getSession(false);
    }

    public static String getMessage(MessageSource messageSource, String key) {
        return getMessage(messageSource, key, null);
    }

    public static String getMessage(MessageSource messageSource, String key, Object[] params) {
        try {
            if (messageSource != null) {
                return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
            }
        } catch (Exception ex) {
        }
        return StringUtils.EMPTY;
    }

    public static void printJson(HttpServletResponse response, Object object) throws IOException {
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(object));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static AbstractResponse<FileMetadata> sentFileToS3(ApiS3Service apiS3Service, String filename, UtilEnum.TIPO_CARGA tipoCarga, MultipartFile multipartFile) throws Exception {
        return sentFileToS3(apiS3Service, filename, tipoCarga, multipartFile, null, false);
    }

    public static AbstractResponse<FileMetadata> sentFileToS3(ApiS3Service apiS3Service, String filename, UtilEnum.TIPO_CARGA tipoCarga, File file) throws Exception {
        return sentFileToS3(apiS3Service, filename, tipoCarga, null, file, false);
    }

    private static AbstractResponse<FileMetadata> sentFileToS3(ApiS3Service apiS3Service, String filename, UtilEnum.TIPO_CARGA tipoCarga, MultipartFile multipartFile, File file) throws Exception {
        return sentFileToS3(apiS3Service, filename, tipoCarga, multipartFile, file, false);
    }

    public static AbstractResponse<FileMetadata> sentFileToS3(ApiS3Service apiS3Service, String filename, UtilEnum.TIPO_CARGA tipoCarga, MultipartFile multipartFile, File file, boolean isPublic) throws Exception {

        if (multipartFile == null && file == null) {
            throw new Exception("Validation: File not found");
        }

        S3UploadRequest s3UploadRequest = new S3UploadRequest()
                .setKey(filename)
                .setFolderName(tipoCarga.getFolder())
                .setPublic(isPublic)
                .setEntity(1)
                .setType(tipoCarga.name());

        if (multipartFile != null) {
            s3UploadRequest.setMultipartFile(multipartFile);
        }

        if (file != null) {
            s3UploadRequest.setFile(file);
        }

        AbstractResponse<FileMetadata> resultUploadS3 = apiS3Service.uploadFileS3(s3UploadRequest);

        if (!resultUploadS3.isStatus()) {
            throw new Exception(String.format("%s: %s(%s)", tipoCarga.name(), "S3ApiServer", resultUploadS3.getMessage()));
        }

        return resultUploadS3;
    }

    public static String dateFormat(String format) {
        try {
            return new SimpleDateFormat(format).format(new Date());
        } catch (Exception e) {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    public static MediaType fromExtension(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            default:
                return null;
        }
    }

    public static String nombreMes(Integer mes) {
        switch (mes) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Junio";
            case 8:
                return "Agosto";
            case 9:
                return "Setiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "--";
        }
    }

}
