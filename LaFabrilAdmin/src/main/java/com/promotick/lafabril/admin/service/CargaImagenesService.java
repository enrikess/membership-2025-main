package com.promotick.lafabril.admin.service;

import com.promotick.lafabril.admin.controller.excel.form.ResultCargaImagenes;

import java.io.File;

public interface CargaImagenesService {
    ResultCargaImagenes procesarCargaImagenes(File rar, String fullPath, String originalName, Integer tipoImagen);
}
