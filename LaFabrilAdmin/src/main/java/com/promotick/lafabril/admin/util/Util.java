package com.promotick.lafabril.admin.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    public static MediaType fromExtension(String extension) {
        if (extension == null) {
            return null;
        }
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

    public static String generateName(MultipartFile multipartFile) {
        return UUID.randomUUID().toString().replace("-", "") + "-" + multipartFile.getOriginalFilename();
    }

}
