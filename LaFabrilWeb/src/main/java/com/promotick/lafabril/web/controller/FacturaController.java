package com.promotick.lafabril.web.controller;

import com.promotick.apiclient.model.AbstractResponse;
import com.promotick.apiclient.model.request.S3UploadRequest;
import com.promotick.apiclient.model.response.FileMetadata;
import com.promotick.apiclient.service.ApiS3Service;
import com.promotick.lafabril.model.util.PromotickResult;
import com.promotick.lafabril.model.util.Util;
import com.promotick.lafabril.model.web.Participante;
import com.promotick.lafabril.web.service.ParticipanteWebService;
import com.promotick.lafabril.web.util.BaseController;
import com.promotick.lafabril.web.util.ConstantesWebView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("factura")
@RequiredArgsConstructor
public class FacturaController extends BaseController {

    private final ParticipanteWebService participanteWebService;
    private final ApiS3Service apiS3Service;

    @GetMapping
    public String init(Participante participante) {
        if (!participante.getCatalogo().getViewFacturas()) {
            return "redirect:/";
        }
        return ConstantesWebView.VIEW_FACTURAS;
    }

    @ResponseBody
    @PostMapping("cargar-comprobante")
    public PromotickResult cargarImagenes(@RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile, PromotickResult promotickResult, Participante participante) {
        try {
            if (imagenFile != null) {
                String filepath = this.uploadFileS3(imagenFile, participante);

                participanteWebService.registrarFacturaParticipante(filepath, participante.getIdParticipante());
            }
        } catch (Exception e) {
            promotickResult.setException(e);
        }
        return promotickResult;
    }

    private String uploadFileS3(MultipartFile file, Participante participante) throws Exception {
        String folder = "public/web/facturas/";
        String name = FilenameUtils.getBaseName(file.getOriginalFilename()) + Util.dateFormat("yyyyMMddhhmmss");
        String format = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = name + "." + format;
        String fileUrl = subirRecursoS3(file, participante.getIdParticipante(), folder, "FACTURA", true, fileName);

        if (fileUrl == null) {
            throw new Exception("Error al subir factura");
        }
        return fileUrl;
    }

    public String subirRecursoS3(MultipartFile file, Integer idUsuario, String folder, String tipo, boolean publico, String fileName) {
        try {
            S3UploadRequest s3UploadRequest = new S3UploadRequest();
            s3UploadRequest.setMultipartFile(file);
            s3UploadRequest.setFolderName(folder);
            s3UploadRequest.setKey(fileName);
            s3UploadRequest.setPublic(publico);
            s3UploadRequest.setType(tipo);
            s3UploadRequest.setEntity(idUsuario);
            s3UploadRequest.setMediaType(Util.fromExtension(FilenameUtils.getExtension(file.getOriginalFilename())));
            AbstractResponse<FileMetadata> resultUpload = apiS3Service.uploadFileS3(s3UploadRequest);
            if (resultUpload.isStatus()) {
                return resultUpload.getData().getUrl();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
