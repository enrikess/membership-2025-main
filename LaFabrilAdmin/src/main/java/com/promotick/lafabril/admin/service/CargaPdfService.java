package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.admin.controller.excel.form.ResultCargaPdf;

import java.io.File;

public interface CargaPdfService {
    ResultCargaPdf procesarCargaPdf(File rar, String fullPath, String originalName);
}
