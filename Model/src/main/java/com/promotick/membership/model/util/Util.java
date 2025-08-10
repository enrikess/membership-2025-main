package com.promotick.membership.model.util;

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
}
